
package acme.features.entrepreneur.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepreneurApplicationUpdateService implements AbstractUpdateService<Entrepreneur, Application> {

	@Autowired
	private EntrepreneurApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		int applicationId;
		InvestmentRound investment;
		Application application;
		Entrepreneur entrepreneur;
		Principal principal;

		applicationId = request.getModel().getInteger("id");
		application = this.repository.findOneApplicationById(applicationId);
		investment = application.getInvestment();
		entrepreneur = investment.getEntrepreneur();
		principal = request.getPrincipal();
		result = entrepreneur.getUserAccount().getId() == principal.getAccountId() && application.getStatus().equals("PENDING");

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "ticker", "creationDate", "statement", "investmentOffer", "investment", "investor");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creationDate", "statement", "investmentOffer", "status", "investment.ticker");
	}

	@Override
	public Application findOne(final Request<Application> request) {
		assert request != null;

		Application result;
		int applicationID;

		applicationID = request.getModel().getInteger("id");
		result = this.repository.findOneApplicationById(applicationID);

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("status") && entity.getStatus().equals("REJECTED") && !errors.hasErrors("rejectReason")) {
			errors.state(request, !entity.getRejectReason().isEmpty(), "rejectReason", "entrepreneur.application.error.justification");
		}

		if (!errors.hasErrors("status") && entity.getStatus().equals("PENDING")) {
			errors.state(request, false, "status", "entrepreneur.application.error.pending");
		}
	}

	@Override
	public void update(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);

		if (entity.getStatus().equals("ACCEPTED")) {
			Forum forum = this.repository.findOneForumByInvestmentRoundId(entity.getInvestment().getId());

			if (forum != null) {
				forum.getParticipants().add(entity.getInvestor().getUserAccount());
			}
		}
	}

}
