
package acme.features.entrepreneur.activity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurActivityCreateService implements AbstractCreateService<Entrepreneur, Activity> {

	@Autowired
	private EntrepreneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result;
		int investmentID;
		InvestmentRound investment;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentID = request.getModel().getInteger("invId");
		investment = this.repository.findOneInvestmentRoundById(investmentID);
		entrepreneur = investment.getEntrepreneur();
		principal = request.getPrincipal();

		result = entrepreneur.getUserAccount().getId() == principal.getAccountId() && !investment.sumActivitiesBudgets();

		return result;
	}

	@Override
	public void bind(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startDateTime", "endDateTime", "budget");
	}

	@Override
	public Activity instantiate(final Request<Activity> request) {
		assert request != null;

		Activity result = new Activity();

		return result;
	}

	@Override
	public void validate(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("startDateTime") && !errors.hasErrors("endDateTime")) {
			errors.state(request, entity.getEndDateTime().after(entity.getStartDateTime()), "startDateTime", "entrepreneur.activity.error.start-date.before");
		}
	}

	@Override
	public void create(final Request<Activity> request, final Activity entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

	}

}
