package spring.Service;

import java.util.List;

import jakarta.persistence.criteria.Order;
import org.springframework.stereotype.Service;

import spring.Entity.OrderEntity;
import spring.Repository.OrderRepository;

@Service
public class OrderServiceImp implements OrderService {

	private final OrderRepository orderRepository;

	public OrderServiceImp(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	@Override
	public List<OrderEntity> findAllByCartId(Long id) {
		return orderRepository.findByCartId(id);
	}

	@Override
	public OrderEntity addOrder(OrderEntity orderEntity) {
		return orderRepository.save(orderEntity);
	}

	@Override
	public OrderEntity deleteById(Long id) {
		OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		orderRepository.deleteById(id);
		return order;
	}

	@Override
	public OrderEntity updateOrder(OrderEntity orderEntity, Long id) {
		OrderEntity order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
		order.setQuantity(orderEntity.getQuantity());
		order.setProduct(orderEntity.getProduct());
		order.setCart(orderEntity.getCart());
		return orderRepository.save(order);
	}

	@Override
	public List<OrderEntity> findAll() {
		return orderRepository.findAll();
	}

	@Override
	public OrderEntity findById(Long id) {
		return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found"));
	}
}
