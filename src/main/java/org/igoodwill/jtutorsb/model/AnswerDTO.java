package org.igoodwill.jtutorsb.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "answer_dto")
public class AnswerDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToMany(cascade = { CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH,
			CascadeType.DETACH }, orphanRemoval = true)
	private List<UserAnswer> answers;

	private Integer userId;

	private Integer questId;

	private Integer questionId;

	public AnswerDTO() {
	}

	public AnswerDTO(List<UserAnswer> answers, Integer userId, Integer questId, Integer questionId) {
		this.answers = answers;
		this.userId = userId;
		this.questId = questId;
		this.questionId = questionId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public List<UserAnswer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<UserAnswer> answers) {
		this.answers = answers;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getQuestId() {
		return questId;
	}

	public void setQuestId(Integer questId) {
		this.questId = questId;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}
}
