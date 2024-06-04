package com.example.demo.Controller;



import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;

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
import com.example.demo.Model.BrandModel;
import com.example.demo.Repository.BrandRepository;
import com.example.demo.Repository.BrandModelRepository;

@Controller
@RequestMapping("/admin/brandmodels")
public class BrandModelController {

    @Autowired
    private BrandModelRepository brandModelRepository;

    @Autowired
    private BrandRepository brandRepository;

    @PostMapping
    public String createBrandModel(@ModelAttribute("brandModelForm") BrandModel brandModelForm,
                                   @RequestParam("parentBrandId") Long parentBrandId) {

        Brand parentBrand = brandRepository.findById(parentBrandId).orElse(null);

        if (parentBrand == null) {
            return "redirect:/admin/brandmodels?error=parentBrandNotFound";
        }

        brandModelForm.setParentBrand(parentBrand);
        brandModelRepository.save(brandModelForm);

        return "redirect:/admin/brandmodels";
    }

    @GetMapping
    public String getAllBrandModels(Model model) {
        List<BrandModel> brandModels = brandModelRepository.findAll();

        List<BrandModel> brandModelsWithParent = brandModels.stream()
                .filter(brandModel -> brandModel.getParentBrand() != null)
                .collect(Collectors.toList());

        List<String> parentBrandNames = brandModelsWithParent.stream()
                .map(brandModel -> brandModel.getParentBrand().getName())
                .collect(Collectors.toList());

        List<Brand> brands = brandRepository.findAll();

        parentBrandNames.forEach(System.out::println);

        model.addAttribute("brandModels", brandModels);
        model.addAttribute("parentBrandNames", parentBrandNames);
        model.addAttribute("brands", brands);

        return "brandmodels_list";
    }

    @GetMapping("/{id}")
    public String getBrandModelById(@PathVariable Long id, Model model) {
        Optional<BrandModel> brandModelOpt = brandModelRepository.findById(id);
        if (brandModelOpt.isPresent()) {
            model.addAttribute("brandModel", brandModelOpt.get());
            return "brandmodel_details";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<BrandModel> optionalBrandModel = brandModelRepository.findById(id);
        if (optionalBrandModel.isPresent()) {
            BrandModel brandModel = optionalBrandModel.get();
            model.addAttribute("brandModel", brandModel);
            List<Brand> brands = brandRepository.findAll();
            model.addAttribute("brands", brands);
            return "brandmodel_edit_form";
        } else {
            return "not_found";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateBrandModel(@PathVariable Long id, @ModelAttribute("brandModel") BrandModel brandModelDetails) {
        Optional<BrandModel> optionalBrandModel = brandModelRepository.findById(id);
        if (optionalBrandModel.isPresent()) {
            BrandModel brandModel = optionalBrandModel.get();
            brandModel.setName(brandModelDetails.getName());
            brandModel.setActive(brandModelDetails.isActive());
            brandModelRepository.save(brandModel);
            return "redirect:/admin/brandmodels";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteBrandModel(@PathVariable Long id) {
        brandModelRepository.deleteById(id);
        return "redirect:/admin/brandmodels";
    }

    @GetMapping("/deleteSelected")
    public String deleteSelectedBrandModels(@RequestParam("selectedIds") List<Long> selectedIds) {
        brandModelRepository.deleteAllById(selectedIds);
        return "redirect:/admin/brandmodels";
    }

    @PostMapping("/activate")
    public String activateSelectedBrandModels(@RequestBody List<Long> selectedIds) {
        List<BrandModel> brandModels = brandModelRepository.findAllById(selectedIds);
        brandModels.forEach(brandModel -> brandModel.setActive(true));
        brandModelRepository.saveAll(brandModels);
        return "redirect:/admin/brandmodels";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedBrandModels(@RequestBody List<Long> selectedIds) {
        List<BrandModel> brandModels = brandModelRepository.findAllById(selectedIds);
        brandModels.forEach(brandModel -> brandModel.setActive(false));
        brandModelRepository.saveAll(brandModels);
        return "redirect:/admin/brandmodels";
    }
}



