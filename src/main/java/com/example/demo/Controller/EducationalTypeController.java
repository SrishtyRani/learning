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

import com.example.demo.Model.EducationalType;

import com.example.demo.Repository.EducationalTypeRepository;

@Controller
@RequestMapping("/admin/educationaltype")
public class EducationalTypeController {
	

	@Autowired
	private  EducationalTypeRepository educationalrepo;

	
	@PostMapping
	public String createEducationalType(@ModelAttribute("EducationalType") EducationalType EducationalType) {
		educationalrepo.save(EducationalType);
		
		return "redirect:/admin/educationaltype";
	}

@GetMapping
public String getAllEducationalType(Model model) {
	List<EducationalType> EducationalType = educationalrepo.findAll();
	model.addAttribute("EducationalType", EducationalType);
	
	return "EducationalType_list";
}


@GetMapping("/{id}")
public String getEducationalTypeById(@PathVariable Long id, Model model) {
	Optional<EducationalType> EducationalType = educationalrepo.findById(id);
	if (EducationalType.isPresent()) {
		model.addAttribute("EducationalType", EducationalType.get());
		return "EducationalType_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<EducationalType> EducationalType = educationalrepo.findById(id);
	if (EducationalType.isPresent()) {
		model.addAttribute("EducationalType", EducationalType.get());
		return "EducationalType_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateEducationalType(@PathVariable Long id, @ModelAttribute("EducationalType") EducationalType EducationalTypeDetails) {
	Optional<EducationalType> optionalEducationalType = educationalrepo.findById(id);
	if (optionalEducationalType.isPresent()) {
		EducationalType EducationalType = optionalEducationalType.get();
		EducationalType.setName(EducationalTypeDetails.getName());
		EducationalType.setActive(EducationalTypeDetails.isActive());
		educationalrepo.save(EducationalType);
		return "redirect:/admin/educationaltype";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteEducationalType(@PathVariable Long id) {
	educationalrepo.deleteById(id);
	return "redirect:/admin/educationaltype";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedEducationalType(@RequestParam("selectedIds") List<Long> selectedIds) {
        educationalrepo.deleteAllById(selectedIds);
        return "redirect:/admin/educationaltype";
    }

    @PostMapping("/activate")
    public String activateSelectedEducationalType(@RequestBody List<Long> selectedIds) {
        List<EducationalType> EducationalType = educationalrepo.findAllById(selectedIds);
        EducationalType.forEach(educationalType -> educationalType.setActive(true));
        educationalrepo.saveAll(EducationalType);
        return "redirect:/admin/educationaltype";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedEducationalType(@RequestBody List<Long> selectedIds) {
        List<EducationalType> EducationalType = educationalrepo.findAllById(selectedIds);
        EducationalType.forEach(educationalType -> educationalType.setActive(false));
        educationalrepo.saveAll(EducationalType);
        return "redirect:/admin/educationaltype";
   
	
    }




}
