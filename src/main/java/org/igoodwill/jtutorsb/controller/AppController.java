package org.igoodwill.jtutorsb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AppController {

	@GetMapping({ "/", "/home" })
	public String homePage(final Model model) {
		model.addAttribute("searchQuery", "");
		return "home";
	}

	@GetMapping("/search")
	public String search(final Model model, @ModelAttribute final String searchQuery) {
		model.addAttribute("searchQuery", searchQuery);
		return "home";
	}
}