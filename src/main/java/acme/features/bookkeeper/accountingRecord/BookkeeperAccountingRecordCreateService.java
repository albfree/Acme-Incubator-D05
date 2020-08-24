
package acme.features.bookkeeper.accountingRecord;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.records.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class BookkeeperAccountingRecordCreateService implements AbstractCreateService<Bookkeeper, AccountingRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		return true;
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

		request.unbind(entity, model, "title", "status", "creationMoment", "body", "bookkeeper", "investmentRound");

		if (request.isMethod(HttpMethod.GET)) {
			model.setAttribute("irId", request.getModel().getAttribute("irId"));
		}
	}

	@Override
	public AccountingRecord instantiate(final Request<AccountingRecord> request) {
		assert request != null;

		AccountingRecord result = new AccountingRecord();
		Integer bookkeeperId = request.getPrincipal().getActiveRoleId();
		Bookkeeper bookkeeper = this.repository.findOneBookkeeperById(bookkeeperId);

		Integer irId = request.getModel().getInteger("irId");
		InvestmentRound ir = this.repository.findOneInvestmentRoundById(irId);

		Date moment = new Date(System.currentTimeMillis() - 1);

		result.setBookkeeper(bookkeeper);
		result.setInvestmentRound(ir);
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

		Integer bookkeeperId = request.getPrincipal().getActiveRoleId();
		Bookkeeper bookkeeper = this.repository.findOneBookkeeperById(bookkeeperId);

		Integer irId = request.getModel().getInteger("irId");
		InvestmentRound ir = this.repository.findOneInvestmentRoundById(irId);

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setBookkeeper(bookkeeper);
		entity.setInvestmentRound(ir);
		entity.setCreationMoment(moment);

		this.repository.save(entity);
	}

	@Override
	public void onSuccess(final Request<AccountingRecord> request, final Response<AccountingRecord> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
