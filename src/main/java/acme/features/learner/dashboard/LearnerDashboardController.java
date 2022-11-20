package acme.features.learner.dashboard;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.entities.dashboard.LearnerDashboard;
import acme.framework.controllers.AbstractController;
import acme.roles.Learner;

@Controller
public class LearnerDashboardController extends AbstractController<Learner,LearnerDashboard> {
	@Autowired
	protected LearnerDashboardShowService showService;
	
	@PostConstruct
	protected void initialise() {
		super.addCommand("show", this.showService);
	}
}
