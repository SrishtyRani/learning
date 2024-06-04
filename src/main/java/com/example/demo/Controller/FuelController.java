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

import com.example.demo.Model.Fuel;
import com.example.demo.Repository.FuelRepository;

@Controller
@RequestMapping("/admin/Fuel")
public class FuelController {
	
		@Autowired
		private FuelRepository fuelRepository;

		
		@PostMapping
		public String createbrand(@ModelAttribute("Fuel") Fuel fuel) {
			fuelRepository.save(fuel);
			
			return "redirect:/admin/Fuel";
		}

	@GetMapping
	public String getAllBrand(Model model) {
		List<Fuel> Fuel = fuelRepository.findAll();
		model.addAttribute("Fuel", Fuel);
		
		return "Fuel_list";
	}


	@GetMapping("/{id}")
	public String getfuelById(@PathVariable Long id, Model model) {
		Optional<Fuel> fuel = fuelRepository.findById(id);
		if (fuel.isPresent()) {
			model.addAttribute("fuel", fuel.get());
			return "fuel_details";
		} else {
			return "not_found";
		}
	}


	@GetMapping("/{id}/edit")
	public String showUpdateForm(@PathVariable Long id, Model model) {
		Optional<Fuel> fuel = fuelRepository.findById(id);
		if (fuel.isPresent()) {
			model.addAttribute("fuel", fuel.get());
			return "fuel_edit_form";
		} else {
			return "not_found";
		}
	}

	@PostMapping("/{id}/edit")
	public String updatebrand(@PathVariable Long id, @ModelAttribute("fuel") Fuel fuelDetails) {
		Optional<Fuel> optionalfuel = fuelRepository.findById(id);
		if (optionalfuel.isPresent()) {
			Fuel fuel = optionalfuel.get();
			fuel.setName(fuelDetails.getName());
			fuel.setActive(fuelDetails.isActive());
			fuelRepository.save(fuel);
			return "redirect:/admin/Fuel";
		} else {
			return "not_found";
		}
	}

	@GetMapping("/{id}/delete")
	public String deletefuel(@PathVariable Long id) {
		fuelRepository.deleteById(id);
		return "redirect:/admin/Fuel";
	}

	  @GetMapping("/deleteSelected")
	    public String deleteSelectedfuel(@RequestParam("selectedIds") List<Long> selectedIds) {
	        fuelRepository.deleteAllById(selectedIds);
	        return "redirect:/admin/Fuel";
	    }

	    @PostMapping("/activate")
	    public String activateSelectedBrand(@RequestBody List<Long> selectedIds) {
	        List<Fuel> Fuel = fuelRepository.findAllById(selectedIds);
	       Fuel.forEach(fuel -> fuel.setActive(true));
	        fuelRepository.saveAll(Fuel);
	        return "redirect:/admin/Fuel";
	    }

	    @PostMapping("/deactivateSelected")
	    public String deactivateSelectedBrand(@RequestBody List<Long> selectedIds) {
	        List<Fuel> Fuel = fuelRepository.findAllById(selectedIds);
	        Fuel.forEach(fuel -> fuel.setActive(false));
	        fuelRepository.saveAll(Fuel);
	        return "redirect:/admin/Fuel";
	   
		
	    }

	}



