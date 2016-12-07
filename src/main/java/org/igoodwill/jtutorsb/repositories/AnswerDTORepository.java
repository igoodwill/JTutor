package org.igoodwill.jtutorsb.repositories;

import java.util.List;

import org.igoodwill.jtutorsb.model.AnswerDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AnswerDTORepository extends CrudRepository<AnswerDTO, Integer> {

	@Transactional
	public void deleteAllByUserId(Integer userId);

	@Transactional
	public void deleteAllByUserIdAndQuestionId(Integer userId, Integer questionId);

	public List<AnswerDTO> findAllByUserIdAndQuestId(Integer userId, Integer questId);

	public AnswerDTO findByUserIdAndQuestionId(Integer userId, Integer questionId);
}
