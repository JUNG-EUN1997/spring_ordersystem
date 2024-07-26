package beyondProjectForOrdersystem.ordering.service;

import beyondProjectForOrdersystem.member.domain.Member;
import beyondProjectForOrdersystem.member.repository.MemberRepository;
import beyondProjectForOrdersystem.ordering.domain.OrderDetail;
import beyondProjectForOrdersystem.ordering.domain.Ordering;
import beyondProjectForOrdersystem.ordering.dto.OrderListResDto;
import beyondProjectForOrdersystem.ordering.dto.OrderSaveReqDto;
import beyondProjectForOrdersystem.ordering.repository.OrderDetailRepository;
import beyondProjectForOrdersystem.ordering.repository.OrderingRepository;
import beyondProjectForOrdersystem.product.domain.Product;
import beyondProjectForOrdersystem.product.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service
@Transactional
public class OrderingService {
    private final OrderingRepository orderingRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public OrderingService(OrderingRepository orderingRepository, OrderDetailRepository orderDetailRepository, MemberRepository memberRepository, ProductRepository productRepository) {
        this.orderingRepository = orderingRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }


    public Ordering orderCreate(OrderSaveReqDto dto){
//        ⭐방법1.⭐쉬운방식
////        Ordering생성 : member_id, status
//        Member member = memberRepository.findById(dto.getMemberId()).orElseThrow(()->new EntityNotFoundException("없음"));
//        Ordering ordering = orderingRepository.save(dto.toEntity(member));
//
////        OrderDetail생성 : order_id, product_id, quantity
//        for(OrderSaveReqDto.OrderDetailDto orderDto : dto.getOrderDetailDtoList()){
//            Product product = productRepository.findById(orderDto.getProductId()).orElse(null);
//            int quantity = orderDto.getProductCount();
//            OrderDetail orderDetail =  OrderDetail.builder()
//                    .product(product)
//                    .quantity(quantity)
//                    .ordering(ordering)
//                    .build();
//            orderDetailRepository.save(orderDetail);
//        }
//
//        return ordering;


//        ⭐방법2.⭐ JPA에 적합한 방식 :: [OrderDetailRepository]가 필요없는 경우
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(()-> new EntityNotFoundException("없는 유저입니다."));

        Ordering ordering = Ordering.builder()
                .member(member)
                .build();

        for (OrderSaveReqDto.OrderDetailDto saveProduct : dto.getOrderDetailDtoList()) {
            Product product = productRepository.findById(saveProduct.getProductId())
                    .orElseThrow(()-> new EntityNotFoundException("없는 상품 입니다."));
            OrderDetail orderDetail = OrderDetail.builder()
                    .quantity(saveProduct.getProductCount())
                    .product(product)
                    .ordering(ordering)
                    .build();

            ordering.getOrderDetails().add(orderDetail); // ⭐⭐ orderDetailRepository.save 대신 add
//            JPA의 영속성 컨텍스트를 통해 생성이 가능한 방법이다.
        }

        Ordering savedOrdering = orderingRepository.save(ordering);
        return savedOrdering;
    }

    public Page<OrderListResDto> orderList(Pageable pageable){
        Page<Ordering> orderings =  orderingRepository.findAll(pageable);
        Page<OrderListResDto> orderListResDtos = orderings.map(a->a.fromEntity());

        return orderListResDtos;
    }

}
