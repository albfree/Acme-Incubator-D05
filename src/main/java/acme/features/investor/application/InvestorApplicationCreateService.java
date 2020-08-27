
package acme.features.investor.application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class InvestorApplicationCreateService implements AbstractCreateService<Investor, Application> {

	@Autowired
	private InvestorApplicationRepository repository;


	@Override
	public boolean authorise(final Request<Application> request) {
		assert request != null;

		boolean result;
		Principal principal;
		int investmentId;
		Application application;
		InvestmentRound investment;

		principal = request.getPrincipal();
		investmentId = request.getModel().getInteger("invId");
		application = this.repository.findOneApplicationByInvestorAndInvestmentId(principal.getActiveRoleId(), investmentId);
		investment = this.repository.findOneInvestmentRoundById(investmentId);

		result = application == null && investment.getEntrepreneur().getUserAccount().getId() != principal.getAccountId() && investment.sumActivitiesBudgets();

		return result;
	}

	@Override
	public void bind(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationDate", "status");
	}

	@Override
	public void unbind(final Request<Application> request, final Application entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "statement", "investmentOffer");

		int investmentID;

		investmentID = request.getModel().getInteger("invId");
		model.setAttribute("ivID", investmentID);
	}

	@Override
	public Application instantiate(final Request<Application> request) {
		assert request != null;

		Application result = new Application();
		Principal principal;
		int investmentId;
		Investor investor;
		InvestmentRound investment;

		principal = request.getPrincipal();
		investmentId = request.getModel().getInteger("invId");
		investor = this.repository.findOneInvestorById(principal.getActiveRoleId());
		investment = this.repository.findOneInvestmentRoundById(investmentId);
		Date moment = new Date(System.currentTimeMillis() - 1);

		result.setCreationDate(moment);
		result.setInvestor(investor);
		result.setInvestment(investment);
		result.setStatus("PENDING");

		return result;
	}

	@Override
	public void validate(final Request<Application> request, final Application entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean tickerIsCorrect = true;

		if (!errors.hasErrors("ticker")) {
			String[] ticker = entity.getTicker().split("-");
			String investorSector;

			if (entity.getInvestor().getActivitySector().length() >= 3) {
				investorSector = entity.getInvestor().getActivitySector().substring(0, 3).toUpperCase();
			} else {
				investorSector = entity.getInvestor().getActivitySector().toUpperCase();
			}

			if (investorSector.length() == 3 && !investorSector.equals(ticker[0]) || investorSector.length() == 2 && !ticker[0].equals(investorSector + "X") || investorSector.length() == 1 && !ticker[0].equals(investorSector + "XX")) {
				tickerIsCorrect = false;
				errors.state(request, false, "ticker", "investor.application.error.ticker.sector", entity.getInvestor().getActivitySector());
			}

			DateFormat dateFormat = new SimpleDateFormat("yy");
			String yearDigits = dateFormat.format(Calendar.getInstance().getTime());

			if (!ticker[1].equals(yearDigits)) {
				tickerIsCorrect = false;
				errors.state(request, false, "ticker", "investor.application.error.ticker.year");
			}
		}

		if (tickerIsCorrect && this.repository.findOneApplicationByTicker(entity.getTicker()) != null) {
			errors.state(request, false, "ticker", "investor.application.error.ticker.exists");
		}
	}

	@Override
	public void create(final Request<Application> request, final Application entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setStatus("PENDING");

		entity.setCreationDate(moment);

		this.repository.save(entity);
	}

}
