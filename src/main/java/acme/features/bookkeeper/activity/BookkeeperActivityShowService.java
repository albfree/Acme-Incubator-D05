
package acme.features.bookkeeper.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractShowService;

@Service
public class BookkeeperActivityShowService implements AbstractShowService<Bookkeeper, Activity> {

	@Autowired
	private BookkeeperActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result = true;
		int id;

		id = request.getModel().getInteger("id");

		Activity act = this.repository.findOneActivityById(id);

		if (!act.getInvestment().sumActivitiesBudgets()) {
			result = false;
		}

		return result;
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startDateTime", "endDateTime", "budget");
	}

	@Override
	public Activity findOne(final Request<Activity> request) {
		assert request != null;

		Activity result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneActivityById(id);

		return result;
	}

}
