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

import com.example.demo.Model.Vehicles;

import com.example.demo.Repository.VehiclesRepository;

@Controller
@RequestMapping("/admin/Vehicles")
public class VehiclesController {
	
	

	@Autowired
	private  VehiclesRepository vehiclesrepo;

	
	@PostMapping
	public String createVehicles(@ModelAttribute("Vehicles") Vehicles Vehicles) {
		vehiclesrepo.save(Vehicles);
		
		return "redirect:/admin/Vehicles";
	}

@GetMapping
public String getAllVehicles(Model model) {
	List<Vehicles> Vehicles = vehiclesrepo.findAll();
	model.addAttribute("Vehicles", Vehicles);
	
	return "Vehicles_list";
}


@GetMapping("/{id}")
public String getVehiclesById(@PathVariable Long id, Model model) {
	Optional<Vehicles> Vehicles = vehiclesrepo.findById(id);
	if (Vehicles.isPresent()) {
		model.addAttribute("Vehicles", Vehicles.get());
		return "Vehicles_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Vehicles> Vehicles = vehiclesrepo.findById(id);
	if (Vehicles.isPresent()) {
		model.addAttribute("Vehicles", Vehicles.get());
		return "Vehicles_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateVehicles(@PathVariable Long id, @ModelAttribute("Vehicles") Vehicles VehiclesDetails) {
	Optional<Vehicles> optionalVehicles = vehiclesrepo.findById(id);
	if (optionalVehicles.isPresent()) {
		Vehicles Vehicles = optionalVehicles.get();
		Vehicles.setName(VehiclesDetails.getName());
		Vehicles.setActive(VehiclesDetails.isActive());
		vehiclesrepo.save(Vehicles);
		return "redirect:/admin/Vehicles";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteVehicles(@PathVariable Long id) {
	vehiclesrepo.deleteById(id);
	return "redirect:/admin/Vehicles";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedVehicles(@RequestParam("selectedIds") List<Long> selectedIds) {
        vehiclesrepo.deleteAllById(selectedIds);
        return "redirect:/admin/Vehicles";
    }

    @PostMapping("/activate")
    public String activateSelectedVehicles(@RequestBody List<Long> selectedIds) {
        List<Vehicles> Vehicles = vehiclesrepo.findAllById(selectedIds);
        Vehicles.forEach(vehicles -> vehicles.setActive(true));
        vehiclesrepo.saveAll(Vehicles);
        return "redirect:/admin/Vehicles";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedVehicles(@RequestBody List<Long> selectedIds) {
        List<Vehicles> Vehicles = vehiclesrepo.findAllById(selectedIds);
        Vehicles.forEach(vehicles -> vehicles.setActive(false));
        vehiclesrepo.saveAll(Vehicles);
        return "redirect:/admin/Vehicles";
   
	
    }



}
