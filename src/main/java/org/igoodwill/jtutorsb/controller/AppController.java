package org.igoodwill.jtutorsb.controller;

import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.igoodwill.jtutorsb.model.admin.Users;
import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;

@Controller
public class AppController {

	public void process(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext, final TemplateEngine templateEngine) throws Exception {

		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		templateEngine.process("label", ctx, response.getWriter());
	}

	@Autowired
	private UsersService usersService;

	@GetMapping({ "/", "/home" })
	public String homePage(final HttpServletRequest request, final HttpServletResponse response,
			final ServletContext servletContext, final TemplateEngine templateEngine, Locale locale,
			Map<String, Object> model) throws Exception {
		WebContext ctx = new WebContext(request, response, servletContext, request.getLocale());

		templateEngine.process("label", ctx, response.getWriter());
		Users loggedInUser = usersService.getLoggedInUser();
		model.put("isAdminOrTutor", loggedInUser.isAdmin() || loggedInUser.isTutor());
		model.put("date", new Date());
		return "home";
	}
}