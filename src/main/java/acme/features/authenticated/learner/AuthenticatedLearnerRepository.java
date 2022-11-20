/*
 * AuthenticatedLearnerRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.learner;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Learner;
import acme.roles.Teacher;

@Repository
public interface AuthenticatedLearnerRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select t from Teacher t where t.userAccount.id = :id")
	Teacher findOneTeacherByUserAccountId(int id);
	
	@Query("select l from Learner l where l.userAccount.id = :id")
	Learner findOneLearnerByUserAccountId(int id);

}
