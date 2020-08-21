
package acme.features.entrepreneur.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
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

}
