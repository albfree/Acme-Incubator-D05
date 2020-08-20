
package acme.features.bookkeeper.investmentRound;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BookkeeperInvestmentRoundRepository extends AbstractRepository {

	@Query("select iv from InvestmentRound iv where iv.id= ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select ar.investmentRound from AccountingRecord ar where ar.bookkeeper.id = ?1")
	Set<InvestmentRound> findInvestmentRoundsWithMyAccounting(int bookkeeperId);

	@Query("select iv from InvestmentRound iv where iv.id NOT IN(select ar.investmentRound.id from AccountingRecord ar where ar.bookkeeper.id = ?1)")
	Set<InvestmentRound> findInvestmentRoundsWithoutMyAccounting(int bookkeeperId);

}
