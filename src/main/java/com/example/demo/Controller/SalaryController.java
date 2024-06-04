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

import com.example.demo.Model.SalaryPeriod;

import com.example.demo.Repository.SalaryPeriodRepository;

@Controller
@RequestMapping("/admin/Salary")
public class SalaryController {
	
	@Autowired
	private  SalaryPeriodRepository salayrepo;

	
	@PostMapping
	public String createSalayPeriod(@ModelAttribute("SalayPeriod") SalaryPeriod SalayPeriod) {
		salayrepo.save(SalayPeriod);
		
		return "redirect:/admin/Salary";
	}

@GetMapping
public String getAllSalayPeriod(Model model) {
	List<SalaryPeriod> SalayPeriod = salayrepo.findAll();
	model.addAttribute("SalayPeriod", SalayPeriod);
	
	return "SalayPeriod_list";
}


@GetMapping("/{id}")
public String getSalayPeriodById(@PathVariable Long id, Model model) {
	Optional<SalaryPeriod> SalayPeriod = salayrepo.findById(id);
	if (SalayPeriod.isPresent()) {
		model.addAttribute("SalayPeriod", SalayPeriod.get());
		return "SalayPeriod_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<SalaryPeriod> SalayPeriod = salayrepo.findById(id);
	if (SalayPeriod.isPresent()) {
		model.addAttribute("SalayPeriod", SalayPeriod.get());
		return "SalayPeriod_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateSalayPeriod(@PathVariable Long id, @ModelAttribute("SalayPeriod") SalaryPeriod SalayPeriodDetails) {
	Optional<SalaryPeriod> optionalSalayPeriod = salayrepo.findById(id);
	if (optionalSalayPeriod.isPresent()) {
		SalaryPeriod SalayPeriod = optionalSalayPeriod.get();
		SalayPeriod.setName(SalayPeriodDetails.getName());
		SalayPeriod.setActive(SalayPeriodDetails.isActive());
		salayrepo.save(SalayPeriod);
		return "redirect:/admin/Salary";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteSalayPeriod(@PathVariable Long id) {
	salayrepo.deleteById(id);
	return "redirect:/admin/Salary";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedSalayPeriod(@RequestParam("selectedIds") List<Long> selectedIds) {
        salayrepo.deleteAllById(selectedIds);
        return "redirect:/admin/Salary";
    }

    @PostMapping("/activate")
    public String activateSelectedSalayPeriod(@RequestBody List<Long> selectedIds) {
        List<SalaryPeriod> SalayPeriod = salayrepo.findAllById(selectedIds);
        SalayPeriod.forEach(salayPeriod -> salayPeriod.setActive(true));
        salayrepo.saveAll(SalayPeriod);
        return "redirect:/admin/Salary";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedSalayPeriod(@RequestBody List<Long> selectedIds) {
        List<SalaryPeriod> SalayPeriod = salayrepo.findAllById(selectedIds);
        SalayPeriod.forEach(salayPeriod -> salayPeriod.setActive(false));
        salayrepo.saveAll(SalayPeriod);
        return "redirect:/admin/Salary";
   
	
    }



}
