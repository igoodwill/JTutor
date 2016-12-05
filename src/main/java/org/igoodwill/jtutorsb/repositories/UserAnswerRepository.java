package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.UserAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAnswerRepository extends CrudRepository<UserAnswer, Integer> {

}
