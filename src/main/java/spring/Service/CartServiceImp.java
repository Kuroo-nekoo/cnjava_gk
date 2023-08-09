package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Entity.CartEntity;
import spring.Repository.CartRepository;

@Service
public class CartServiceImp implements CartService{

	final
	CartRepository cartRepo;


	public CartServiceImp(CartRepository cartRepo) {
		this.cartRepo = cartRepo;
	}

	@Override
	public CartEntity findByUserId(Long id) {
		// TODO Auto-generated method stub
		return cartRepo.findByUserId(id);
	}

	@Override
	public void addCart(CartEntity cartEntity) {
		cartRepo.save(cartEntity);
	}

	@Override
	public CartEntity findById(Long id) {
		// TODO Auto-generated method stub
		return cartRepo.getById(id);
	}

	@Override
	public void deleteById(Long id) {
		cartRepo.deleteById(id);
	}

	@Override
	public void updateCart(CartEntity cartEntity, Long id) {
		CartEntity cart = cartRepo.findById(id).get();
		cart.setId(cartEntity.getId());
		cart.setOrder(cartEntity.getOrder());
		cart.setUser(cartEntity.getUser());
		cartRepo.save(cart);
	}
}
