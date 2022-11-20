package acme.features.any.course;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Course;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnyCourseRepository extends AbstractRepository{
	
	@Query("select c from Course c where c.publish = true")
	Collection<Course> findCourses();

	@Query("select c from Course c where c.id = :id")
	Course findOneCourseById(int id);
	
	@Query("select t.cost.amount, t.cost.currency from Course c, Register r, TheoryTutorial t where c.id = r.course.id and r.theoryTutorial.id = t.id and c.id = :id")
	List<Object[]> getCourseTheoryTutorialsPrice(int id);

	@Query("select l.cost.amount, l.cost.currency from Course c, Register r, LabTutorial l where c.id = r.course.id and r.labTutorial.id = l.id and c.id = :id")
	List<Object[]> getCourseLabTutorialsPrice(int id);

	@Query("select c from Course c where c.ticker = :ticker")
	Course findByTicker(String ticker);

	@Query("select c from Course c")
	List<Course> findAllUnpublishedCourses();

}
