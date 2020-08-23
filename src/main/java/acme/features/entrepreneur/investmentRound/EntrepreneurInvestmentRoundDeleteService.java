
package acme.features.entrepreneur.investmentRound;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.applications.Application;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.records.AccountingRecord;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractDeleteService;

@Service
public class EntrepreneurInvestmentRoundDeleteService implements AbstractDeleteService<Entrepreneur, InvestmentRound> {

	@Autowired
	private EntrepreneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		boolean result;
		int investmentID;
		Collection<Application> applications;
		InvestmentRound investment;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentID = request.getModel().getInteger("id");
		applications = this.repository.findApplicationsByInvestmentRoundId(investmentID);
		investment = this.repository.findOneInvestmentRoundById(investmentID);
		entrepreneur = investment.getEntrepreneur();
		principal = request.getPrincipal();

		result = entrepreneur.getUserAccount().getId() == principal.getAccountId() && applications.isEmpty();

		return result;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationDate");
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creationDate", "kindOfRound", "title", "description", "amount", "optionalLink");
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

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		if (entity.getWorkProgramme() != null && !entity.getWorkProgramme().isEmpty()) {
			this.repository.deleteAll(entity.getWorkProgramme());
		}

		int investmentID = entity.getId();

		Collection<AccountingRecord> accountings = this.repository.findAccountingRecordsByInvestmentRoundId(investmentID);

		if (accountings != null && !accountings.isEmpty()) {
			this.repository.deleteAll(accountings);
		}

		Forum forum = this.repository.findForumByInvestmentRoundId(investmentID);

		if (forum != null) {
			Collection<Message> messages = this.repository.findMessagesByForumId(forum.getId());

			if (messages != null && !messages.isEmpty()) {
				this.repository.deleteAll(messages);
			}
			this.repository.delete(forum);
		}

		this.repository.delete(entity);
	}

}
