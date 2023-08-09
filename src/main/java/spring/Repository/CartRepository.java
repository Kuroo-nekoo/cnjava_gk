package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.Entity.CartEntity;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, Long>{

	CartEntity findByUserId(Long id);
	
}


	
