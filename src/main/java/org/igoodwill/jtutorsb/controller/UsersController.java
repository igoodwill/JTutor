package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Users;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("/login")
	public String login(Users users) {
		return "user/login";
	}

	@GetMapping("/user/list")
	public String list(ModelMap map) {
		Iterable<Users> users = this.usersRepository.findAll();
		map.addAttribute("users", users);
		return "user/list";
	}

	@GetMapping("/user/register")
	public String register(Users users) {
		return "user/register";
	}

	@PostMapping("/user/register")
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
			result.rejectValue("email", "error.alreadyExists",
					"This username or email already exists, please try to reset password instead.");
			return "user/register";
		}
	}

	@GetMapping("/user/reset-password")
	public String resetPasswordEmail(Users users) {
		return "user/reset-password";
	}

	@PostMapping("/user/reset-password")
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

	@GetMapping("/user/reset-password-change")
	public String resetPasswordChange(Users users, BindingResult result, Model model) {
		Users u = usersRepository.findOneByToken(users.getToken());
		if (users.getToken().equals("1") || u == null) {
			result.rejectValue("activation", "error.doesntExist", "We could not find this reset password request.");
		} else {
			model.addAttribute("username", u.getUsername());
		}
		return "user/reset-password-change";
	}

	@PostMapping("/user/reset-password-change")
	public ModelAndView resetPasswordChangePost(Users users, BindingResult result) {
		Boolean isChanged = usersService.resetPassword(users);
		if (isChanged) {
			usersService.autoLogin(users.getUsername());
			return new ModelAndView("redirect:/");
		} else {
			return new ModelAndView("user/reset-password-change", "error", "Password could not be changed");
		}
	}

	@GetMapping("/user/activation-send")
	public ModelAndView activationSend(Users users) {
		return new ModelAndView("/user/activation-send");
	}

	@PostMapping("/user/activation-send")
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

	@GetMapping("/user/delete")
	public String delete(Integer id) {
		usersService.delete(id);
		return "redirect:/user/list";
	}

	@GetMapping("/user/activate")
	public String activate(String activation) {
		Users u = usersService.activate(activation);
		if (u != null) {
			usersService.autoLogin(u);
			return "redirect:/";
		}
		return "redirect:/error?message=Could not activate with this activation code, please contact support";
	}

	@GetMapping("/user/autologin")
	public String autoLogin(Users users) {
		usersService.autoLogin(users.getUsername());
		return "redirect:/";
	}

	@GetMapping("/user/edit/{id}")
	public String edit(final Model model, @PathVariable("id") Integer id) {
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

		u.setId(u.getId());
		u.setUsername(u.getUsername());
		u.setEmail(u.getEmail());
		u.setFirstName(u.getFirstName());
		u.setLastName(u.getLastName());

		model.addAttribute("user", u);

		return "user/edit";
	}

	@PostMapping("/user/edit")
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