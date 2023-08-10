package spring.Controller.api;

import org.springframework.web.bind.annotation.*;
import spring.Entity.OrderEntity;
import spring.Service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@ResponseBody
public class OrderControllerAPI {
    final
    OrderService orderService;

    public OrderControllerAPI(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<OrderEntity> findAllOrder() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderEntity findProductById(@PathVariable("id") Long id) {
    	return orderService.findById(id);
    }

    @PostMapping
    public OrderEntity addOrder(OrderEntity orderEntity) {
    	return orderService.addOrder(orderEntity);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(OrderEntity orderEntity, @PathVariable("id") Long id) {
    	return orderService.updateOrder(orderEntity, id);
    }

    @DeleteMapping("/{id}")
    public OrderEntity deleteOrderById(@PathVariable("id") Long id) {
    	return orderService.deleteById(id);
    }
}
