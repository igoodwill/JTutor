package org.igoodwill.jtutorsb.repositories;

import java.util.List;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Integer> {

	public List<Lecture> findByNameIgnoreCaseContaining(String name);
}
