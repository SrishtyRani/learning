package com.example.demo.Repository;





import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.SubCatrgories;



public interface SubCategoriesRepository extends JpaRepository <SubCatrgories, Long> {

	
	  List<SubCatrgories> findByParentCategory_Id(Long parentId);

	List<SubCatrgories> findByParentCategoryIdAndActiveTrue(Long parentId);


}
