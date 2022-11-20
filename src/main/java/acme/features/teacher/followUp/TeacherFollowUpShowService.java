/*
 * TeacherFollowUpShowService.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.FollowUp;
import acme.features.administrator.configuration.AdministratorConfigurationRepository;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.services.AbstractShowService;
import acme.roles.Teacher;

@Service
public class TeacherFollowUpShowService implements AbstractShowService<Teacher, FollowUp> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected TeacherFollowUpRepository repository;
	
	@Autowired
	protected AdministratorConfigurationRepository configurationRepository;

	@Override
	public boolean authorise(final Request<FollowUp> request) {
		assert request != null;

		boolean result;
		int followUpId;
		FollowUp followUp;
		boolean isOwner;

		followUpId = request.getModel().getInteger("id");
		followUp = this.repository.findOneFollowUpById(followUpId);
		Integer teacher = this.repository.findTeacherByFollowUpId(followUpId);
		isOwner = teacher == request.getPrincipal().getActiveRoleId();
		result = followUp != null && isOwner;

		return result;
	}

	@Override
	public FollowUp findOne(final Request<FollowUp> request) {
		assert request != null;

		int followUpId;
		FollowUp result;

		followUpId = request.getModel().getInteger("id");
		result = this.repository.findOneFollowUpById(followUpId);

		return result;
	}
	
	@Override
	public void unbind(final Request<FollowUp> request, final FollowUp entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "sequenceNumber", "creationMoment", "message", "link");

	}

}
