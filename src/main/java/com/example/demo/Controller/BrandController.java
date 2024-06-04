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

import com.example.demo.Model.Brand;

import com.example.demo.Repository.BrandRepository;



@Controller
@RequestMapping("/admin/Brand")
public class BrandController {
	
	@Autowired
	private BrandRepository brandRepository;

	
	@PostMapping
	public String createbrand(@ModelAttribute("brand") Brand brand) {
		brandRepository.save(brand);
		
		return "redirect:/admin/Brand";
	}

@GetMapping
public String getAllBrand(Model model) {
	List<Brand> Brand = brandRepository.findAll();
	model.addAttribute("Brand", Brand);
	
	return "Brand_list";
}


@GetMapping("/{id}")
public String getbrandById(@PathVariable Long id, Model model) {
	Optional<Brand> brand = brandRepository.findById(id);
	if (brand.isPresent()) {
		model.addAttribute("brand", brand.get());
		return "brand_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Brand> brand = brandRepository.findById(id);
	if (brand.isPresent()) {
		model.addAttribute("brand", brand.get());
		return "brand_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updatebrand(@PathVariable Long id, @ModelAttribute("brand") Brand brandDetails) {
	Optional<Brand> optionalbrand = brandRepository.findById(id);
	if (optionalbrand.isPresent()) {
		Brand brand = optionalbrand.get();
		brand.setName(brandDetails.getName());
		brand.setActive(brandDetails.isActive());
		brandRepository.save(brand);
		return "redirect:/admin/Brand";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deletebrand(@PathVariable Long id) {
	brandRepository.deleteById(id);
	return "redirect:/admin/Brand";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedBrand(@RequestParam("selectedIds") List<Long> selectedIds) {
        brandRepository.deleteAllById(selectedIds);
        return "redirect:/admin/Brand";
    }

    @PostMapping("/activate")
    public String activateSelectedBrand(@RequestBody List<Long> selectedIds) {
        List<Brand> Brand = brandRepository.findAllById(selectedIds);
        Brand.forEach(brand -> brand.setActive(true));
        brandRepository.saveAll(Brand);
        return "redirect:/admin/Brand";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedBrand(@RequestBody List<Long> selectedIds) {
        List<Brand> Brand = brandRepository.findAllById(selectedIds);
        Brand.forEach(brand -> brand.setActive(false));
        brandRepository.saveAll(Brand);
        return "redirect:/admin/Brand";
   
	
    }

}
