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

import com.example.demo.Model.Property;

import com.example.demo.Repository.PropertyRepository;

@Controller
@RequestMapping("/admin/property")
public class PropertyController {
	
	@Autowired
	private  PropertyRepository propertyrepo;

	
	@PostMapping
	public String createProperty(@ModelAttribute("Property") Property Property) {
		propertyrepo.save(Property);
		
		return "redirect:/admin/property";
	}

@GetMapping
public String getAllProperty(Model model) {
	List<Property> Property = propertyrepo.findAll();
	model.addAttribute("Property", Property);
	
	return "Property_list";
}


@GetMapping("/{id}")
public String getPropertyById(@PathVariable Long id, Model model) {
	Optional<Property> Property = propertyrepo.findById(id);
	if (Property.isPresent()) {
		model.addAttribute("Property", Property.get());
		return "Property_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Property> Property = propertyrepo.findById(id);
	if (Property.isPresent()) {
		model.addAttribute("Property", Property.get());
		return "Property_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateProperty(@PathVariable Long id, @ModelAttribute("Property") Property PropertyDetails) {
	Optional<Property> optionalProperty = propertyrepo.findById(id);
	if (optionalProperty.isPresent()) {
		Property Property = optionalProperty.get();
		Property.setName(PropertyDetails.getName());
		Property.setActive(PropertyDetails.isActive());
		propertyrepo.save(Property);
		return "redirect:/admin/property";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteProperty(@PathVariable Long id) {
	propertyrepo.deleteById(id);
	return "redirect:/admin/property";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedProperty(@RequestParam("selectedIds") List<Long> selectedIds) {
        propertyrepo.deleteAllById(selectedIds);
        return "redirect:/admin/property";
    }

    @PostMapping("/activate")
    public String activateSelectedProperty(@RequestBody List<Long> selectedIds) {
        List<Property> Property = propertyrepo.findAllById(selectedIds);
        Property.forEach(property -> property.setActive(true));
        propertyrepo.saveAll(Property);
        return "redirect:/admin/property";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedProperty(@RequestBody List<Long> selectedIds) {
        List<Property> Property = propertyrepo.findAllById(selectedIds);
        Property.forEach(property -> property.setActive(false));
        propertyrepo.saveAll(Property);
        return "redirect:/admin/property";
   
	
    }



}
