package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.Answer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends CrudRepository<Answer, Integer> {

}
