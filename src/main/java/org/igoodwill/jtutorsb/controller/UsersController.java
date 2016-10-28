package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.Users;
import org.igoodwill.jtutorsb.repositories.UsersRepository;
import org.igoodwill.jtutorsb.service.MailService;
import org.igoodwill.jtutorsb.service.UsersService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
// @RequestMapping("/user/*")
public class UsersController {
	private Logger log = LoggerFactory.getLogger(UsersController.class);

	@Value("${app.user.verification}")
	private Boolean requireActivation;

	@Value("${app.user.root}")
	private String userRoot;

	@Autowired
	private UsersRepository usersRepository;

	@Autowired
	protected AuthenticationManager authenticationManager;

	@Autowired
	private UsersService usersService;

	@Autowired
	private MailService mailService;

	@RequestMapping("/login")
	public String login(Users users) {
		return "user/login";
	}

	@RequestMapping("/user/list")
	public String list(ModelMap map) {
		Iterable<Users> users = this.usersRepository.findAll();
		map.addAttribute("users", users);
		return "user/list";
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.GET)
	public String register(Users users) {
		return "user/register";
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	public String registerPost(@Valid Users users, BindingResult result) {
		if (result.hasErrors()) {
			return "user/register";
		}

		Users registeredUser = usersService.register(users);
		if (registeredUser != null) {
			mailService.sendNewRegistration(users.getEmail(), registeredUser.getToken());
			if (!requireActivation) {
				usersService.autoLogin(users.getUsername());
				return "redirect:/";
			}
			return "user/register-success";
		} else {
			log.error("User already exists: " + users.getUsername());
			result.rejectValue("email", "error.alreadyExists", "This username or email already exists, please try to reset password instead.");
			return "user/register";
		}
	}

	@RequestMapping(value = "/user/reset-password")
	public String resetPasswordEmail(Users users) {
		return "user/reset-password";
	}

	@RequestMapping(value = "/user/reset-password", method = RequestMethod.POST)
	public String resetPasswordEmailPost(Users users, BindingResult result) {
		Users u = usersRepository.findOneByEmail(users.getEmail());
		if (u == null) {
			result.rejectValue("email", "error.doesntExist", "We could not find this email in our database");
			return "user/reset-password";
		} else {
			String resetToken = usersService.createResetPasswordToken(u, true);
			mailService.sendResetPassword(users.getEmail(), resetToken);
		}
		return "user/reset-password-sent";
	}

	@RequestMapping(value = "/user/reset-password-change")
	public String resetPasswordChange(Users users, BindingResult result, Model model) {
		Users u = usersRepository.findOneByToken(users.getToken());
		if (users.getToken().equals("1") || u == null) {
			result.rejectValue("activation", "error.doesntExist", "We could not find this reset password request.");
		} else {
			model.addAttribute("username", u.getUsername());
		}
		return "user/reset-password-change";
	}

	@RequestMapping(value = "/user/reset-password-change", method = RequestMethod.POST)
	public ModelAndView resetPasswordChangePost(Users users, BindingResult result) {
		Boolean isChanged = usersService.resetPassword(users);
		if (isChanged) {
			usersService.autoLogin(users.getUsername());
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("user/reset-password-change", "error", "Password could not be changed");
		}
	}

	@RequestMapping("/user/activation-send")
	public ModelAndView activationSend(Users users) {
		return new ModelAndView("/user/activation-send");
	}

	@RequestMapping(value = "/user/activation-send", method = RequestMethod.POST)
	public ModelAndView activationSendPost(Users users, BindingResult result) {
		Users u = usersService.resetActivation(users.getEmail());
		if (u != null) {
			mailService.sendNewActivationRequest(u.getEmail(), u.getToken());
			return new ModelAndView("/user/activation-sent");
		} else {
			result.rejectValue("email", "error.doesntExist", "We could not find this email in our database");
			return new ModelAndView("/user/activation-send");
		}
	}

	@RequestMapping("/user/delete")
	public String delete(Integer id) {
		usersService.delete(id);
		return "redirect:/user/list";
	}

	@RequestMapping("/user/activate")
	public String activate(String activation) {
		Users u = usersService.activate(activation);
		if (u != null) {
			usersService.autoLogin(u);
			return "redirect:/";
		}
		return "redirect:/error?message=Could not activate with this activation code, please contact support";
	}

	@RequestMapping("/user/autologin")
	public String autoLogin(Users users) {
		usersService.autoLogin(users.getUsername());
		return "redirect:/";
	}

	@RequestMapping("/user/edit/{id}")
	public String edit(@PathVariable("id") Integer id, Users users) {
		Users u;
		Users loggedInUser = usersService.getLoggedInUser();
		if (id == 0) {
			id = loggedInUser.getId();
		}
		if (loggedInUser.getId() != id && !loggedInUser.isAdmin()) {
			return "user/permission-denied";
		} else if (loggedInUser.isAdmin()) {
			u = usersRepository.findOne(id);
		} else {
			u = loggedInUser;
		}
		users.setId(u.getId());
		users.setUsername(u.getUsername());
		users.setEmail(u.getEmail());
		users.setFirstName(u.getFirstName());
		users.setLastName(u.getLastName());

		return "/user/edit";
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST)
	public String editPost(@Valid Users users, BindingResult result) {
		if (result.hasFieldErrors("email")) {
			return "/user/edit";
		}

		if (usersService.getLoggedInUser().isAdmin()) {
			usersService.updateUser(users);
		} else {
			usersService.updateUser(usersService.getLoggedInUser().getUsername(), users);
		}

		if (usersService.getLoggedInUser().getId().equals(users.getId())) {
			// put updated user to session
			usersService.getLoggedInUser(true);
		}

		return "redirect:/user/edit/" + users.getId() + "?updated";
	}
}
