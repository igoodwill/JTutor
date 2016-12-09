package org.igoodwill.jtutorsb.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/lecture/")
public class LectureController {

	@Autowired
	LectureRepository lectureRepo;

	// CLIENT

	@GetMapping("lectures")
	public String list(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		return "lecture/lectures";
	}

	@GetMapping("{lectureId}")
	public String showLecture(final Model model, @PathVariable final Integer lectureId) {
		Lecture lecture = lectureRepo.findOne(lectureId);
		model.addAttribute("lecture", lecture);

		File file = new File(getClass().getClassLoader().getResource("templates/fragments").getFile() + "/lecture"
				+ lecture.getId() + ".html");
		try {
			String content = lecture.getValue();

			Files.write(file.toPath(), (content.getBytes()));
		} catch (IOException e) {
		}

		return "lecture/show";
	}

	// ADMIN

	@GetMapping("add")
	public String lectureForm(final Model model) {
		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", new Lecture());
		return "admin/lectures";
	}

	@PostMapping("add")
	public String lectureSubmit(@Valid @ModelAttribute("lectureForm") final Lecture lectureForm,
			final BindingResult result) {

		if (result.hasErrors()) {
			return "admin/lectures";
		}
		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("{lectureId}/edit")
	public String editLecture(@PathVariable Integer lectureId, final Model model) {

		model.addAttribute("lectures", lectureRepo.findAll());
		model.addAttribute("lectureForm", lectureRepo.findOne(lectureId));
		return "admin/lectures";
	}

	@PostMapping("{lectureId}/edit")
	public String updateLecture(@PathVariable final Integer lectureId,
			@Valid @ModelAttribute("lectureForm") final Lecture lectureForm, final BindingResult result) {

		if (result.hasErrors()) {
			return "admin/lectures";
		}
		lectureRepo.save(lectureForm);
		return "redirect:/lecture/add";
	}

	@GetMapping("{lectureId}/delete")
	public String removeLecture(@PathVariable final Integer lectureId) {
		lectureRepo.delete(lectureId);
		return "redirect:/lecture/add";
	}
}
