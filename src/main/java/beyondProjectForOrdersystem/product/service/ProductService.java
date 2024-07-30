package beyondProjectForOrdersystem.product.service;

import beyondProjectForOrdersystem.product.domain.Product;
import beyondProjectForOrdersystem.product.dto.ProductResDto;
import beyondProjectForOrdersystem.product.dto.ProductSaveReqDto;
import beyondProjectForOrdersystem.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.UUID;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
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
            Path path = Paths.get("C:/Users/rro06/OneDrive/Desktop/tmp/"
                    , product.getId() + "_" + image.getOriginalFilename());
            Files.write(path,bytes, StandardOpenOption.CREATE, StandardOpenOption.WRITE);

            product.updateImagePath(path.toString());

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
