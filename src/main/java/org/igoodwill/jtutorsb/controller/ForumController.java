package org.igoodwill.jtutorsb.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.igoodwill.jtutorsb.model.ForumAnswer;
import org.igoodwill.jtutorsb.model.ForumQuestion;
import org.igoodwill.jtutorsb.repositories.ForumAnswerRepository;
import org.igoodwill.jtutorsb.repositories.ForumQuestionRepository;
import org.igoodwill.jtutorsb.repositories.UsersRepository;
import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.repository.query.Param;
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
@RequestMapping("/forum")
public class ForumController {

	@Autowired
	private UsersService usersService;

	@Autowired
	private UsersRepository usersRepo;

	@Autowired
	private ForumQuestionRepository questionRepo;

	@Autowired
	private ForumAnswerRepository answerRepo;

	@GetMapping("")
	public String forumPage(final Model model) {
		List<ForumQuestion> questions = (List<ForumQuestion>) questionRepo.findAll();
		Collections.reverse(questions);

		if (!model.containsAttribute("query")) {
			model.addAttribute("query", "");
			model.addAttribute("questions", questions);
		}

		model.addAttribute("usersRepo", usersRepo);
		model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

		return "forum/index";
	}

	@PostMapping("")
	public String search(@Param(value = "query") final String query, final RedirectAttributes redirectAttributes) {
		List<ForumQuestion> questions = questionRepo.findByNameIgnoreCaseContaining(query);
		Collections.reverse(questions);

		redirectAttributes.addFlashAttribute("query", query);
		redirectAttributes.addFlashAttribute("questions", questions);

		return "redirect:/forum";
	}

	@GetMapping("/question/new")
	public String newQuestion(final Model model) {
		model.addAttribute("question", new ForumQuestion());

		return "forum/question/new";
	}

	@PostMapping("/question/new")
	public String newQuestion(@Valid @ModelAttribute("question") final ForumQuestion question,
			final BindingResult result, final Model model) {

		question.setId(null);
		question.setCreatorId(usersService.getLoggedInUser().getId());
		question.setCreated(new Date());

		if (result.hasErrors()) {
			return "forum/question/new";
		}

		questionRepo.save(question);

		List<ForumQuestion> questions = (List<ForumQuestion>) questionRepo.findAll();

		return "redirect:/forum/question/" + questions.get(questions.size() - 1).getId();
	}

	@GetMapping("/question/{questionId}")
	public String questionPage(@PathVariable final Integer questionId, final Model model) {
		ForumQuestion question = questionRepo.findOne(questionId);

		model.addAttribute("question", question);
		model.addAttribute("usersService", usersService);
		model.addAttribute("usersRepo", usersRepo);
		model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());
		model.addAttribute("answerForm", new ForumAnswer());
		model.addAttribute("edit", false);

