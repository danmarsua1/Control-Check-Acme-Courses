package acme.features.any.course;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Course;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Any;
import acme.framework.services.AbstractListService;

@Service
public class AnyCourseListService implements AbstractListService<Any, Course>{

	
	@Autowired
	protected AnyCourseRepository repository;
	
	@Override
	public boolean authorise(final Request<Course> request) {
		assert request != null;
		
		return true;
	}

	@Override
	public Collection<Course> findMany(final Request<Course> request) {
		assert request != null;
		
		Collection<Course> result;
		result = this.repository.findCourses();
		
		return result;
	}

	@Override
	public void unbind(final Request<Course> request, final Course entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;
		
		request.unbind(entity, model, "ticker", "caption", "abstractText");
		
	}

}
