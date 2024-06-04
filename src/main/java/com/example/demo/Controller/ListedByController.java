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

import com.example.demo.Model.ListedBy;

import com.example.demo.Repository.ListedByRepository;

@Controller
@RequestMapping("/admin/listedBy")
public class ListedByController {
	
	
	@Autowired
	private  ListedByRepository listedrepo;

	
	@PostMapping
	public String createListedBy(@ModelAttribute("ListedBy") ListedBy ListedBy) {
		listedrepo.save(ListedBy);
		
		return "redirect:/admin/listedBy";
	}

@GetMapping
public String getAllListedBy(Model model) {
	List<ListedBy> ListedBy = listedrepo.findAll();
	model.addAttribute("ListedBy", ListedBy);
	
	return "ListedBy_list";
}


@GetMapping("/{id}")
public String getListedByById(@PathVariable Long id, Model model) {
	Optional<ListedBy> ListedBy = listedrepo.findById(id);
	if (ListedBy.isPresent()) {
		model.addAttribute("ListedBy", ListedBy.get());
		return "ListedBy_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<ListedBy> ListedBy = listedrepo.findById(id);
	if (ListedBy.isPresent()) {
		model.addAttribute("ListedBy", ListedBy.get());
		return "ListedBy_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updateListedBy(@PathVariable Long id, @ModelAttribute("ListedBy") ListedBy ListedByDetails) {
	Optional<ListedBy> optionalListedBy = listedrepo.findById(id);
	if (optionalListedBy.isPresent()) {
		ListedBy ListedBy = optionalListedBy.get();
		ListedBy.setName(ListedByDetails.getName());
		ListedBy.setActive(ListedByDetails.isActive());
		listedrepo.save(ListedBy);
		return "redirect:/admin/listedBy";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deleteListedBy(@PathVariable Long id) {
	listedrepo.deleteById(id);
	return "redirect:/admin/listedBy";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedListedBy(@RequestParam("selectedIds") List<Long> selectedIds) {
        listedrepo.deleteAllById(selectedIds);
        return "redirect:/admin/listedBy";
    }

    @PostMapping("/activate")
    public String activateSelectedListedBy(@RequestBody List<Long> selectedIds) {
        List<ListedBy> ListedBy = listedrepo.findAllById(selectedIds);
        ListedBy.forEach(listedBy -> listedBy.setActive(true));
        listedrepo.saveAll(ListedBy);
        return "redirect:/admin/listedBy";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedListedBy(@RequestBody List<Long> selectedIds) {
        List<ListedBy> ListedBy = listedrepo.findAllById(selectedIds);
        ListedBy.forEach(listedBy -> listedBy.setActive(false));
        listedrepo.saveAll(ListedBy);
        return "redirect:/admin/listedBy";
   
	
    }

	
	
	

}
