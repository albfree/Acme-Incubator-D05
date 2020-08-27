
package acme.features.entrepreneur.activity;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepreneurActivityDeleteService implements AbstractDeleteService<Entrepreneur, Activity> {

	@Autowired
	private EntrepreneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result;
		int activityID;
		Activity activity;
		InvestmentRound investment;
		Entrepreneur entrepreneur;
		Principal principal;

		activityID = request.getModel().getInteger("id");
		activity = this.repository.findOneActivityById(activityID);
		investment = activity.getInvestment();
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
	public Activity findOne(final Request<Activity> request) {
		assert request != null;

		Activity result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneActivityById(id);

		return result;
	}

	@Override
	public void validate(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<Activity> request, final Activity entity) {
		assert request != null;
		assert entity != null;

		Collection<Activity> activities = entity.getInvestment().getWorkProgramme();

		activities.remove(entity);

		if (!activities.isEmpty()) {
			Double sum = 0.;
			for (Activity a : activities) {
				sum += a.getBudget().getAmount();
			}

			if (sum.equals(entity.getInvestment().getAmount().getAmount())) {
				Forum forum = new Forum();
				String title;

				if (request.getLocale().getLanguage().equals("en")) {
					title = "Investment Round Forum: " + entity.getInvestment().getTicker();
				} else {
					title = "Foro del Investment Round: " + entity.getInvestment().getTicker();
				}

				forum.setTitle(title);
				forum.setInvestment(entity.getInvestment());
				this.repository.save(forum);
			}
		}

		this.repository.delete(entity);
	}

}
