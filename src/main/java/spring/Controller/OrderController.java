package spring.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import spring.Entity.OrderEntity;
import spring.Service.OrderService;

@Controller
public class OrderController {
//    @Autowired
//    private OrderService orderService;
//
//    @PostMapping("/add")
//    public OrderEntity addOrder(@RequestBody OrderEntity orderEntity) {
//         return orderService.addOrder(orderEntity);
//    }
}
