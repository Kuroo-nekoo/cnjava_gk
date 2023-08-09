package spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Entity.DetailBillEntity;
import spring.Repository.DetailBillRepository;

@Service
public class DetailBillServiceImp implements DetailBillService{

	@Autowired
	DetailBillRepository detailBillRepo;
	
	@Override
	public void saveDetailBill(DetailBillEntity detailBillEntity) {
		// TODO Auto-generated method stub
		detailBillRepo.save(detailBillEntity);
	}

	@Override
	public List<DetailBillEntity> findAllByBillId(Long id) {
		// TODO Auto-generated method stub
		return detailBillRepo.findAllByBillId(id);
	}

	

}
