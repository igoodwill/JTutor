package org.igoodwill.jtutorsb.controller;

import java.util.Collections;
import java.util.List;

import org.igoodwill.jtutorsb.model.ForumQuestion;
import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.repositories.ForumQuestionRepository;
import org.igoodwill.jtutorsb.repositories.LectureRepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/search")
public class SearchController {

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private QuestRepository questRepo;

	@Autowired
	private LectureRepository lectureRepo;

	@Autowired
	private ForumQuestionRepository forumQuestionRepo;

	@GetMapping("")
	public String searchPage(final Model model) {
		if (!model.containsAttribute("query")) {
			List<ForumQuestion> forumQuestions = (List<ForumQuestion>) forumQuestionRepo.findAll();
			Collections.reverse(forumQuestions);

			model.addAttribute("query", "");
			model.addAttribute("quests", questRepo.findAll());
			model.addAttribute("lectures", lectureRepo.findAll());
			model.addAttribute("forumQuestions", forumQuestions);
		}

		model.addAttribute("usersRepo", usersRepo);
		model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

		return "search";
	}

	@PostMapping("")
	public String search(@Param(value = "query") final String query, final RedirectAttributes redirectAttributes) {
		List<Quest> quests = questRepo.findByNameIgnoreCaseContaining(query);
		List<Lecture> lectures = lectureRepo.findByNameIgnoreCaseContaining(query);
		List<ForumQuestion> forumQuestions = forumQuestionRepo.findByNameIgnoreCaseContaining(query);
		Collections.reverse(forumQuestions);

		redirectAttributes.addFlashAttribute("query", query);
		redirectAttributes.addFlashAttribute("quests", quests);
		redirectAttributes.addFlashAttribute("lectures", lectures);
		redirectAttributes.addFlashAttribute("forumQuestions", forumQuestions);

		return "redirect:/search";
	}
}
