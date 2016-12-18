package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.ForumAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForumAnswerRepository extends CrudRepository<ForumAnswer, Integer> {
}
