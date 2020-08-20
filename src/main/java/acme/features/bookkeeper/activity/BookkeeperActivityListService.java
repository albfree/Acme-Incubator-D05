
package acme.features.bookkeeper.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractListService;

@Service
public class BookkeeperActivityListService implements AbstractListService<Bookkeeper, Activity> {

	@Autowired
	private BookkeeperActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result = true;

		if (!request.getModel().hasAttribute("id")) {
			result = false;
		} else {
			int id = request.getModel().getInteger("id");

			InvestmentRound investment = this.repository.findInvestmentRoundById(id);

			result = investment.sumActivitiesBudgets();
		}

		return result;
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "budget");

	}

	@Override
	public Collection<Activity> findMany(final Request<Activity> request) {
		assert request != null;

		Collection<Activity> result;
		int ivID;

		ivID = request.getModel().getInteger("id");

		result = this.repository.findActivitiesByInvestmentRound(ivID);

		return result;
	}

}
