package com.example.demo.Controller;


import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Model.Categories;
import com.example.demo.Model.FormData;
import com.example.demo.Model.FormDataSave;
import com.example.demo.Model.SubCatrgories;
import com.example.demo.Repository.CategoriesRepository;

import com.example.demo.Repository.FormDataRepository;
import com.example.demo.Repository.FormDataSaveRepository;

import com.example.demo.Repository.SubCategoriesRepository;





@Controller
@RequestMapping("/admin/Formsavedata")
public class FormDataSaveController {
	
	@Autowired
	  private  	FormDataSaveRepository  formDataSaveRepository ;
	
    @Autowired
    private FormDataRepository formDataRepository; 
    
    @Autowired
    private SubCategoriesRepository subcategoryRepository; 
    
    @Autowired
    private CategoriesRepository categoryRepository; 
    
   


    
    @GetMapping
    public String getAllSavedtitle(Model model) {
        List<FormDataSave> formDataList = formDataSaveRepository.findAll();
        
        model.addAttribute("formDataSaveList", formDataList); 
        
        for (FormDataSave formDataSave : formDataList) {
            Optional<FormData> formDataOptional = formDataRepository.findById(formDataSave.getFormid());
            
            if (formDataOptional.isPresent()) {
                FormData formData = formDataOptional.get();
                
                Optional<SubCatrgories> subcategoryOptional = subcategoryRepository.findById(formData.getSubcategory());
                
                if (subcategoryOptional.isPresent()) {
                    SubCatrgories subcategory = subcategoryOptional.get();
                    
                    Optional<Categories> categoryOptional = categoryRepository.findById(formData.getCategory());
                    
                    System.out.println(formDataSave.getPathsList());
                    
                    if (categoryOptional.isPresent()) {
                        Categories category = categoryOptional.get();
                        
                        model.addAttribute("formDataSave", formDataSave);
                        model.addAttribute("category", category);
                        model.addAttribute("subcategory", subcategory);
                        model.addAttribute("title", formDataSave.getTitle());
                    }
                }
            }
        }
        
        return "FormDataSave"; 
    }



    @GetMapping("/deleteSelected")
      public String deleteSelectedFacing(@RequestParam("selectedIds") List<Long> selectedIds) {
    	
    	formDataSaveRepository.deleteAllById(selectedIds);
          return "redirect:/admin/Formsavedata";
      }

      @PostMapping("/activate")
      public String activateSelectedFacing(@RequestBody List<Long> selectedIds) {
          List<FormDataSave> Facing = formDataSaveRepository.findAllById(selectedIds);
          Facing.forEach(facing -> facing.setActive(true));
          formDataSaveRepository.saveAll(Facing);
          return "redirect:/admin/Formsavedata";
      }

      @PostMapping("/deactivateSelected")
      public String deactivateSelectedFacing(@RequestBody List<Long> selectedIds) {
          List<FormDataSave> Facing = formDataSaveRepository.findAllById(selectedIds);
          Facing.forEach(facing -> facing.setActive(false));
          formDataSaveRepository.saveAll(Facing);
          return "redirect:/admin/Formsavedata";
     
  	
      }


}
