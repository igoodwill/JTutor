package org.igoodwill.jtutorsb.controller;

import java.util.List;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.igoodwill.jtutorsb.repositories.LectureRepository;
import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LectureController {

	@Autowired
	private UsersService usersService;

	@Autowired
	LectureRepository lectureRepo;

	// CLIENT

	@GetMapping("/tutorial/lectures")
	public String list(final Model model) {
		if (!model.containsAttribute("query")) {
			model.addAttribute("query", "");
			model.addAttribute("lectures", lectureRepo.findAll());
		}

		return "lecture/lectures";
	}

	@PostMapping("/tutorial/lectures")
	public String search(@Param(value = "query") final String query, final RedirectAttributes redirectAttributes) {
		List<Lecture> lectures = lectureRepo.findByNameIgnoreCaseContaining(query);

		redirectAttributes.addFlashAttribute("query", query);
		redirectAttributes.addFlashAttribute("lectures", lectures);

		return "redirect:/tutorial/lectures";
	}

	@GetMapping("/tutorial/{lectureId}")
	public String showLecture(final Model model, @PathVariable final Integer lectureId) {
		Lecture lecture = lectureRepo.findOne(lectureId);

		if (lecture == null) {
			return "redirect:/search/";
		}

		model.addAttribute("lecture", lecture);

		return "lecture/index";
	}

	// ADMIN

	@GetMapping("/lecture/add")
	public String lectureForm(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", new Lecture());
		model.addAttribute("usersService", usersService);
		model.addAttribute("isUserHasAccess", true);

		return "admin/lectures";
	}

	@PostMapping("/lecture/add")
	public String lectureSubmit(@Valid @ModelAttribute("lectureForm") final Lecture lectureForm,
			final BindingResult result, final Model model) {

		lectureForm.setCreatorId(usersService.getLoggedInUser().getId());

		if (result.hasErrors()) {
			model.addAttribute("lectures", lectureRepo.findAll());
			model.addAttribute("usersService", usersService);
			model.addAttribute("isUserHasAccess", true);

			return "admin/lectures";
		}

		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("/lecture/{lectureId}/edit")
	public String editLecture(@PathVariable Integer lectureId, final Model model) {

		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", lectureRepo.findOne(lectureId));
		model.addAttribute("usersService", usersService);
		model.addAttribute("isUserHasAccess", usersService.getLoggedInUser().isAdmin()
				|| lectureRepo.findOne(lectureId).getCreatorId().equals(usersService.getLoggedInUser().getId()));

		return "admin/lectures";
	}

	@PostMapping("/lecture/{lectureId}/edit")
	public String updateLecture(@PathVariable final Integer lectureId,
			@Valid @ModelAttribute("lectureForm") final Lecture lectureForm, final BindingResult result,
			final Model model) {

		if (result.hasErrors()) {
			model.addAttribute("lectures", lectureRepo.findAll());
			model.addAttribute("usersService", usersService);
			model.addAttribute("isUserHasAccess", usersService.getLoggedInUser().isAdmin()
					|| lectureRepo.findOne(lectureId).getCreatorId().equals(usersService.getLoggedInUser().getId()));

			return "admin/lectures";
		}

		if (!usersService.getLoggedInUser().isAdmin()
				&& !lectureForm.getCreatorId().equals(usersService.getLoggedInUser().getId())) {
			return "redirect:/lecture/add";
		}

		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("/lecture/{lectureId}/delete")
	public String removeLecture(@PathVariable final Integer lectureId) {
		if (!usersService.getLoggedInUser().isAdmin()
				&& !lectureRepo.findOne(lectureId).getCreatorId().equals(usersService.getLoggedInUser().getId())) {
			return "redirect:/lecture/add";
		}

		lectureRepo.delete(lectureId);
		return "redirect:/lecture/add";
	}
}
