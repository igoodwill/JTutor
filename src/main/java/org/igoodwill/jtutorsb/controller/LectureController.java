package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.igoodwill.jtutorsb.repositories.LectureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LectureController {

	@Autowired
	LectureRepository lectureRepo;

	// CLIENT

	@GetMapping("/tutorial/lectures")
	public String list(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		return "lecture/lectures";
	}

	@GetMapping("/tutorial")
	public String showLecture(final Model model, @RequestBody final Integer id) {
		Lecture lecture = lectureRepo.findOne(id);

		if (lecture == null) {
			return "redirect:/home";
		}

		model.addAttribute("lecture", lecture);

		return "lecture/show";
	}

	// ADMIN

	@GetMapping("/lecture/add")
	public String lectureForm(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", new Lecture());
		return "admin/lectures";
	}

	@PostMapping("/lecture/add")
	public String lectureSubmit(@Valid @ModelAttribute("lectureForm") final Lecture lectureForm,
			final BindingResult result) {

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
