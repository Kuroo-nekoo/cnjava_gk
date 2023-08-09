package spring.Service;

import java.util.List;

import spring.Entity.DetailCategoryEntity;

public interface DetailCategoryService {

	List<DetailCategoryEntity> findAll();
	void save(DetailCategoryEntity detailCategoryEntity);

}
