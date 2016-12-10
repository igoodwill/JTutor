package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.igoodwill.jtutorsb.repositories.LectureRepository;
import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LectureController {

	@Autowired
	private UsersService usersService;

	@Autowired
	LectureRepository lectureRepo;

	// CLIENT

	@GetMapping("/tutorial/lectures")
	public String list(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		return "lecture/lectures";
	}

	@GetMapping("/tutorial/{lectureId}")
	public String showLecture(final Model model, @PathVariable final Integer lectureId) {
		Lecture lecture = lectureRepo.findOne(lectureId);
		model.addAttribute("lecture", lecture);

		return "lecture/show";
	}

	// ADMIN

	@GetMapping("/lecture/add")
	public String lectureForm(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", new Lecture());
		model.addAttribute("usersService", usersService);
		return "admin/lectures";
	}

	@PostMapping("/lecture/add")
	public String lectureSubmit(@Valid @ModelAttribute("lectureForm") final Lecture lectureForm,
			final BindingResult result) {

		lectureForm.setCreatorId(usersService.getLoggedInUser().getId());

		if (result.hasErrors()) {
			return "admin/lectures";
		}
		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("/lecture/{lectureId}/edit")
	public String editLecture(@PathVariable Integer lectureId, final Model model) {

		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", lectureRepo.findOne(lectureId));
		return "admin/lectures";
	}

	@PostMapping("/lecture/{lectureId}/edit")
	public String updateLecture(@PathVariable final Integer lectureId,
			@Valid @ModelAttribute("lectureForm") final Lecture lectureForm, final BindingResult result) {

		if (result.hasErrors()) {
			return "admin/lectures";
		}
		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("/lecture/{lectureId}/delete")
	public String removeLecture(@PathVariable final Integer lectureId) {
		lectureRepo.delete(lectureId);
		return "redirect:/lecture/add";
	}
}
