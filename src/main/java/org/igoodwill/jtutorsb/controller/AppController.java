package org.igoodwill.jtutorsb.controller;

import java.util.Date;
import java.util.Map;

import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

	@Autowired
	private UsersService usersService;

	@GetMapping({ "/", "/home" })
	public String homePage(Map<String, Object> model) {
		model.put("isAdmin", usersService.getLoggedInUser().isAdmin());
		model.put("date", new Date());
		return "home";
	}
}