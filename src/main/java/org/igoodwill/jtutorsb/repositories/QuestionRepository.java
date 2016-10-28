package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Integer> {

}
