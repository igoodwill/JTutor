package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.Quest;
import org.igoodwill.jtutorsb.model.Question;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quest/")
public class QuestionController {

	@Autowired
	QuestRepository questRepo;

	@Autowired
	QuestionRepository questionRepo;

	@GetMapping("{questId}/questions")
	public String questionForm(
			@PathVariable final Integer questId, 
			final Model model) {

		Quest quest = questRepo.findOne(questId);
		model.addAttribute("quest", quest);
		model.addAttribute("questions", quest.getQuestions());
		if (!model.containsAttribute("questionForm")) {
			model.addAttribute("questionForm", new Question());
		}
		return "questions";
	}

	@PostMapping("{questId}/questions")
	public String addQuestion(
			@PathVariable final Integer questId, 
			@Valid @ModelAttribute("questionForm") final Question questionForm, 
			final BindingResult result, 
			final RedirectAttributes redirectAttributes) {
		
		Quest quest = questRepo.findOne(questId);
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.questionForm", result);
	        redirectAttributes.addFlashAttribute("questionForm", questionForm);
			return "redirect:/quest/" + questId + "/questions";
		}
		quest.getQuestions().add(questionForm);
		questionRepo.save(questionForm);
		questRepo.save(quest);
		return "redirect:/quest/" + questId + "/questions";
	}

	@GetMapping("{questId}/question/{questionId}/edit")
	public String editQuestion(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId, 
			final Model model) {
			
		Quest quest = questRepo.findOne(questId);
		model.addAttribute("questions", quest.getQuestions());
		model.addAttribute("quest", quest);
		if (!model.containsAttribute("questionForm")) {
			model.addAttribute("questionForm", questionRepo.findOne(questionId));
		}
		return "questions";
	}

	@PostMapping("{questId}/question/{questionId}/edit")
	public String updateQuestion(
			@PathVariable final Integer questId, 
			@Valid @ModelAttribute("questionForm") final Question questionForm,
			final BindingResult result,
			final RedirectAttributes redirectAttributes) {
		
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.questionForm", result);
	        redirectAttributes.addFlashAttribute("questionForm", questionForm);
			return "redirect:/quest/" + questId + "/questions";
		}
		questionRepo.save(questionForm);
		return "redirect:/quest/" + questId + "/questions";
	}

	@GetMapping("{questId}/question/{questionId}/delete")
	public String removeQuestion(
			@PathVariable final Integer questId, 
			@PathVariable final Integer questionId) {
		
		Quest quest = questRepo.findOne(questId);
		quest.getQuestions().remove(questionRepo.findOne(questionId));
		questRepo.save(quest);
		questionRepo.delete(questionId);
		return "redirect:/quest/" + questId + "/questions";
	}
}