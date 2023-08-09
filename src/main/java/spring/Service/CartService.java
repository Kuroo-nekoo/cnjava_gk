package spring.Service;

import spring.Entity.CartEntity;

public interface CartService {

	CartEntity findByUserId(Long id);
	CartEntity findById(Long id);
	void deleteById(Long id);
	void updateCart(CartEntity cartEntity, Long id);
	void addCart(CartEntity cartEntity);
	
}
