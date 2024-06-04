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

import com.example.demo.Model.Accessories;
import com.example.demo.Repository.AccessoriesRepository;


@Controller
@RequestMapping("/admin/accessories")
public class AccessoriesController {
	
	@Autowired
	private AccessoriesRepository accessoriesrepository;

	
	@PostMapping
	public String createAccessories(@ModelAttribute("Accessories") Accessories Accessories) {
		accessoriesrepository.save(Accessories);
		
		return "redirect:/admin/accessories";
	}

@GetMapping
public String getAllAccessories(Model model) {
	List<Accessories> Accessories = accessoriesrepository.findAll();
	model.addAttribute("Accessories", Accessories);
	
	return "Accessories_list";
}


@GetMapping("/{id}")
public String getAccessoriesById(@PathVariable Long id, Model model) {
	Optional<Accessories> Accessories = accessoriesrepository.findById(id);
	if (Accessories.isPresent()) {
		model.addAttribute("Accessories", Accessories.get());
		return "Accessories_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Accessories> Accessories = accessoriesrepository.findById(id);
	if (Accessories.isPresent()) {
		model.addAttribute("Accessories", Accessories.get());
		return "Accessories_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateAccessories(@PathVariable Long id, @ModelAttribute("Accessories") Accessories AccessoriesDetails) {
	Optional<Accessories> optionalAccessories = accessoriesrepository.findById(id);
	if (optionalAccessories.isPresent()) {
		Accessories Accessories = optionalAccessories.get();
		Accessories.setName(AccessoriesDetails.getName());
		Accessories.setActive(AccessoriesDetails.isActive());
		accessoriesrepository.save(Accessories);
		return "redirect:/admin/accessories";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteAccessories(@PathVariable Long id) {
	accessoriesrepository.deleteById(id);
	return "redirect:/admin/accessories";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedAccessories(@RequestParam("selectedIds") List<Long> selectedIds) {
        accessoriesrepository.deleteAllById(selectedIds);
        return "redirect:/admin/accessories";
    }

    @PostMapping("/activate")
    public String activateSelectedAccessories(@RequestBody List<Long> selectedIds) {
        List<Accessories> Accessories = accessoriesrepository.findAllById(selectedIds);
        Accessories.forEach(accessories -> accessories.setActive(true));
        accessoriesrepository.saveAll(Accessories);
        return "redirect:/admin/accessories";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedAccessories(@RequestBody List<Long> selectedIds) {
        List<Accessories> Accessories = accessoriesrepository.findAllById(selectedIds);
        Accessories.forEach(accessories -> accessories.setActive(false));
        accessoriesrepository.saveAll(Accessories);
        return "redirect:/admin/accessories";
   
	
    }

}


