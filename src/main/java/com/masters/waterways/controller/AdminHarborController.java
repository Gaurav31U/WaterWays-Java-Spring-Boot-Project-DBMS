package com.masters.waterways.controller;

import com.masters.waterways.daos.HarborDao;
import com.masters.waterways.models.Harbor;
import com.masters.waterways.models.HarborStatusProvider;
import com.masters.waterways.models.HarborStatusProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.masters.waterways.daos.HarborDao;
import com.masters.waterways.models.Harbor;

@Controller
public class AdminHarborController {
	@Autowired
	private HarborDao harborDao;
	
	@GetMapping("/admin/harbor")
	public String listHarbors(Model model) {
		model.addAttribute("harbors", harborDao.getAll());
		model.addAttribute("harborstatuses", HarborStatusProvider.getHarborStatusDesc);
		return "HarborList";
	}

	@GetMapping("/admin/harbor/add")
	public String addHarborForm(Model model) {
		Harbor new_harbor =new Harbor();
		model.addAttribute("new_harbor", new_harbor);
		model.addAttribute("harbors", harborDao.getAll());
		model.addAttribute("harborstatuses", HarborStatusProvider.getHarborStatusCode);
		return "AddHarborForm";
	}
	
	@PostMapping("/admin/harbor/add")
	public String saveHarbor(@ModelAttribute("new_harbor") Harbor new_harbor) {
		harborDao.insert(new_harbor);
		return "redirect:/admin/harbor";
	}
	
//	@GetMapping("/admin/harbor/{id}/upddate")
//	public String updateHarborForm(@PathVariable int id, Model model) {
//		model.addAttribute("harbor", harborDao.getById(id));
//		model.addAttribute("harbors", harborDao.getAll());
//		model.addAttribute("harborstatuses", HarborStatusProvider.getHarborStatusCode);
//		return "UpdateHarborForm";
//	}
	
//	@PostMapping("/admin/harbor/{id}/update")
//	public String updateharbor(@PathVariable int id,
//			@ModelAttribute("harbor") Harbor harbor,
//			Model model) {
//		harborDao.update(harbor, id);
//		return "redirect:/admin/harbor";
//	}

	@GetMapping("/admin/harbor/{id}/update")
	public String updateHarborStatus(@PathVariable int id, Model model) {
		Harbor harbor=harborDao.getById(id);
		if (HarborStatusProvider.getHarborStatusDesc.get(harbor.getHarborStatusCode()).equals("SUSPENDED")){
			harborDao.setActive(id);
		}
		else {
			harborDao.setSuspended(id);
		}
		return "redirect:/admin/harbor";
	}
	

}
