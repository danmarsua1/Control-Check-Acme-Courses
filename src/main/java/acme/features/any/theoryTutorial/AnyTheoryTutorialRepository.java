/*
 * AnyTheoryTutorialRepository.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.any.theoryTutorial;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.TheoryTutorial;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyTheoryTutorialRepository extends AbstractRepository {

	@Query("select t from TheoryTutorial t where t.id = :id")
	TheoryTutorial findOneTheoryTutorialById(int id);
	
	@Query("select t from TheoryTutorial t")
	List<TheoryTutorial> findAllUnpublishedTheoryTutorials();
	
	@Query("select t from TheoryTutorial t where t.publish = true")
	List<TheoryTutorial> findAllTheoryTutorials();

	@Query("select t from Course c, Register r, TheoryTutorial t where c.id = r.course.id and r.theoryTutorial.id = t.id and c.id = :id and t.publish = true")
	Collection<TheoryTutorial> findManyTheoryTutorialsByCourseId(int id);
	
	
}
