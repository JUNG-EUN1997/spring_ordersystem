package beyondProjectForOrdersystem.ordering.controller;

import beyondProjectForOrdersystem.common.dto.CommonResDto;
import beyondProjectForOrdersystem.ordering.domain.Ordering;
import beyondProjectForOrdersystem.ordering.dto.OrderListResDto;
import beyondProjectForOrdersystem.ordering.dto.OrderSaveReqDto;
import beyondProjectForOrdersystem.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class OrderingController {
    private final OrderingService orderingService;

    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }


    @PostMapping("/order/create")
    public ResponseEntity<?> orderCreate(@RequestBody OrderSaveReqDto dto){
        Ordering ordering = orderingService.orderCreate(dto);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.CREATED,"정상 주문 완료", ordering.getId());
        return new ResponseEntity<>(commonResDto,HttpStatus.CREATED);
    }

    @GetMapping("/order/list")
    public ResponseEntity<?> orderList(Pageable pageable){
        Page<OrderListResDto> orderlist = orderingService.orderList(pageable);
        CommonResDto commonResDto = new CommonResDto(HttpStatus.OK,"정상 조회 완료",orderlist);
        return new ResponseEntity<>(commonResDto,HttpStatus.OK);
    }

}
