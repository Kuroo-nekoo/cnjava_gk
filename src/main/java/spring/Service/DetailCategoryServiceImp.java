package spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Entity.DetailCategoryEntity;
import spring.Repository.CategoryDetailRepository;
@Service
public class DetailCategoryServiceImp implements DetailCategoryService{

	@Autowired
	CategoryDetailRepository detailCategoryRepo;
	
	@Override
	public List<DetailCategoryEntity> findAll() {
		// TODO Auto-generated method stub
		return detailCategoryRepo.findAll();
	}

	@Override
	public void save(DetailCategoryEntity detailCategoryEntity) {
		// TODO Auto-generated method stub
		detailCategoryRepo.save(detailCategoryEntity);
	}

	

}
