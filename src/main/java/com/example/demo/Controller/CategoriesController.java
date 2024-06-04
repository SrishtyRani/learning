package com.example.demo.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Model.Categories;
import com.example.demo.Repository.CategoriesRepository;

@Controller
@RequestMapping("/admin/categories")
public class CategoriesController {

	@Autowired
	private CategoriesRepository categoriesRepository;

		@PostMapping
		public String createCategory(@ModelAttribute("category") Categories category) {
			categoriesRepository.save(category);
			
			return "redirect:/admin/categories";
		}

	@GetMapping
	public String getAllCategories(Model model) {
		List<Categories> categories = categoriesRepository.findAll();
		model.addAttribute("categories", categories);
		
		return "categories_list";
	}
	

	@GetMapping("/{id}")
	public String getCategoryById(@PathVariable Long id, Model model) {
		Optional<Categories> category = categoriesRepository.findById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "category_details";
		} else {
			return "not_found";
		}
	}

	
	@GetMapping("/{id}/edit")
	public String showUpdateForm(@PathVariable Long id, Model model) {
		Optional<Categories> category = categoriesRepository.findById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "category_edit_form";
		} else {
			return "not_found";
		}
	}

	@PostMapping("/{id}/edit")
	public String updateCategory(@PathVariable Long id, @ModelAttribute("category") Categories categoryDetails) {
		Optional<Categories> optionalCategory = categoriesRepository.findById(id);
		if (optionalCategory.isPresent()) {
			Categories category = optionalCategory.get();
			category.setName(categoryDetails.getName());
			category.setActive(categoryDetails.isActive());
			categoriesRepository.save(category);
			return "redirect:/admin/categories";
		} else {
			return "not_found";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteCategory(@PathVariable Long id) {
		categoriesRepository.deleteById(id);
		return "redirect:/admin/categories";
	}
	
	  @GetMapping("/deleteSelected")
	    public String deleteSelectedCategories(@RequestParam("selectedIds") List<Long> selectedIds) {
	        categoriesRepository.deleteAllById(selectedIds);
	        return "redirect:/admin/categories";
	    }

	    @PostMapping("/activate")
	    public String activateSelectedCategories(@RequestBody List<Long> selectedIds) {
	        List<Categories> categories = categoriesRepository.findAllById(selectedIds);
	        categories.forEach(category -> category.setActive(true));
	        categoriesRepository.saveAll(categories);
	        return "redirect:/admin/categories";
	    }

	    @PostMapping("/deactivateSelected")
	    public String deactivateSelectedCategories(@RequestBody List<Long> selectedIds) {
	        List<Categories> categories = categoriesRepository.findAllById(selectedIds);
	        categories.forEach(category -> category.setActive(false));
	        categoriesRepository.saveAll(categories);
	        return "redirect:/admin/categories";
	    }
	    

}
