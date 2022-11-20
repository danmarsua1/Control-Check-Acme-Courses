/*
 * LearnerHelpRequestRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.learner.helpRequest;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.FollowUp;
import acme.entities.HelpRequest;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Learner;
import acme.roles.Teacher;

@Repository
public interface LearnerHelpRequestRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select i from Teacher i where i.userAccount.id = :id")
	Teacher findOneTeacherById(int id);

	@Query("select h from HelpRequest h where h.teacher.id = :id")
	Collection<HelpRequest> findManyHelpRequestsByTeacher(int id);
	
	@Query("select h from HelpRequest h where h.learner.id = :id")
	Collection<HelpRequest> findManyHelpRequestsByLearner(int id);

	@Query("select h from HelpRequest h where h.id = :id")
	HelpRequest findOneHelpRequestById(int id);

	@Query("select h.teacher.id from HelpRequest h where h.id = :id")
	Integer findTeacherByHelpRequestId(int id);

	@Query("select h.learner.id from HelpRequest h where h.id = :id")
	Integer findLearnerByHelpRequestId(int id);

	@Query("select l from Learner l where l.userAccount.id = :id")
	Learner findOneLearnerById(int id);

	@Query("select h from HelpRequest h")
	Collection<HelpRequest> findAllUnpublishedHelpRequests();
	
	@Query("select f from FollowUp f where f.helpRequest.id = :id")
	Collection<FollowUp> findManyFollowUpsByHelpRequest(int id);

	@Query("select te from Teacher te")
	Collection<Teacher> findAllTeachers();
	
	@Query("select te from Teacher te where te.userAccount.username =:username")
	Teacher findByTeacherName(String username);

}
