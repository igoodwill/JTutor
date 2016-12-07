package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.admin.Lecture;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LectureRepository extends CrudRepository<Lecture, Integer> {

}
