/*
 * TeacherHelpRequestRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.helpRequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.HelpRequest;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Teacher;

@Repository
public interface TeacherHelpRequestRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select i from Teacher i where i.userAccount.id = :id")
	Teacher findOneTeacherById(int id);

	@Query("select h from HelpRequest h where h.teacher.id = :id and h.publish = true")
	Collection<HelpRequest> findManyHelpRequestsByTeacher(int id);

	@Query("select h from HelpRequest h where h.id = :id")
	HelpRequest findOneHelpRequestById(int id);

	@Query("select h.teacher.id from HelpRequest h where h.id = :id")
	Integer findTeacherByHelpRequestId(int id);

	
}
