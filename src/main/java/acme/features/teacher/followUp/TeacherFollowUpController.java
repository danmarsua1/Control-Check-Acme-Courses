/*
 * TeacherFollowUpController.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.teacher.followUp;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.FollowUp;
import acme.framework.controllers.AbstractController;
import acme.roles.Teacher;

@Controller
public class TeacherFollowUpController extends AbstractController<Teacher, FollowUp> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherFollowUpListService	listService;

	@Autowired
	protected TeacherFollowUpShowService	showService;
	
	@Autowired
	protected TeacherFollowUpCreateService	createService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("list", this.listService);
		super.addCommand("show", this.showService);
		
		super.addCommand("create", this.createService);
	}

}
