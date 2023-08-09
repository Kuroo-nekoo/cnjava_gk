package spring.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import spring.Entity.CategoryEntity;
import spring.Repository.CategoryRepository;

@Service
public class CategoryServiceImp implements CategoryService{

	@Autowired
	private CategoryRepository categoryRepo;
	
	@Override
	public List<CategoryEntity> findAll() {
		// TODO Auto-generated method stub
		return categoryRepo.findAll();
	}

	
}
