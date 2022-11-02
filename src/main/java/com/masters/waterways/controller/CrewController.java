package com.masters.waterways.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.masters.waterways.daos.crewdao;
import com.masters.waterways.models.Crew;

@Controller
public class CrewController {
	@Autowired
	private crewdao crewdao;
	
	@GetMapping("/admin/crew")
	public String listcrews(Model model) {
		model.addAttribute("crews", crewdao.getall());
		return "crewList";
	}
	@GetMapping("/admin/crew/new")
	public String createcrewform(Model model) {
		Crew newcrew =new Crew();
		model.addAttribute("newcrew", newcrew);
		return "createcrewform";
	}
	
	@PostMapping("/admin/crew/new")
	public String savecrew(@ModelAttribute("newcrew") Crew newcrew) {
		crewdao.save(newcrew);	
		return "redirect:/admin/crew";
	}
	
	@GetMapping("/admin/crew/edit/{id}")
	public String editcrewform(@PathVariable int id, Model model) {
		model.addAttribute("crew", crewdao.getbyid(id));
		return "editcrewform";
	}
	
	@PostMapping("admin/crew/edit/{id}")
	public String updateStudent(@PathVariable int id,
			@ModelAttribute("crew") Crew crew,
			Model model) {
		crewdao.update(crew, id);
		return "redirect:/admin/crew";
	}
	
	@GetMapping("/admin/crew/delete/{id}")
	public String deletecrew(@PathVariable int id) {
		crewdao.delete(id);
		return "redirect:/admin/crew";
	}
	
}
