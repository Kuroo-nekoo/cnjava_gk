package spring.Controller.api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import spring.Dto.OrderDTO;
import spring.Entity.OrderEntity;
import spring.Repository.CartRepository;
import spring.Repository.ProductRepository;
import spring.Service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@ResponseBody
@AllArgsConstructor
public class OrderControllerAPI {

    private final OrderService orderService;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @GetMapping
    public List<OrderEntity> findAllOrder() {
        return orderService.findAll();
    }

    @GetMapping("/{id}")
    public OrderEntity findProductById(@PathVariable("id") Long id) {
    	return orderService.findById(id);
    }

    @PostMapping
    public OrderEntity addOrder(@ModelAttribute OrderDTO orderDTO) {
        System.out.println(orderDTO);
        System.out.println(cartRepository.findById(orderDTO.getCart_id()).orElseThrow(() -> new RuntimeException("Not found")));
        System.out.println(productRepository.findById(orderDTO.getProduct_id()).orElseThrow(() -> new RuntimeException("Not found")));
        OrderEntity order = OrderEntity.builder().cart(cartRepository.findById(orderDTO.getCart_id()).orElseThrow(() -> new RuntimeException("Not found")))
                .product(productRepository.findById(orderDTO.getProduct_id()).orElseThrow(() -> new RuntimeException("Not found")))
                .quantity(orderDTO.getQuantity()).build();
    	return orderService.addOrder(order);
    }

    @PutMapping("/{id}")
    public OrderEntity updateOrder(OrderDTO orderDTO, @PathVariable("id") Long id) {
        OrderEntity orderEntity = OrderEntity.builder().cart(cartRepository.findById(orderDTO.getCart_id()).orElseThrow(() -> new RuntimeException("Not found")))
                .product(productRepository.findById(orderDTO.getProduct_id()).orElseThrow(() -> new RuntimeException("Not found")))
                .quantity(orderDTO.getQuantity()).build();
    	return orderService.updateOrder(orderEntity, id);
    }

    @DeleteMapping("/{id}")
    public OrderEntity deleteOrderById(@PathVariable("id") Long id) {
    	return orderService.deleteById(id);
    }
}
