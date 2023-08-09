package spring.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.Entity.DetailBillEntity;

@Repository
public interface DetailBillRepository extends JpaRepository<DetailBillEntity, Long>{
	List<DetailBillEntity> findAllByBillId(Long id);
}
