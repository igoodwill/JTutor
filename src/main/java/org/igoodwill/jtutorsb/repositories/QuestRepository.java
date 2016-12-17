package org.igoodwill.jtutorsb.repositories;

import java.util.List;

import org.igoodwill.jtutorsb.model.admin.Quest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestRepository extends CrudRepository<Quest, Integer> {

	public List<Quest> findByNameIgnoreCaseContaining(String name);
}
