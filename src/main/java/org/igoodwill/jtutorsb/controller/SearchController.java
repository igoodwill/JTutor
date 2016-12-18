package org.igoodwill.jtutorsb.controller;

import java.util.ArrayList;
import java.util.List;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.repositories.LectureRepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private QuestRepository questRepo;

	@Autowired
	private LectureRepository lectureRepo;

	@GetMapping("")
	public String searchPage(final Model model) {
		model.addAttribute("query", "");
		model.addAttribute("quests", new ArrayList<Quest>());
		model.addAttribute("lectures", new ArrayList<Lecture>());

		return "search";
	}

	@PostMapping("")
	public String search(final Model model, @Param(value = "query") final String query) {
		List<Quest> quests = questRepo.findByNameIgnoreCaseContaining(query);
		List<Lecture> lectures = lectureRepo.findByNameIgnoreCaseContaining(query);

		model.addAttribute("query", query);
		model.addAttribute("quests", quests);
		model.addAttribute("lectures", lectures);

		return "search";
	}
}
