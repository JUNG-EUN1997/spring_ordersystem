package beyondProjectForOrdersystem.ordering.service;

import beyondProjectForOrdersystem.member.domain.Member;
import beyondProjectForOrdersystem.member.repository.MemberRepository;
import beyondProjectForOrdersystem.ordering.domain.OrderDetail;
import beyondProjectForOrdersystem.ordering.domain.Ordering;
import beyondProjectForOrdersystem.ordering.dto.OrderSaveReqDto;
import beyondProjectForOrdersystem.ordering.repository.OrderingRepository;
import beyondProjectForOrdersystem.product.domain.Product;
import beyondProjectForOrdersystem.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public OrderingService(OrderingRepository orderingRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.orderingRepository = orderingRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }


    public Ordering orderCreate(OrderSaveReqDto dto){
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(()-> new EntityNotFoundException("없는 유저입니다."));

        Ordering ordering = Ordering.builder()
                .member(member)
                .build();

        List<OrderDetail> orderDetailList = new ArrayList<>();
        for (OrderSaveReqDto.OrderDetailDto saveProduct : dto.getOrderDetailDtoList()) {
            Product product = productRepository.findById(saveProduct.getProductId())
                    .orElseThrow(()-> new EntityNotFoundException("없는 상품 입니다."));
            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(saveProduct.getProductCount())
                    .product(product)
                    .ordering(ordering)
                    .build();

            ordering.getOrderDetails().add(orderDetail);
        }


        Ordering savedOrdering = orderingRepository.save(ordering);
        return savedOrdering;

    }

}
