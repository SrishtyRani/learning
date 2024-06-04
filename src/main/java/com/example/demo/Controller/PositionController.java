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

import com.example.demo.Model.Position;

import com.example.demo.Repository.PositionRepository;
@Controller
@RequestMapping("/admin/position")
public class PositionController {
	
	@Autowired
	private  PositionRepository positionrepo;

	
	@PostMapping
	public String createPosition(@ModelAttribute("Position") Position Position) {
		positionrepo.save(Position);
		
		return "redirect:/admin/position";
	}

@GetMapping
public String getAllPosition(Model model) {
	List<Position> Position = positionrepo.findAll();
	model.addAttribute("Position", Position);
	
	return "Position_list";
}


@GetMapping("/{id}")
public String getPositionById(@PathVariable Long id, Model model) {
	Optional<Position> Position = positionrepo.findById(id);
	if (Position.isPresent()) {
		model.addAttribute("Position", Position.get());
		return "Position_details";
	} else {
		return "not_found";
	}
}


@GetMapping("/{id}/edit")
public String showUpdateForm(@PathVariable Long id, Model model) {
	Optional<Position> Position = positionrepo.findById(id);
	if (Position.isPresent()) {
		model.addAttribute("Position", Position.get());
		return "Position_edit_form";
	} else {
		return "not_found";
	}
}

@PostMapping("/{id}/edit")
public String updatePosition(@PathVariable Long id, @ModelAttribute("Position") Position PositionDetails) {
	Optional<Position> optionalPosition = positionrepo.findById(id);
	if (optionalPosition.isPresent()) {
		Position Position = optionalPosition.get();
		Position.setName(PositionDetails.getName());
		Position.setActive(PositionDetails.isActive());
		positionrepo.save(Position);
		return "redirect:/admin/position";
	} else {
		return "not_found";
	}
}

@GetMapping("/{id}/delete")
public String deletePosition(@PathVariable Long id) {
	positionrepo.deleteById(id);
	return "redirect:/admin/position";
}

  @GetMapping("/deleteSelected")
    public String deleteSelectedPosition(@RequestParam("selectedIds") List<Long> selectedIds) {
        positionrepo.deleteAllById(selectedIds);
        return "redirect:/admin/position";
    }

    @PostMapping("/activate")
    public String activateSelectedPosition(@RequestBody List<Long> selectedIds) {
        List<Position> Position = positionrepo.findAllById(selectedIds);
        Position.forEach(position -> position.setActive(true));
        positionrepo.saveAll(Position);
        return "redirect:/admin/position";
    }

    @PostMapping("/deactivateSelected")
    public String deactivateSelectedPosition(@RequestBody List<Long> selectedIds) {
        List<Position> Position = positionrepo.findAllById(selectedIds);
        Position.forEach(position -> position.setActive(false));
        positionrepo.saveAll(Position);
        return "redirect:/admin/position";
   
	
    }




}
