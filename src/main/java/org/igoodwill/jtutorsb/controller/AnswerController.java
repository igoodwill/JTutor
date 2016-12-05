package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Answer;
import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.model.admin.Question;
import org.igoodwill.jtutorsb.repositories.AnswerRepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.QuestionRepository;
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
@RequestMapping("/quest/")
public class AnswerController {

	@Autowired
	QuestRepository questRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	AnswerRepository answerRepo;

	@GetMapping("{questId}/question/{questionId}/answers")
	public String answerForm(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId, 
			final Model model) {
		
		Quest quest = questRepo.findOne(questId);
		Question question = questionRepo.findOne(questionId);
		model.addAttribute("question", question);
		model.addAttribute("answers", question.getAnswers());
		model.addAttribute("quest", quest);
		model.addAttribute("answerForm", new Answer());
		return "answers";
	}

	@PostMapping("{questId}/question/{questionId}/answers")
	public String addAnswer(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId,
			@Valid @ModelAttribute("answerForm") final Answer answerForm, 
			final BindingResult result) {
		
		Question question = questionRepo.findOne(questionId);
		if (result.hasErrors()) {
			return "redirect:/quest/" + questId + "/question/" + questionId + "/answers";
		}
		question.getAnswers().add(answerForm);
		answerRepo.save(answerForm);
		questionRepo.save(question);
		return "redirect:/quest/" + questId + "/question/" + questionId + "/answers";
	}

	@GetMapping("{questId}/question/{questionId}/answer/{answerId}/edit")
	public String editAnswer(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId, 
			@PathVariable final Integer answerId,
			final Model model) {
		
		Question question = questionRepo.findOne(questionId);
		model.addAttribute("question", question);
		model.addAttribute("answers", question.getAnswers());
		model.addAttribute("answerForm", answerRepo.findOne(answerId));
		model.addAttribute("quest", questRepo.findOne(questId));
		return "answers";
	}

	@PostMapping("{questId}/question/{questionId}/answer/{answerId}/edit")
	public String updateAnswer(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId,
			@Valid @ModelAttribute("answerForm") final Answer answerForm, 
			final BindingResult result) {
		
		if (result.hasErrors()) {
			return "redirect:/quest/" + questId + "/question/" + questionId + "/answers";
		}
		answerRepo.save(answerForm);
		return "redirect:/quest/" + questId + "/question/" + questionId + "/answers";
	}

	@GetMapping("{questId}/question/{questionId}/answer/{answerId}/delete")
	public String removeAnswer(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId, 
			@PathVariable final Integer answerId) {
		
		Question question = questionRepo.findOne(questionId);
		question.getAnswers().remove(answerRepo.findOne(answerId));
		questionRepo.save(question);
		answerRepo.delete(answerId);
		return "redirect:/quest/" + questId + "/question/" + questionId + "/answers";
	}
}