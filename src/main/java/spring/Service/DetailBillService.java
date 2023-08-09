package spring.Service;

import java.util.List;

import spring.Entity.DetailBillEntity;

public interface DetailBillService {

	void saveDetailBill(DetailBillEntity detailBillEntity);
	List<DetailBillEntity> findAllByBillId(Long id);
}
