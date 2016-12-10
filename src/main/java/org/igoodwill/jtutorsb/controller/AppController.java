package org.igoodwill.jtutorsb.controller;

import java.util.Date;
import java.util.Map;

import org.igoodwill.jtutorsb.model.admin.Users;
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
		Users loggedInUser = usersService.getLoggedInUser();
		model.put("isAdminOrTutor", loggedInUser.isAdmin() || loggedInUser.isTutor());
		model.put("date", new Date());
		return "home";
	}
}