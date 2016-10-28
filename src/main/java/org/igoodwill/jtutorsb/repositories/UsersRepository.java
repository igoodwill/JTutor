package org.igoodwill.jtutorsb.repositories;

import org.igoodwill.jtutorsb.model.Users;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Integer> {
	Users findOneByUsername(String name);

	Users findOneByEmail(String email);

	Users findOneByUsernameOrEmail(String username, String email);

	Users findOneByToken(String token);

	@Modifying
	@Transactional
	@Query("update Users u set u.email = :email, u.firstName = :firstName, " + "u.lastName = :lastName " + "where u.username = :username")
	int updateUser(@Param("username") String username, @Param("email") String email, @Param("firstName") String firstName, @Param("lastName") String lastName);

	@Modifying
	@Transactional
	@Query("update Users u set u.lastLogin = CURRENT_TIMESTAMP where u.username = ?1")
	int updateLastLogin(String username);
}