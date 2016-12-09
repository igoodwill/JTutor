package org.igoodwill.jtutorsb.repositories;

import java.util.List;

import org.igoodwill.jtutorsb.model.QuizResult;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface QuizResultRepository extends CrudRepository<QuizResult, Integer> {

	@Transactional
	public void deleteAllByUserIdAndQuestId(Integer userId, Integer questId);

	public QuizResult findByUserIdAndQuestId(Integer userId, Integer questId);

	public List<QuizResult> findAllByUserId(Integer userId);
}
