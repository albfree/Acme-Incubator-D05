
package acme.features.authenticated.investmentRound;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedInvestmentRoundRepository extends AbstractRepository {

	@Query("select iv from InvestmentRound iv where iv.id= ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

	@Query("select iv from InvestmentRound iv")
	Collection<InvestmentRound> findActiveInvestmentRounds();

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select a from Application a where a.investor.id = ?1 and a.investment.id = ?2")
	Application findOneApplicationByInvestorAndInvestmentId(int investorId, int investmentId);
}
