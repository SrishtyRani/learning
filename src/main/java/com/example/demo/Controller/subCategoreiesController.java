package com.example.demo.Controller;


import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

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
import com.example.demo.Model.SubCatrgories;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.SubCategoriesRepository;

@Controller
@RequestMapping("/admin/subcategories")
public class subCategoreiesController {

	@Autowired
	private SubCategoriesRepository subcategoriesRepository;
	
	   @Autowired
	    private CategoriesRepository categoriesRepository;
	   
	   @PostMapping
	   public String createSubCategory(@ModelAttribute("subCategoryForm") SubCatrgories subCategoryForm, 
	                                   @RequestParam("parentCategoryId") Long parentCategoryId) {
	  
	       Categories parentCategory = categoriesRepository.findById(parentCategoryId).orElse(null);
	       
	       if (parentCategory == null) {
	          
	           return "redirect:/admin/subcategories?error=parentCategoryNotFound";
	       }
	       
	 
	       subCategoryForm.setParentCategory(parentCategory);
	       subcategoriesRepository.save(subCategoryForm);

	       return "redirect:/admin/subcategories";
	   }


	   @GetMapping
	   public String getAllCategories(Model model) {
	       List<SubCatrgories> subCategories = subcategoriesRepository.findAll();

	      
	       List<SubCatrgories> subCategoriesWithParent = subCategories.stream()
	               .filter(subCategory -> subCategory.getParentCategory() != null)
	               .collect(Collectors.toList());

	       List<String> parentCategoryNames = subCategoriesWithParent.stream()
	               .map(subCategory -> subCategory.getParentCategory().getName())
	               .collect(Collectors.toList());

	       List<Categories> categories = categoriesRepository.findAll();
	       
	       parentCategoryNames.forEach(System.out::println);


	       model.addAttribute("subCategories", subCategories);
	       model.addAttribute("parentCategoryNames", parentCategoryNames);
	       model.addAttribute("categories", categories); 

	       return "subcategories_list";
	   }
	   

	   


	@GetMapping("/{id}")
	public String getCategoryById(@PathVariable Long id, Model model) {
		Optional<SubCatrgories> category = subcategoriesRepository.findById(id);
		if (category.isPresent()) {
			model.addAttribute("category", category.get());
			return "subcategory_details";
		} else {
			return "not_found";
		}
	}


	@GetMapping("/{id}/edit")
	public String showEditForm(@PathVariable Long id, Model model) {
	    Optional<SubCatrgories> optionalSubcategory = subcategoriesRepository.findById(id);
	    if (optionalSubcategory.isPresent()) {
	        SubCatrgories subcategory = optionalSubcategory.get();
	        model.addAttribute("subcategory", subcategory);
	        List<Categories> categories = categoriesRepository.findAll();
	        model.addAttribute("categories", categories);
	        return "subcategory_edit_form";
	    } else {
	        return "not_found"; 
	    }
	}
	@PostMapping("/{id}/edit")
	public String updateCategory(@PathVariable Long id, @ModelAttribute("category") SubCatrgories categoryDetails) {
		Optional<SubCatrgories> optionalCategory = subcategoriesRepository.findById(id);
		if (optionalCategory.isPresent()) {
			SubCatrgories category = optionalCategory.get();
			category.setName(categoryDetails.getName());
			category.setActive(categoryDetails.isActive());
			subcategoriesRepository.save(category);
			return "redirect:/admin/subcategories";
		} else {
			return "not_found";
		}
	}

	@GetMapping("/{id}/delete")
	public String deleteCategory(@PathVariable Long id) {
		subcategoriesRepository.deleteById(id);
		return "redirect:/admin/subcategories";
	}
	
	

    @GetMapping("/deleteSelected")
    public String deleteSelectedCategories(@RequestParam("selectedIds") List<Long> selectedIds) {
        subcategoriesRepository.deleteAllById(selectedIds);
        return "redirect:/admin/subcategories";
    }

    @PostMapping("/activate")
    public String activateSelectedCategories(@RequestBody List<Long> selectedIds) {
        List<SubCatrgories> categories = subcategoriesRepository.findAllById(selectedIds);
        categories.forEach(category -> category.setActive(true));
        subcategoriesRepository.saveAll(categories);
        return "redirect:/admin/subcategories";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedCategories(@RequestBody List<Long> selectedIds) {
        List<SubCatrgories> categories = subcategoriesRepository.findAllById(selectedIds);
        categories.forEach(category -> category.setActive(false));
        subcategoriesRepository.saveAll(categories);
        return "redirect:/admin/subcategories";
    }

   
}
