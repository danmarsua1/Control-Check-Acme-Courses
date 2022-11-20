package acme.features.any.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.Course;
import acme.framework.controllers.AbstractController;
import acme.framework.roles.Any;

@Controller
@RequestMapping("/any/course/")
public class AnyCourseController extends AbstractController<Any, Course> {
	
	@Autowired
	protected AnyCourseListService listService;
	
	@Autowired
	protected AnyCourseShowService showService;

	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
	}
}
