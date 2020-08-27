
package acme.features.entrepreneur.investmentRound;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.customizations.Customization;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.messages.Message;
import acme.entities.records.AccountingRecord;
import acme.entities.roles.Entrepreneur;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface EntrepreneurInvestmentRoundRepository extends AbstractRepository {

	@Query("select iv from InvestmentRound iv where iv.id= ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select iv from InvestmentRound iv where iv.entrepreneur.id = ?1")
	Collection<InvestmentRound> findInvestmentRoundsByEntrepreneurId(int entrepreneurId);

	@Query("select e from Entrepreneur e where e.userAccount.id = ?1")
	Entrepreneur findOneEntrepreneurByAccountId(int id);

	@Query("select iv from InvestmentRound iv where iv.ticker = ?1")
	InvestmentRound findOneInvestmentRoundByTicker(String ticker);

	@Query("select c from Customization c")
	List<Customization> findCustomizations();

	@Query("select a from Application a where a.investment.id = ?1")
	Collection<Application> findApplicationsByInvestmentRoundId(int id);

	@Query("select ar from AccountingRecord ar where ar.investmentRound.id = ?1")
	Collection<AccountingRecord> findAccountingRecordsByInvestmentRoundId(int id);

	@Query("select f from Forum f where f.investment.id = ?1")
	Forum findForumByInvestmentRoundId(int id);

	@Query("select m from Message m where m.forum.id = ?1")
	Collection<Message> findMessagesByForumId(int id);

}
