package spring.Service;

import java.util.List;

import spring.Entity.BillEntity;

public interface BillService {

	void saveBill(BillEntity billEntity);
	List<BillEntity> findAll();
	BillEntity getById(Long id);
}
