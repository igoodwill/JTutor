package org.igoodwill.jtutorsb.repositories;

import java.util.List;

import org.igoodwill.jtutorsb.model.ForumQuestion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumQuestionRepository extends CrudRepository<ForumQuestion, Integer> {

	public List<ForumQuestion> findByNameIgnoreCaseContaining(String name);
}
