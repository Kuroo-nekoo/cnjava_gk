package spring.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.Entity.DetailCategoryEntity;

public  interface  CategoryDetailRepository  extends JpaRepository<DetailCategoryEntity,Long>{ }
