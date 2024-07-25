package beyondProjectForOrdersystem.ordering.controller;

import beyondProjectForOrdersystem.ordering.dto.OrderSaveReqDto;
import beyondProjectForOrdersystem.ordering.service.OrderingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class OrderingController {
    private final OrderingService orderingService;

    @Autowired
    public OrderingController(OrderingService orderingService){
        this.orderingService = orderingService;
    }


    @PostMapping("/order/create")
    public void orderCreate(@RequestBody OrderSaveReqDto dto){
        orderingService.orderCreate(dto);
    }

}
