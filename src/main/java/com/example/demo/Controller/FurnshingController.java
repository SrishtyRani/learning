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

import com.example.demo.Model.Furnshing;
import com.example.demo.Repository.FurnshingRepository;

@Controller
@RequestMapping("/admin/furnshing")
public class FurnshingController {
	
	@Autowired
	private  FurnshingRepository furnshingrepo;

	
	@PostMapping
	public String createFurnshing(@ModelAttribute("Furnshing") Furnshing Furnshing) {
		furnshingrepo.save(Furnshing);
		
		return "redirect:/admin/furnshing";
	}

@GetMapping
public String getAllFurnshing(Model model) {
	List<Furnshing> Furnshing = furnshingrepo.findAll();
	model.addAttribute("Furnshing", Furnshing);
	
	return "Furnshing_list";
}


@GetMapping("/{id}")
public String getFurnshingById(@PathVariable Long id, Model model) {
	Optional<Furnshing> Furnshing = furnshingrepo.findById(id);
	if (Furnshing.isPresent()) {
		model.addAttribute("Furnshing", Furnshing.get());
		return "Furnshing_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Furnshing> Furnshing = furnshingrepo.findById(id);
	if (Furnshing.isPresent()) {
		model.addAttribute("Furnshing", Furnshing.get());
		return "Furnshing_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateFurnshing(@PathVariable Long id, @ModelAttribute("Furnshing") Furnshing FurnshingDetails) {
	Optional<Furnshing> optionalFurnshing = furnshingrepo.findById(id);
	if (optionalFurnshing.isPresent()) {
		Furnshing Furnshing = optionalFurnshing.get();
		Furnshing.setName(FurnshingDetails.getName());
		Furnshing.setActive(FurnshingDetails.isActive());
		furnshingrepo.save(Furnshing);
		return "redirect:/admin/furnshing";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteFurnshing(@PathVariable Long id) {
	furnshingrepo.deleteById(id);
	return "redirect:/admin/furnshing";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedFurnshing(@RequestParam("selectedIds") List<Long> selectedIds) {
        furnshingrepo.deleteAllById(selectedIds);
        return "redirect:/admin/furnshing";
    }

    @PostMapping("/activate")
    public String activateSelectedFurnshing(@RequestBody List<Long> selectedIds) {
        List<Furnshing> Furnshing = furnshingrepo.findAllById(selectedIds);
        Furnshing.forEach(furnshing -> furnshing.setActive(true));
        furnshingrepo.saveAll(Furnshing);
        return "redirect:/admin/furnshing";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedFurnshing(@RequestBody List<Long> selectedIds) {
        List<Furnshing> Furnshing = furnshingrepo.findAllById(selectedIds);
        Furnshing.forEach(furnshing -> furnshing.setActive(false));
        furnshingrepo.saveAll(Furnshing);
        return "redirect:/admin/furnshing";
   
	
    }




}
