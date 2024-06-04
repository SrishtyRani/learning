package com.example.demo.ApiController;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.CategoryDto;
import com.example.demo.Model.Categories;
import com.example.demo.Repository.CategoriesRepository;



@RestController
@RequestMapping("/api/v2")
public class CategoriesApiController {

	@Autowired
	private CategoriesRepository categoryrepo;
	
	

	@GetMapping("/{userId}/get")
	public ResponseEntity<Categories> getUserDetails(@PathVariable Long userId) {
		Categories category = categoryrepo.findById(userId).orElse(null);
		if (category != null) {

			if (category.isActive()) {
				return ResponseEntity.ok(category);
			}

			return ResponseEntity.ok(category);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/category")
	public ResponseEntity<List<CategoryDto>> getActiveCategories() {
		List<Categories> activeCategories = categoryrepo.findByActiveTrue(); 
		List<CategoryDto> categoryDTOs = new ArrayList<>();

		for (Categories category : activeCategories) {
			CategoryDto categoryDTO = new CategoryDto();
			categoryDTO.setId(category.getId());
			categoryDTO.setName(category.getName());
			categoryDTOs.add(categoryDTO);
		}

		return ResponseEntity.ok(categoryDTOs);
	}
	
 
}
