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

import com.example.demo.Model.ConstructionStatus;

import com.example.demo.Repository.ConstructionStatusRepository;

@Controller
@RequestMapping("/admin/construction")
public class ConstructionStatusController {
	
	@Autowired
	private ConstructionStatusRepository constructionstatusrepo;

	
	@PostMapping
	public String createConstructionStatus(@ModelAttribute("ConstructionStatus") ConstructionStatus ConstructionStatus) {
		constructionstatusrepo.save(ConstructionStatus);
		
		return "redirect:/admin/construction";
	}

@GetMapping
public String getAllConstructionStatus(Model model) {
	List<ConstructionStatus> ConstructionStatus = constructionstatusrepo.findAll();
	model.addAttribute("ConstructionStatus", ConstructionStatus);
	
	return "ConstructionStatus_list";
}


@GetMapping("/{id}")
public String getConstructionStatusById(@PathVariable Long id, Model model) {
	Optional<ConstructionStatus> ConstructionStatus = constructionstatusrepo.findById(id);
	if (ConstructionStatus.isPresent()) {
		model.addAttribute("ConstructionStatus", ConstructionStatus.get());
		return "ConstructionStatus_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<ConstructionStatus> ConstructionStatus = constructionstatusrepo.findById(id);
	if (ConstructionStatus.isPresent()) {
		model.addAttribute("ConstructionStatus", ConstructionStatus.get());
		return "ConstructionStatus_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateConstructionStatus(@PathVariable Long id, @ModelAttribute("ConstructionStatus") ConstructionStatus ConstructionStatusDetails) {
	Optional<ConstructionStatus> optionalConstructionStatus = constructionstatusrepo.findById(id);
	if (optionalConstructionStatus.isPresent()) {
		ConstructionStatus ConstructionStatus = optionalConstructionStatus.get();
		ConstructionStatus.setName(ConstructionStatusDetails.getName());
		ConstructionStatus.setActive(ConstructionStatusDetails.isActive());
		constructionstatusrepo.save(ConstructionStatus);
		return "redirect:/admin/construction";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteConstructionStatus(@PathVariable Long id) {
	constructionstatusrepo.deleteById(id);
	return "redirect:/admin/ConstructionStatus";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedConstructionStatus(@RequestParam("selectedIds") List<Long> selectedIds) {
        constructionstatusrepo.deleteAllById(selectedIds);
        return "redirect:/admin/construction";
    }

    @PostMapping("/activate")
    public String activateSelectedConstructionStatus(@RequestBody List<Long> selectedIds) {
        List<ConstructionStatus> ConstructionStatus = constructionstatusrepo.findAllById(selectedIds);
        ConstructionStatus.forEach(constructionStatus -> constructionStatus.setActive(true));
        constructionstatusrepo.saveAll(ConstructionStatus);
        return "redirect:/admin/construction";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedConstructionStatus(@RequestBody List<Long> selectedIds) {
        List<ConstructionStatus> ConstructionStatus = constructionstatusrepo.findAllById(selectedIds);
        ConstructionStatus.forEach(constructionStatus -> constructionStatus.setActive(false));
        constructionstatusrepo.saveAll(ConstructionStatus);
        return "redirect:/admin/construction";
   
	
    }






}
