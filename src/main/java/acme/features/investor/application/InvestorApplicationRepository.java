
package acme.features.investor.application;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.applications.Application;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Investor;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface InvestorApplicationRepository extends AbstractRepository {

	@Query("select a from Application a where a.id= ?1")
	Application findOneApplicationById(int applicationID);

	@Query("select a from Application a where a.investor.id= ?1")
	Collection<Application> findMyApplications(int id);

	@Query("select a from Application a where a.investor.id = ?1 and a.investment.id = ?2")
	Application findOneApplicationByInvestorAndInvestmentId(int investorId, int investmentId);

	@Query("select a from Application a where a.ticker = ?1")
	Application findOneApplicationByTicker(String ticker);

	@Query("select i from Investor i where i.id = ?1")
	Investor findOneInvestorById(int id);

	@Query("select iv from InvestmentRound iv where iv.id = ?1")
	InvestmentRound findOneInvestmentRoundById(int id);
}
