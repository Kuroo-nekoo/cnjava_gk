package spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Entity.BillEntity;
import spring.Repository.BillRepository;

@Service
public class BillServiceImp implements BillService{

	@Autowired  
	BillRepository billRepo;

	@Override
	public void saveBill(BillEntity billEntity) {
		// TODO Auto-generated method stub
		billRepo.save(billEntity);
	}

	@Override
	public List<BillEntity> findAll() {
		// TODO Auto-generated method stub
		return billRepo.findAll();
	}

	@Override
	public BillEntity getById(Long id) {
		// TODO Auto-generated method stub
		return billRepo.getById(id);
	}

}
