package beyondProjectForOrdersystem.product.service;

import beyondProjectForOrdersystem.product.domain.Product;
import beyondProjectForOrdersystem.product.dto.ProductResDto;
import beyondProjectForOrdersystem.product.dto.ProductSaveReqDto;
import beyondProjectForOrdersystem.product.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final ProductRepository productRepository;
    private final S3Client s3Client;

    @Autowired
    public ProductService(ProductRepository productRepository, S3Client s3Client) {
        this.productRepository = productRepository;
        this.s3Client = s3Client;
    }

    public Product productCreate(ProductSaveReqDto dto){
        MultipartFile image = dto.getProductImage();
        Product product;
        try{
            product = productRepository.save(dto.toEntity());
            byte[] bytes = image.getBytes();
            Path path = Paths.get("C:/Users/rro06/OneDrive/Desktop/tmp/"
                    , product.getId() + "_" + image.getOriginalFilename());
            Files.write(path,bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            product.updateImagePath(path.toString());

        }catch (IOException e){
            throw new RuntimeException("이미지 저장 실패");
        }
        return product;
    }

//    aws s3 버킷에 파일을 넣을 것 인데, 업로드를 위한 권한을 어떻게 줄 것인가?
//          1) id + pw  > 권한이 너무 많아서 X
//          2) s3에 권한이 있는 특정 계정 생성함
    public Product productAwsCreate(ProductSaveReqDto dto){
        MultipartFile image = dto.getProductImage();
        Product product;
        try{
            product = productRepository.save(dto.toEntity());
            byte[] bytes = image.getBytes();
            String fileName = product.getId() + "_" + image.getOriginalFilename();
            Path path = Paths.get("C:/Users/rro06/OneDrive/Desktop/tmp/"
                    , fileName);
            /*
            * 1. FRONT > BACK 에서 줄 때 AWS에서 BYTE를 바로 올리는 기능을 지원해주지 않음
            * 2. 따라서 BYTE를 다시 파일로 변환
            * 3. 그 파일을 AWS로 업로드 하는 방향으로 진행
            * */

//            local PC에 임시 저장
            Files.write(path,bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

//            AWS에 PC에 저장된 파일을 업로드
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileName)
                    .build();

            PutObjectResponse putObjectResponse = s3Client.putObject(putObjectRequest, RequestBody.fromFile(path));
            String s3FiltPath = s3Client.utilities()
                    .getUrl(a->a.bucket(bucket).key(fileName)) // 해당 fileName으로 bucket 안에 있는 path를 찾아옴
                    .toExternalForm();

            product.updateImagePath(s3FiltPath);

        }catch (IOException e){
            throw new RuntimeException("이미지 저장 실패");
        }
        return product;
    }


    public Page<ProductResDto> productList(Pageable pageable){
        Page<Product> listProduct = productRepository.findAll(pageable);
        Page<ProductResDto> productResDtoPage = listProduct.map(a->a.fromEntity());
        return productResDtoPage;
    }

}
