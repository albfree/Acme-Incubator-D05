
package acme.features.bookkeeper.accountingRecord;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.records.AccountingRecord;
import acme.entities.roles.Bookkeeper;
import acme.entities.status.StatusAccounting;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractListService;

@Service
public class BookkeeperAccountingRecordListService implements AbstractListService<Bookkeeper, AccountingRecord> {

	@Autowired
	BookkeeperAccountingRecordRepository repository;


	@Override
	public boolean authorise(final Request<AccountingRecord> request) {
		assert request != null;

		int ivID;
		InvestmentRound investment;

		ivID = request.getModel().getInteger("id");

		investment = this.repository.findOneInvestmentRoundById(ivID);

		return investment.sumActivitiesBudgets();
	}

	@Override
	public void unbind(final Request<AccountingRecord> request, final AccountingRecord entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment", "title", "status");
	}

	@Override
	public Collection<AccountingRecord> findMany(final Request<AccountingRecord> request) {
		assert request != null;

		Collection<AccountingRecord> result = new ArrayList<>();
		Collection<AccountingRecord> temporalResult;
		Principal principal;
		int ivID;

		ivID = request.getModel().getInteger("id");
		principal = request.getPrincipal();
		temporalResult = this.repository.findAccountingRecordsByInvestmentRoundID(ivID);

		for (AccountingRecord ar : temporalResult) {
			if (ar.getBookkeeper().getUserAccount().getId() == principal.getAccountId()) {
				result.add(ar);
			} else {
				if (ar.getStatus().equals(StatusAccounting.PUBLISHED)) {
					result.add(ar);
				}
			}
		}

		return result;
	}

}
