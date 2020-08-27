
package acme.features.authenticated.investmentRound;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedInvestmentRoundShowService implements AbstractShowService<Authenticated, InvestmentRound> {

	@Autowired
	private AuthenticatedInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");

		result = this.repository.findOneInvestmentRoundById(id);

		return result.sumActivitiesBudgets();
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creationDate", "kindOfRound", "title", "description", "amount", "optionalLink");

		int ivID = entity.getId();

		model.setAttribute("ivID", ivID);

		Principal principal = request.getPrincipal();

		UserAccount user = this.repository.findOneUserAccountById(principal.getAccountId());

		boolean isInvestor = user.hasRole(Investor.class);

		model.setAttribute("isInvestor", isInvestor);

		if (isInvestor) {
			Application application = this.repository.findOneApplicationByInvestorAndInvestmentId(user.getRole(Investor.class).getId(), ivID);
			model.setAttribute("applied", application != null);
		}

		if (entity.getEntrepreneur().getUserAccount().getId() == principal.getAccountId()) {
			model.setAttribute("isCreatorInv", true);
		}

	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneInvestmentRoundById(id);

		return result;
	}

}
