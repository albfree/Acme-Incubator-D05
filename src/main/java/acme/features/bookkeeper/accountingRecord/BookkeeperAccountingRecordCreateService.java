
package acme.features.bookkeeper.accountingRecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.records.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.services.AbstractCreateService;

@Service
public class BookkeeperAccountingRecordCreateService implements AbstractCreateService<Bookkeeper, AccountingRecord> {

	@Autowired
	private BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		int investmentId;
		InvestmentRound investment;

		investmentId = request.getModel().getInteger("irId");
		investment = this.repository.findOneInvestmentRoundById(investmentId);

		return investment.sumActivitiesBudgets();
	}

	@Override
	public void bind(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment");
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "status", "body");

		model.setAttribute("irId", request.getModel().getAttribute("irId"));
	}

	@Override
	public AccountingRecord instantiate(final Request<AccountingRecord> request) {
		assert request != null;

		AccountingRecord result = new AccountingRecord();
		int bookkeeperId = request.getPrincipal().getActiveRoleId();
		Bookkeeper bookkeeper = this.repository.findOneBookkeeperById(bookkeeperId);

		int irId = request.getModel().getInteger("irId");
		InvestmentRound investment = this.repository.findOneInvestmentRoundById(irId);

		Date moment = new Date(System.currentTimeMillis() - 1);

		result.setBookkeeper(bookkeeper);
		result.setInvestmentRound(investment);
		result.setCreationMoment(moment);

		return result;
	}

	@Override
	public void validate(final Request<AccountingRecord> request, final AccountingRecord entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<AccountingRecord> request, final AccountingRecord entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreationMoment(moment);

		this.repository.save(entity);
	}

}
