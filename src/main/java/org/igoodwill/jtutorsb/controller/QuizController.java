package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.Quest;
import org.igoodwill.jtutorsb.repositories.AnswerRepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

	@GetMapping("{questId}/questions")
	public String questionForm(@PathVariable final Integer questId, final Model model) {

		Quest quest = questRepo.findOne(questId);
		model.addAttribute("quest", quest);
		model.addAttribute("questions", quest.getQuestions());
		if (!model.containsAttribute("question")) {
			model.addAttribute("question", quest.getQuestions().get(0).getValue());
			model.addAttribute("questionId", 1);
			model.addAttribute("answers", quest.getQuestions().get(0).getAnswers());
		}
		return "quiz/questions";
	}

	@PostMapping("{questId}/questions")
	public String nextQuestion(@PathVariable final Integer questId,
			@Valid @ModelAttribute("questionId") final Integer questionId, final Model model) {

		Quest quest = questRepo.findOne(questId);
		
		if (questionId == quest.getQuestions().size())
		{
			// TODO
		}
		
		model.addAttribute("quest", quest);
		model.addAttribute("questions", quest.getQuestions());
		model.addAttribute("question", quest.getQuestions().get(questionId).getValue());
		model.addAttribute("questionId", questionId + 1);

		return "quiz/questions";
	}
}
