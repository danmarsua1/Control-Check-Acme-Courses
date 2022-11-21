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

package acme.features.teacher.blahblah;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Blahblah;
import acme.entities.HelpRequest;
import acme.entities.TheoryTutorial;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Teacher;

@Repository
public interface TeacherBlahblahRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select i from Teacher i where i.userAccount.id = :id")
	Teacher findOneTeacherById(int id);

	@Query("select h from HelpRequest h where h.teacher.id = :id and h.publish = true")
	Collection<HelpRequest> findManyHelpRequestsByTeacher(int id);
	
	@Query("select h.teacher.id from HelpRequest h where h.id = :id")
	Integer findTeacherByHelpRequestId(int id);
	
	// Control-Check
	
	@Query("select b from Blahblah b where b.id = :id")
	Blahblah findOneBlahblahById(int id);
	
	@Query("select b from Blahblah b where b.theoryTutorial.id = :id")
	Blahblah findOneBlahblahByTheoryTutorialId(int id);

	@Query("select b from Teacher te, Course c, Register r, TheoryTutorial t, Blahblah b where te.id = c.teacher.id and c.id = r.course.id and r.theoryTutorial.id = t.id and t.id = b.theoryTutorial.id and te.id = :id")
	Collection<Blahblah> findManyBlahblahsByTeacher(int id);

	@Query("select t from TheoryTutorial t where t.id = :id")
	TheoryTutorial findByTheoryTutorialId(int id);

	@Query("select b from Blahblah b")
	Collection<Blahblah> findAllBlahblahs();

}
