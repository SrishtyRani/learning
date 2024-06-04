package com.example.demo.Controller;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Model.Categories;
import com.example.demo.Model.FormData;
import com.example.demo.Model.SubCatrgories;
import com.example.demo.Repository.CategoriesRepository;
import com.example.demo.Repository.FormDataRepository;
import com.example.demo.Repository.SubCategoriesRepository;


@Controller
@RequestMapping("/admin/Formdata")
public class FormdataController {
	
	
	@Autowired
	private FormDataRepository formdatarepo	;
	
	@Autowired
	private SubCategoriesRepository subcategoriesRepository;
	
	
    @Autowired
    private CategoriesRepository categoriesRepository;
	

   
    
    @GetMapping("/api/subcategories/{categoryId}")
    @ResponseBody
    public List<SubCatrgories> getSubcategoriesByParentCategoryId(@PathVariable Long categoryId) {
        return subcategoriesRepository.findByParentCategory_Id(categoryId);
    }
    

    @PostMapping("/submit-form")
    public ResponseEntity<String> submitForm(@RequestBody FormData formData) {
        try {
        	System.out.println(formData.getSubcategory());
        	formdatarepo.save(formData);
            return ResponseEntity.status(HttpStatus.CREATED).body("Form submitted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Form submission failed");
        }
    }
     
    @GetMapping
    public String getFormData(Model model) {
        List<FormData> formDataList = formdatarepo.findAll();
        List<SubCatrgories> subcategoryList = subcategoriesRepository.findAll(); 
        List<Categories> categories = categoriesRepository.findAll();

        model.addAttribute("formDataList", formDataList);
        model.addAttribute("subcategoryList", subcategoryList);
        model.addAttribute("categories", categories);
        
        return "FormData";
    }

    public String findSubcategoryName(Long subcategory) {
        Optional<SubCatrgories> matchingSubcategory = subcategoriesRepository.findById(subcategory);
        return matchingSubcategory.map(SubCatrgories::getName).orElse("Subcategory not found");
    }
    
    @GetMapping("/{id}/delete")
	public String deleteCategory(@PathVariable Long id) {
    	formdatarepo.deleteById(id);
		return "redirect:/admin/Formdata";
	}
    
    @GetMapping("/deleteSelected")
    public String deleteSelectedFormData(@RequestParam("selectedIds") List<Long> selectedIds) {
    	formdatarepo.deleteAllById(selectedIds);
        return "redirect:/admin/Formdata";
    }

    @GetMapping("/{id}/edit")
    public String editFormData(@PathVariable Long id, Model model) {
        Optional<FormData> formDataOptional = formdatarepo.findById(id);
        if (formDataOptional.isPresent()) {
            FormData formData = formDataOptional.get();
            model.addAttribute("formData", formData);
            List<SubCatrgories> subcategoryList = subcategoriesRepository.findAll();
            model.addAttribute("subcategoryList", subcategoryList);
            List<Categories> categories = categoriesRepository.findAll();
            model.addAttribute("categories", categories);
            return "editForm";
        } else {
            return "redirect:/admin/Formdata";
        }
    }
    
 
    @PostMapping("/update")
    @Transactional 
    public ResponseEntity<String> updateFormData(@RequestBody FormData updatedFormData) {
        try {
            Optional<FormData> existingFormDataOptional = formdatarepo.findById(updatedFormData.getId());
            if (existingFormDataOptional.isPresent()) {
                FormData existingFormData = existingFormDataOptional.get();
                
                System.out.println("Existing Form ID: " + existingFormData.getId());
                System.out.println("Updated Form ID: " + updatedFormData.getId());
                
                existingFormData.setCategory(updatedFormData.getCategory());
                existingFormData.setSubcategory(updatedFormData.getSubcategory());
                existingFormData.setCheckedBoxes(updatedFormData.getCheckedBoxes());
                
                formdatarepo.save(existingFormData);
                return ResponseEntity.status(HttpStatus.CREATED).body("Form submitted successfully");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Form submission failed");
        }
    }

}


