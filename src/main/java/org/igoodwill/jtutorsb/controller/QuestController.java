package org.igoodwill.jtutorsb.controller;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
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
public class QuestController {

	@Autowired
	QuestRepository repo;

	@GetMapping("add")
	public String questForm(final Model model) {
		model.addAttribute("quests", repo.findAll());
		model.addAttribute("questForm", new Quest());
		return "quests";
	}

	@PostMapping("add")
	public String questSubmit(
			@Valid @ModelAttribute("questForm") final Quest questForm, 
			final BindingResult result) {
		
		if (result.hasErrors()) {
			return "quests";
		}
		repo.save(questForm);
		return "redirect:/quest/add";
	}

	@GetMapping("{questId}/edit")
	public String editQuest(
			@PathVariable Integer questId, 
			final Model model) {
		
		model.addAttribute("quests", repo.findAll());
		model.addAttribute("questForm", repo.findOne(questId));
		return "quests";
	}

	@PostMapping("{questId}/edit")
	public String updateQuest(
			@PathVariable final Integer questId, 
			@Valid @ModelAttribute("questForm") final Quest questForm, 
			final BindingResult result) {
		
		if (result.hasErrors()) {
			return "quests";
		}
		repo.save(questForm);
		return "redirect:/quest/add";
	}

	@GetMapping("{questId}/delete")
	public String removeQuest(@PathVariable final Integer questId) {
		repo.delete(questId);
		return "redirect:/quest/add";
	}
}