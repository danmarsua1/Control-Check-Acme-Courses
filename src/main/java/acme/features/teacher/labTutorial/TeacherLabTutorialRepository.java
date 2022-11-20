/*
 * TeacherLabTutorialRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.labTutorial;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.entities.LabTutorial;
import acme.entities.TheoryTutorial;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Teacher;

@Repository
public interface TeacherLabTutorialRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("select t from Teacher t where t.userAccount.id = :id")
	Teacher findOneTeacherById(int id);

	@Query("select c from Course c where c.teacher.id = :id")
	Collection<Course> findManyCoursesByTeacher(int id);
	
	@Query("select c from Course c")
	Collection<Course> findAllCourses();
	
	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);
	
	@Query("select t.cost.amount, t.cost.currency from Course c, Register r, TheoryTutorial t where c.id = r.course.id and r.theoryTutorial.id = t.id and c.id = :id and t.publish = true")
	List<Object[]> getCourseTheoryTutorialsPrice(int id);

	@Query("select l.cost.amount, l.cost.currency from Course c, Register r, LabTutorial l where c.id = r.course.id and r.labTutorial.id = l.id and c.id = :id and l.publish = true")
	List<Object[]> getCourseLabTutorialsPrice(int id);
	
	@Query("select t from Teacher te, Course c, Register r, TheoryTutorial t where te.id = c.teacher.id and c.id = r.course.id and r.theoryTutorial.id = t.id and te.id = :id")
	Collection<TheoryTutorial> findManyTheoryTutorialsByTeacherId(int id);
	
	@Query("select l from Teacher te, Course c, Register r, LabTutorial l where te.id = c.teacher.id and c.id = r.course.id and r.labTutorial.id = l.id and te.id = :id")
	Collection<LabTutorial> findManyLabTutorialsByTeacherId(int id);

	@Query("select distinct te.id from Teacher te, Course c, Register r, TheoryTutorial t where te.id = c.teacher.id and c.id = r.course.id and r.theoryTutorial.id = :id")
	Integer findTeacherByTheoryTutorialId(int id);
	
	@Query("select distinct te.id from Teacher te, Course c, Register r, LabTutorial l where te.id = c.teacher.id and c.id = r.course.id and r.labTutorial.id = :id")
	Integer findTeacherByLabTutorialId(int id);
	
	@Query("select t from TheoryTutorial t where t.id = :id")
	TheoryTutorial findOneTheoryTutorialById(int id);
	
	@Query("select l from LabTutorial l where l.id = :id")
	LabTutorial findOneLabTutorialById(int id);
	
	@Query("select l from Teacher te, Course c, Register r, LabTutorial l where te.id = c.teacher.id and c.id = r.course.id and r.labTutorial.id = l.id and te.id = :id and c.id = :masterId")
	Collection<LabTutorial> findManyLabTutorialsByTeacherAndCourseId(int id, int masterId);
	
}
