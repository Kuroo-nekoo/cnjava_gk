package spring.Service;

import java.util.List;

import spring.Entity.OrderEntity;

public interface OrderService {
	List<OrderEntity> findAllByCartId(Long id);
	OrderEntity addOrder(OrderEntity orderEntity);
	OrderEntity deleteById(Long id);
	OrderEntity updateOrder(OrderEntity orderEntity, Long id);

	List<OrderEntity> findAll();
	OrderEntity findById(Long id);
}
