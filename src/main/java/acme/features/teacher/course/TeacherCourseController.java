/*
 * TeacherCourseController.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.course;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.Course;
import acme.framework.controllers.AbstractController;
import acme.roles.Teacher;

@Controller
public class TeacherCourseController extends AbstractController<Teacher, Course> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherCourseListService	listService;

	@Autowired
	protected TeacherCourseShowService	showService;
	
	@Autowired
	protected TeacherCourseCreateService createService;
	
	@Autowired
	protected TeacherCourseUpdateService updateService;
	
	@Autowired
	protected TeacherCoursePublishService publishService;
	
	@Autowired
	protected TeacherCourseDeleteService deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
		super.addCommand("publish","update", this.publishService);
		super.addCommand("delete", this.deleteService);
	}

}
