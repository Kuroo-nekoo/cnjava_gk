package spring.Service;

import java.util.List;

import spring.Entity.CategoryEntity;

public  interface  CategoryService {

	List<CategoryEntity> findAll();
}
