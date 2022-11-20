/*
 * LearnerFollowUpRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.learner.followUp;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.FollowUp;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Learner;

@Repository
public interface LearnerFollowUpRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select l from Learner l where l.userAccount.id = :id")
	Learner findOneLearnerById(int id);
	
	@Query("select f from FollowUp f, HelpRequest h where f.helpRequest.id = h.id and h.learner.id = :id")
	Collection<FollowUp> findManyFollowUpsByLearner(int id);

	@Query("select f from FollowUp f where f.id = :id")
	FollowUp findOneFollowUpById(int id);

	@Query("select l.id from FollowUp f, HelpRequest h, Learner l where f.helpRequest.id = h.id and h.learner.id = l.id and f.id = :id")
	Integer findLearnerByFollowUpId(int id);

	@Query("select f from FollowUp f where f.helpRequest.id = :id")
	Collection<FollowUp> findFollowUpsByHelpRequest(int id);

}
