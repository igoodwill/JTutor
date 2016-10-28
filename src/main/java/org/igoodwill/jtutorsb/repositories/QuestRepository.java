package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.Quest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends CrudRepository<Quest, Integer> {

}
