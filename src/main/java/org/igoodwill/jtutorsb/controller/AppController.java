package org.igoodwill.jtutorsb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping({ "/", "/home" })
	public String homePage(final Model model) {
		return "index";
	}
}
