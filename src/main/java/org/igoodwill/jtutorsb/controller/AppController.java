package org.igoodwill.jtutorsb.controller;

import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@GetMapping({ "/", "/home" })
	public String homePage(Map<String, Object> model) {
		model.put("date", new Date());
		return "home";
	}
}