package com.example.demo.ApiController;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.SubcategoryDto;
import com.example.demo.Model.FormData;
import com.example.demo.Model.SubCatrgories;
import com.example.demo.Repository.FormDataRepository;
import com.example.demo.Repository.SubCategoriesRepository;



@RestController
@RequestMapping("/api/v2")
public class SubcategoryApiController {

	
	
	@Autowired
	private SubCategoriesRepository  subCategoryRepository;
	@Autowired
	  private  FormDataRepository formDataRepository;
	
	
	   @GetMapping("/subcategories/{parentId}")
	    public ResponseEntity<List<SubcategoryDto>> getActiveSubCategoriesByParentId(@PathVariable Long parentId) {
	        List<SubCatrgories> subCategories = subCategoryRepository.findByParentCategoryIdAndActiveTrue(parentId);
	        List<SubcategoryDto> activeSubCategoryDTOs = subCategories.stream()
	                .map((SubCatrgories subCategory) -> {
	                	SubcategoryDto dto = new SubcategoryDto();
	                    dto.setId(subCategory.getId());
	                    dto.setName(subCategory.getName());
	                    dto.setActive(subCategory.isActive());
	                    return dto;
	                })
	                .collect(Collectors.toList());
	        return ResponseEntity.ok(activeSubCategoryDTOs);
	    }

	    @GetMapping("/form/{subcategory}")
	    public Optional<FormData> getFormDataBySubcategory(@PathVariable Long subcategory) {
	        return formDataRepository.findBySubcategory(subcategory);
	    }
}

