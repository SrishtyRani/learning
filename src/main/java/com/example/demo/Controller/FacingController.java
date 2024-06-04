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

import com.example.demo.Model.Facing;
import com.example.demo.Repository.FacingRepository;


@Controller
@RequestMapping("/admin/facing")
public class FacingController {
	
	@Autowired
	private  FacingRepository facingrepo;

	
	@PostMapping
	public String createFacing(@ModelAttribute("Facing") Facing Facing) {
		facingrepo.save(Facing);
		
		return "redirect:/admin/facing";
	}

@GetMapping
public String getAllFacing(Model model) {
	List<Facing> Facing = facingrepo.findAll();
	model.addAttribute("Facing", Facing);
	
	return "Facing_list";
}


@GetMapping("/{id}")
public String getFacingById(@PathVariable Long id, Model model) {
	Optional<Facing> Facing = facingrepo.findById(id);
	if (Facing.isPresent()) {
		model.addAttribute("Facing", Facing.get());
		return "Facing_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Facing> Facing = facingrepo.findById(id);
	if (Facing.isPresent()) {
		model.addAttribute("Facing", Facing.get());
		return "Facing_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateFacing(@PathVariable Long id, @ModelAttribute("Facing") Facing FacingDetails) {
	Optional<Facing> optionalFacing = facingrepo.findById(id);
	if (optionalFacing.isPresent()) {
		Facing Facing = optionalFacing.get();
		Facing.setName(FacingDetails.getName());
		Facing.setActive(FacingDetails.isActive());
		facingrepo.save(Facing);
		return "redirect:/admin/facing";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteFacing(@PathVariable Long id) {
	facingrepo.deleteById(id);
	return "redirect:/admin/facing";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedFacing(@RequestParam("selectedIds") List<Long> selectedIds) {
        facingrepo.deleteAllById(selectedIds);
        return "redirect:/admin/facing";
    }

    @PostMapping("/activate")
    public String activateSelectedFacing(@RequestBody List<Long> selectedIds) {
        List<Facing> Facing = facingrepo.findAllById(selectedIds);
        Facing.forEach(facing -> facing.setActive(true));
        facingrepo.saveAll(Facing);
        return "redirect:/admin/facing";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedFacing(@RequestBody List<Long> selectedIds) {
        List<Facing> Facing = facingrepo.findAllById(selectedIds);
        Facing.forEach(facing -> facing.setActive(false));
        facingrepo.saveAll(Facing);
        return "redirect:/admin/facing";
   
	
    }




}
