
package acme.features.entrepreneur.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepreneurApplicationUpdateService implements AbstractUpdateService<Entrepreneur, Application> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private EntrepreneurApplicationRepository repository;


	// AbstractShowService<Employer, Application> interface -------------

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
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creationDate", "statement", "investmentOffer", "status");

		//		if (entity.getStatus().equals("REJECTED")) {
		//			request.unbind(entity, model, "ticker", "creationDate", "statement", "investmentOffer", "status", "rejectReason");
		//		} else {
		//			request.unbind(entity, model, "ticker", "creationDate", "statement", "investmentOffer", "status");
		//		}
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
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (request.getModel().getString("status").equals("REJECTED")) {
			request.bind(entity, errors, "status", "rejectReason");
		}
		if (request.getModel().getString("status").equals("ACCEPTED")) {
			request.bind(entity, errors, "status");
		}
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (request.getModel().getString("status").equals("REJECTED") /* && !(request.getModel().getString("status").equals("ACCEPTED") || request.getModel().getString("status").equals("PENDING")) */ && !errors.hasErrors("rejectReason")) {
			errors.state(request, !request.getModel().getString("rejectReason").isEmpty(), "rejectReason", "entrepreneur.application.error.notBlank");
		}
	}

	@Override
	public void update(final Request<Application> request, final Application entity) {
		// TODO Auto-generated method stub
		assert request != null;
		assert entity != null;

		entity.setStatus(request.getModel().getString("status"));
		if (request.getModel().getString("status").equals("REJECTED")) {
			if (!request.getModel().getString("rejectReason").isEmpty()) {
				entity.setRejectReason(request.getModel().getString("rejectReason"));
			}
		}

		this.repository.save(entity);
	}

}
