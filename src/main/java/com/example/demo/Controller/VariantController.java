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

import com.example.demo.Model.BrandModel;
import com.example.demo.Model.Variant;
import com.example.demo.Repository.BrandModelRepository;
import com.example.demo.Repository.VariantRepository;

@Controller
@RequestMapping("/admin/variants")
public class VariantController {

    @Autowired
    private VariantRepository variantRepository;

    @Autowired
    private BrandModelRepository brandModelRepository;

    @PostMapping
    public String createVariant(@ModelAttribute("variantForm") Variant variantForm,
                                @RequestParam("parentBrandModelId") Long parentBrandModelId) {

        BrandModel parentBrandModel = brandModelRepository.findById(parentBrandModelId).orElse(null);

        if (parentBrandModel == null) {
            return "redirect:/admin/variants?error=parentBrandModelNotFound";
        }

        variantForm.setParentBrandModel(parentBrandModel);
        variantRepository.save(variantForm);

        return "redirect:/admin/variants";
    }

    @GetMapping
    public String getAllVariants(Model model) {
        List<Variant> variants = variantRepository.findAll();

        List<Variant> variantsWithParent = variants.stream()
                .filter(variant -> variant.getParentBrandModel() != null)
                .collect(Collectors.toList());

        List<String> parentBrandModelNames = variantsWithParent.stream()
                .map(variant -> variant.getParentBrandModel().getName())
                .collect(Collectors.toList());

        List<BrandModel> brandModels = brandModelRepository.findAll();

        parentBrandModelNames.forEach(System.out::println);

        model.addAttribute("variants", variants);
        model.addAttribute("parentBrandModelNames", parentBrandModelNames);
        model.addAttribute("brandModels", brandModels);

        return "variants_list";
    }

    @GetMapping("/{id}")
    public String getVariantById(@PathVariable Long id, Model model) {
        Optional<Variant> variantOpt = variantRepository.findById(id);
        if (variantOpt.isPresent()) {
            model.addAttribute("variant", variantOpt.get());
            return "variant_details";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Variant> optionalVariant = variantRepository.findById(id);
        if (optionalVariant.isPresent()) {
            Variant variant = optionalVariant.get();
            model.addAttribute("variant", variant);
            List<BrandModel> brandModels = brandModelRepository.findAll();
            model.addAttribute("brandModels", brandModels);
            return "variant_edit_form";
        } else {
            return "not_found";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateVariant(@PathVariable Long id, @ModelAttribute("variant") Variant variantDetails) {
        Optional<Variant> optionalVariant = variantRepository.findById(id);
        if (optionalVariant.isPresent()) {
            Variant variant = optionalVariant.get();
            variant.setName(variantDetails.getName());
            variant.setActive(variantDetails.isActive());
            variantRepository.save(variant);
            return "redirect:/admin/variants";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteVariant(@PathVariable Long id) {
        variantRepository.deleteById(id);
        return "redirect:/admin/variants";
    }

    @GetMapping("/deleteSelected")
    public String deleteSelectedVariants(@RequestParam("selectedIds") List<Long> selectedIds) {
        variantRepository.deleteAllById(selectedIds);
        return "redirect:/admin/variants";
    }

    @PostMapping("/activate")
    public String activateSelectedVariants(@RequestBody List<Long> selectedIds) {
        List<Variant> variants = variantRepository.findAllById(selectedIds);
        variants.forEach(variant -> variant.setActive(true));
        variantRepository.saveAll(variants);
        return "redirect:/admin/variants";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedVariants(@RequestBody List<Long> selectedIds) {
        List<Variant> variants = variantRepository.findAllById(selectedIds);
        variants.forEach(variant -> variant.setActive(false));
        variantRepository.saveAll(variants);
        return "redirect:/admin/variants";
    }
}

