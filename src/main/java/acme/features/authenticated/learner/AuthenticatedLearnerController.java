/*
 * AuthenticatedLearnerController.java
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

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.framework.controllers.AbstractController;
import acme.framework.roles.Authenticated;
import acme.roles.Learner;

@Controller
public class AuthenticatedLearnerController extends AbstractController<Authenticated, Learner> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedLearnerCreateService	createService;

	@Autowired
	protected AuthenticatedLearnerUpdateService	updateService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCommand("create", this.createService);
		super.addCommand("update", this.updateService);
	}

}