		return "forum/question";
	}

	@GetMapping("/question/{questionId}/edit")
	public String editQuestion(@PathVariable Integer questionId, final Model model) {
		ForumQuestion question = questionRepo.findOne(questionId);

		if (!usersService.getLoggedInUser().isAdmin()
				&& !question.getCreatorId().equals(usersService.getLoggedInUser().getId()) || question.isClosed()) {
			return "redirect:/forum/question/" + questionId;
		}

		model.addAttribute("question", question);

		return "forum/question/edit";
	}

	@PostMapping("/question/{questionId}/edit")
	public String updateQuestion(@PathVariable final Integer questionId,
			@Valid @ModelAttribute("question") final ForumQuestion question, final BindingResult result,
			final Model model) {

		ForumQuestion original = questionRepo.findOne(questionId);

		question.setId(questionId);
		question.setName(original.getName());
		question.setClosed(original.isClosed());
		question.setCreated(original.getCreated());
		question.setCreatorId(original.getCreatorId());
		question.setForumAnswers(original.getForumAnswers());

		if (result.hasErrors()) {
			model.addAttribute("question", question);

			return "forum/question/edit";
		}

		if (!usersService.getLoggedInUser().isAdmin()
				&& !question.getCreatorId().equals(usersService.getLoggedInUser().getId()) || question.isClosed()) {
			return "redirect:/forum/question/" + questionId;
		}

		questionRepo.save(question);
		return "redirect:/forum/question/" + questionId;
	}

	@GetMapping("/question/{questionId}/delete")
	public String removeQuestion(@PathVariable final Integer questionId) {
		if (!usersService.getLoggedInUser().isAdmin()) {
			return "redirect:/forum/question/" + questionId;
		}

		questionRepo.delete(questionId);
		return "redirect:/forum";
	}

	@GetMapping("/question/{questionId}/close")
	public String closeQuestion(@PathVariable final Integer questionId) {
		ForumQuestion question = questionRepo.findOne(questionId);
		if (!usersService.getLoggedInUser().isAdmin()
				&& !question.getCreatorId().equals(usersService.getLoggedInUser().getId())) {
			return "redirect:/forum/question/" + questionId;
		}

		question.setClosed(true);

		questionRepo.save(question);
		return "redirect:/forum";
	}

	@PostMapping("/question/{questionId}/answer/new")
	public String newAnswer(@Valid @ModelAttribute("answerForm") final ForumAnswer answerForm,
			final BindingResult result, @PathVariable final Integer questionId, final Model model) {

		ForumQuestion question = questionRepo.findOne(questionId);

		if (question.isClosed()) {
			return "redirect:/forum/question/" + questionId;
		}

		answerForm.setId(null);
		answerForm.setCreatorId(usersService.getLoggedInUser().getId());
		answerForm.setCreated(new Date());

		if (result.hasErrors()) {
			model.addAttribute("edit", false);
			model.addAttribute("answerForm", answerForm);
			model.addAttribute("question", question);
			model.addAttribute("usersService", usersService);
			model.addAttribute("usersRepo", usersRepo);
			model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

			return "forum/question";
		}

		question.getForumAnswers().add(answerForm);

		answerRepo.save(answerForm);

		return "redirect:/forum/question/" + questionId;
	}

	@GetMapping("/question/{questionId}/answer/{answerId}/edit")
	public String editAnswer(@PathVariable Integer questionId, @PathVariable final Integer answerId,
			final Model model) {

		ForumAnswer answerForm = answerRepo.findOne(answerId);
		ForumQuestion question = questionRepo.findOne(questionId);

		if (!usersService.getLoggedInUser().isAdmin()
				&& !answerForm.getCreatorId().equals(usersService.getLoggedInUser().getId()) || question.isClosed()) {
			return "redirect:/forum/question/" + questionId;
		}

		model.addAttribute("edit", true);
		model.addAttribute("answerForm", answerForm);
		model.addAttribute("question", question);
		model.addAttribute("usersService", usersService);
		model.addAttribute("usersRepo", usersRepo);
		model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

		return "forum/question";
	}

	@PostMapping("/question/{questionId}/answer/{answerId}/edit")
	public String updateAnswer(@Valid @ModelAttribute("answerForm") final ForumAnswer answerForm,
			final BindingResult result, @PathVariable final Integer questionId, @PathVariable final Integer answerId,
			final Model model) {

		ForumAnswer original = answerRepo.findOne(answerId);
		ForumQuestion question = questionRepo.findOne(questionId);

		answerForm.setId(answerId);
		answerForm.setCreated(original.getCreated());
		answerForm.setCreatorId(original.getCreatorId());
		answerForm.setLiked(original.getLiked());

		if (result.hasErrors()) {
			model.addAttribute("edit", true);
			model.addAttribute("answerForm", answerForm);
			model.addAttribute("question", question);
			model.addAttribute("usersService", usersService);
			model.addAttribute("usersRepo", usersRepo);
			model.addAttribute("lang", LocaleContextHolder.getLocale().getLanguage());

			return "forum/question";
		}

		if (!usersService.getLoggedInUser().isAdmin()
				&& !answerForm.getCreatorId().equals(usersService.getLoggedInUser().getId()) || question.isClosed()) {
			return "redirect:/forum/question/" + questionId;
		}

		answerRepo.save(answerForm);

		return "redirect:/forum/question/" + questionId;
	}

	@GetMapping("/question/{questionId}/answer/{answerId}/delete")
	public String removeAnswer(@PathVariable final Integer questionId, @PathVariable final Integer answerId) {
		if (!usersService.getLoggedInUser().isAdmin()) {
			return "redirect:/forum/question/" + questionId;
		}

		ForumQuestion question = questionRepo.findOne(questionId);
		question.getForumAnswers().remove(answerRepo.findOne(answerId));

		answerRepo.delete(answerId);
		questionRepo.save(question);

		return "redirect:/forum/question/" + questionId;
	}

	@GetMapping("/question/{questionId}/answer/{answerId}/like")
	public String likeAnswer(@PathVariable final Integer questionId, @PathVariable final Integer answerId) {
		Integer userId = usersService.getLoggedInUser().getId();
		ForumAnswer answer = answerRepo.findOne(answerId);

		if (answer.getCreatorId() == userId || answer.getLiked().contains(userId)) {
			return "redirect:/forum/question/" + questionId;
		}

		answer.getLiked().add(userId);

		answerRepo.save(answer);

		return "redirect:/forum/question/" + questionId;
	}
}
