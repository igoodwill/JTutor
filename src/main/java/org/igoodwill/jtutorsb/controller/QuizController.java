package org.igoodwill.jtutorsb.controller;

import java.util.ArrayList;
import java.util.List;

import org.igoodwill.jtutorsb.model.AnswerDTO;
import org.igoodwill.jtutorsb.model.UserAnswer;
import org.igoodwill.jtutorsb.model.admin.Answer;
import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.model.admin.Question;
import org.igoodwill.jtutorsb.repositories.AnswerDTORepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.QuestionRepository;
import org.igoodwill.jtutorsb.repositories.UserAnswerRepository;
import org.igoodwill.jtutorsb.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/quiz/")
public class QuizController {

	@Autowired
	private UsersService usersService;

	@Autowired
	QuestRepository questRepo;

	@Autowired
	UserAnswerRepository userAnswerRepo;

	@Autowired
	QuestionRepository questionRepo;

	@Autowired
	AnswerDTORepository answerDTORepo;

	@GetMapping("quests")
	public String list(Model model) {
		model.addAttribute("quests", questRepo.findAll());
		return "quiz/quests";
	}

	@GetMapping("{questId}/question/{questionNumber}")
	public String questionForm(@PathVariable final Integer questId, @PathVariable final Integer questionNumber,
			final Model model) {

		if (questionNumber == 1)
			clearResultsByUserId(usersService.getLoggedInUser().getId());

		Quest quest = questRepo.findOne(questId);
		model.addAttribute("quest", quest);
		model.addAttribute("questionsCount", quest.getQuestions().size());

		Question currentQuestion = quest.getQuestions().get(questionNumber - 1);
		model.addAttribute("question", currentQuestion.getValue());
		List<UserAnswer> userAnswers = new ArrayList<>();
		for (Answer answer : currentQuestion.getAnswers()) {
			userAnswers.add(new UserAnswer(answer));
		}
		Integer userId = usersService.getLoggedInUser().getId();
		AnswerDTO answerForm = new AnswerDTO(userAnswers, userId, questId, currentQuestion.getId());
		model.addAttribute("answerForm", answerForm);
		model.addAttribute("answers", answerForm.getAnswers().toArray());
		return "quiz/question";
	}

	@PostMapping("{questId}/question/{questionNumber}")
	public String submitAnswer(@PathVariable final Integer questId, @PathVariable final Integer questionNumber,
			@ModelAttribute final AnswerDTO answerForm, final Model model, final RedirectAttributes redirectAttrs) {

		boolean flag = true;
		for (UserAnswer userAnswer : answerForm.getAnswers()) {
			if (userAnswer.isState()) {
				flag = false;
				break;
			}
		}

		if (flag) {
			redirectAttrs.addFlashAttribute("isSomeAnswerChoosed", true);
			return "redirect:/quiz/" + questId + "/question/" + questionNumber;
		}
		redirectAttrs.addFlashAttribute("isSomeAnswerChoosed", false);

		Integer userId = answerForm.getUserId();
		Integer questionId = answerForm.getQuestionId();

		if (answerDTORepo.findByUserIdAndQuestionId(userId, questionId) != null) {
			answerDTORepo.deleteAllByUserIdAndQuestionId(userId, questionId);
		}

		Quest quest = questRepo.findOne(questId);

		for (UserAnswer userAnswer : answerForm.getAnswers()) {
			userAnswerRepo.save(userAnswer);
		}
		answerDTORepo.save(answerForm);

		if (questionNumber == quest.getQuestions().size()) {
			return "redirect:/quiz/" + questId + "/result";
		}

		return "redirect:/quiz/" + questId + "/question/" + (questionNumber + 1);
	}

	@GetMapping("{questId}/result")
	public String showResult(@PathVariable final Integer questId, final Model model) {
		Integer userId = usersService.getLoggedInUser().getId();

		List<AnswerDTO> userAnswers = answerDTORepo.findAllByUserIdAndQuestId(userId, questId);
		List<Question> questions = new ArrayList<>();
		List<Integer> wrongQuestionIds = new ArrayList<>();
		int count = 0;

		if (userAnswers.size() != questRepo.findOne(questId).getQuestions().size()) {
			clearResultsByUserId(userId);
			return "quiz/error";
		}

		for (AnswerDTO answerDTO : userAnswers) {
			boolean flag = true;
			int size = answerDTO.getAnswers().size();
			Integer questionId = answerDTO.getQuestionId();
			Question question = questionRepo.findOne(questionId);
			questions.add(question);

			for (int i = 0; i < size; i++) {
				List<Answer> answers = question.getAnswers();

				if (answerDTO.getAnswers().get(i).isState() != answers.get(i).isValid()) {
					flag = false;
					wrongQuestionIds.add(questionId);
					break;
				}
			}

			if (flag) {
				count++;
			}
		}

		String message;
		int totalCount = userAnswers.size();
		if ((double) count / totalCount >= 0.75) {
			message = "You've passed! Your result is: " + count + " of " + totalCount;
		} else {
			message = "You've not passed! Your result is: " + count + " of " + totalCount;
		}

		model.addAttribute("message", message);
		model.addAttribute("questions", questions);
		model.addAttribute("userAnswers", userAnswers);
		model.addAttribute("wrongQuestionIds", wrongQuestionIds);

		clearResultsByUserId(userId);
		return "quiz/results";
	}

	public void clearResultsByUserId(Integer userId) {
		answerDTORepo.deleteAllByUserId(userId);
	}
}
