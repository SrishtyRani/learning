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

import com.example.demo.Model.Transmission;
import com.example.demo.Repository.TransmissionRepository;

@Controller
@RequestMapping("/admin/transmissions")
public class TransmissionController {

    @Autowired
    private TransmissionRepository transmissionRepository;

    @PostMapping
    public String createTransmission(@ModelAttribute("transmissionForm") Transmission transmissionForm) {
        transmissionRepository.save(transmissionForm);
        return "redirect:/admin/transmissions";
    }

    @GetMapping
    public String getAllTransmissions(Model model) {
        List<Transmission> transmissions = transmissionRepository.findAll();
        model.addAttribute("transmissions", transmissions);
        return "transmissions_list";
    }

    @GetMapping("/{id}")
    public String getTransmissionById(@PathVariable Long id, Model model) {
        Optional<Transmission> transmissionOpt = transmissionRepository.findById(id);
        if (transmissionOpt.isPresent()) {
            model.addAttribute("transmission", transmissionOpt.get());
            return "transmission_details";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<Transmission> optionalTransmission = transmissionRepository.findById(id);
        if (optionalTransmission.isPresent()) {
            Transmission transmission = optionalTransmission.get();
            model.addAttribute("transmission", transmission);
            return "transmission_edit_form";
        } else {
            return "not_found";
        }
    }

    @PostMapping("/{id}/edit")
    public String updateTransmission(@PathVariable Long id, @ModelAttribute("transmission") Transmission transmissionDetails) {
        Optional<Transmission> optionalTransmission = transmissionRepository.findById(id);
        if (optionalTransmission.isPresent()) {
            Transmission transmission = optionalTransmission.get();
            transmission.setName(transmissionDetails.getName());
            transmission.setActive(transmissionDetails.isActive());
            transmissionRepository.save(transmission);
            return "redirect:/admin/transmissions";
        } else {
            return "not_found";
        }
    }

    @GetMapping("/{id}/delete")
    public String deleteTransmission(@PathVariable Long id) {
        transmissionRepository.deleteById(id);
        return "redirect:/admin/transmissions";
    }

    @GetMapping("/deleteSelected")
    public String deleteSelectedTransmissions(@RequestParam("selectedIds") List<Long> selectedIds) {
        transmissionRepository.deleteAllById(selectedIds);
        return "redirect:/admin/transmissions";
    }

    @PostMapping("/activate")
    public String activateSelectedTransmissions(@RequestBody List<Long> selectedIds) {
        List<Transmission> transmissions = transmissionRepository.findAllById(selectedIds);
        transmissions.forEach(transmission -> transmission.setActive(true));
        transmissionRepository.saveAll(transmissions);
        return "redirect:/admin/transmissions";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedTransmissions(@RequestBody List<Long> selectedIds) {
        List<Transmission> transmissions = transmissionRepository.findAllById(selectedIds);
        transmissions.forEach(transmission -> transmission.setActive(false));
        transmissionRepository.saveAll(transmissions);
        return "redirect:/admin/transmissions";
    }
}

