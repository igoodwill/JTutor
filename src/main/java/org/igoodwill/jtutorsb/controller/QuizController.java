package org.igoodwill.jtutorsb.controller;

import org.igoodwill.jtutorsb.repositories.AnswerRepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz/")
public class QuizController {

	@Autowired
	QuestRepository questRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	AnswerRepository answerRepo;

	@GetMapping("quests")
	public String list(Model model) {
		model.addAttribute("quests", questRepo.findAll());
		return "quiz/quests";
	}
}
