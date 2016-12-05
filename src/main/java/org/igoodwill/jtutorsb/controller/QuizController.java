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
    AnswerDTORepository answerDTORepo;

    @GetMapping("quests")
    public String list(Model model) {
	model.addAttribute("quests", questRepo.findAll());
	return "quiz/quests";
    }

    @GetMapping("{questId}/question/{questionNumber}")
    public String questionForm(@PathVariable final Integer questId, @PathVariable final Integer questionNumber,
	    final Model model) {

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
	    @ModelAttribute final AnswerDTO answerForm, final Model model) {

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
	model.addAttribute("results", answerDTORepo.findAllByUserIdAndQuestId(userId, questId));
	clearResultsByUserId(userId);
	return "quiz/results";
    }

    public void clearResultsByUserId(Integer userId) {
	answerDTORepo.deleteAllByUserId(userId);
    }
}
