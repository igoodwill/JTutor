package org.igoodwill.jtutorsb.controller;

import java.util.ArrayList;
import java.util.List;

import org.igoodwill.jtutorsb.model.AnswerDTO;
import org.igoodwill.jtutorsb.model.QuizResult;
import org.igoodwill.jtutorsb.model.UserAnswer;
import org.igoodwill.jtutorsb.model.admin.Answer;
import org.igoodwill.jtutorsb.model.admin.Quest;
import org.igoodwill.jtutorsb.model.admin.Question;
import org.igoodwill.jtutorsb.repositories.AnswerDTORepository;
import org.igoodwill.jtutorsb.repositories.QuestRepository;
import org.igoodwill.jtutorsb.repositories.QuestionRepository;
import org.igoodwill.jtutorsb.repositories.QuizResultRepository;
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
	QuizResultRepository quizResultRepo;

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

		Integer userId = usersService.getLoggedInUser().getId();
		if (questionNumber == 0) {
			quizResultRepo.deleteByUserIdAndQuestId(userId, questId);
			return "redirect:/quiz/" + questId + "/question/1";
		}

		Quest quest = questRepo.findOne(questId);
		model.addAttribute("quest", quest);
		model.addAttribute("questionsCount", quest.getQuestions().size());

		Question currentQuestion = quest.getQuestions().get(questionNumber - 1);
		model.addAttribute("question", currentQuestion.getValue());

		AnswerDTO answerForm = answerDTORepo.findByUserIdAndQuestionId(userId, currentQuestion.getId());
		if (answerForm == null) {
			List<UserAnswer> userAnswers = new ArrayList<>();
			for (Answer answer : currentQuestion.getAnswers()) {
				userAnswers.add(new UserAnswer(answer));
			}
			answerForm = new AnswerDTO(userAnswers, userId, questId, currentQuestion.getId());
		}

		model.addAttribute("answerForm", answerForm);
		model.addAttribute("answers", answerForm.getAnswers().toArray());

		List<Integer> numbersBefore = new ArrayList<>();
		for (int i = 1; i < questionNumber; i++) {
			numbersBefore.add(i);
		}
		model.addAttribute("numbersBefore", numbersBefore);

		List<Integer> numbersAfter = new ArrayList<>();
		for (int i = questionNumber + 1; i <= quest.getQuestions().size(); i++) {
			if (answerDTORepo.findByUserIdAndQuestionId(userId, quest.getQuestions().get(i - 1).getId()) != null) {
				numbersAfter.add(i);
			}
		}
		model.addAttribute("numbersAfter", numbersAfter);

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

		Quest quest = questRepo.findOne(questId);
		Integer userId = usersService.getLoggedInUser().getId();
		Integer questionId = quest.getQuestions().get(questionNumber - 1).getId();

		answerForm.setUserId(userId);
		answerForm.setQuestId(questId);
		answerForm.setQuestionId(questionId);

		for (UserAnswer userAnswer : answerForm.getAnswers()) {
			userAnswerRepo.save(userAnswer);
		}
		answerDTORepo.save(answerForm);

		List<Question> questions = quest.getQuestions();
		if (questionNumber == questions.size()) {
			List<AnswerDTO> answers = answerDTORepo.findAllByUserIdAndQuestId(userId, questId);
			if (answers.size() != questRepo.findOne(questId).getQuestions().size()) {
				return "quiz/error";
			}
			QuizResult quizResult = new QuizResult(userId, questId, answers);
			quizResultRepo.save(quizResult);
			return "redirect:/quiz/" + questId + "/result";
		}

		return "redirect:/quiz/" + questId + "/question/" + (questionNumber + 1);
	}

	@GetMapping("{questId}/result")
	public String showResult(@PathVariable final Integer questId, final Model model) {
		Integer userId = usersService.getLoggedInUser().getId();

		QuizResult result = quizResultRepo.findByUserIdAndQuestId(userId, questId);
		List<AnswerDTO> userAnswers = result.getAnswers();
		List<Question> questions = new ArrayList<>();
		List<Integer> wrongQuestionIds = new ArrayList<>();
		int count = 0;

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

		return "quiz/result";
	}

	@GetMapping("results")
	public String showResults(final Model model) {
		Integer userId = usersService.getLoggedInUser().getId();
		List<QuizResult> results = quizResultRepo.findAllByUserId(userId);

		model.addAttribute("results", results);
		model.addAttribute("questRepo", questRepo);

		return "quiz/results";
	}
}
