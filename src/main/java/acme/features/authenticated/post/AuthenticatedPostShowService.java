/*
 * AuthenticatedPostShowService.java
 *
 * Copyright (C) 2012-2022 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.authenticated.post;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.Post;
import acme.framework.components.models.Model;
import acme.framework.controllers.Request;
import acme.framework.roles.Authenticated;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedPostShowService implements AbstractShowService<Authenticated, Post> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected AuthenticatedPostRepository repository;

	@Override
	public boolean authorise(final Request<Post> request) {
		assert request!=null;
		
		return true;
	}

	@Override
	public Post findOne(final Request<Post> request) {
		assert request!=null;
		
		Post result;
		int id;
		
		id = request.getModel().getInteger("id");
		result = this.repository.findOnePostById(id);
		
		return result;
	}

	@Override
	public void unbind(final Request<Post> request, final Post entity, final Model model) {
		assert request!=null;
		assert entity!=null;
		assert model!=null;
		
		request.unbind(entity, model, "creationMoment","caption","message","flag","link");
		
	}

	

}