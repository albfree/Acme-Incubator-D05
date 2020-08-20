
package acme.features.authenticated.activity;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.activities.Activity;
import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedActivityRepository extends AbstractRepository {

	@Query("select a from Activity a where a.id= ?1")
	Activity findOneActivityById(int id);

	@Query("select a from Activity a where a.investment.id= ?1")
	Collection<Activity> findActivitiesByInvestmentRound(int ivID);

	@Query("select iv from InvestmentRound iv where iv.id= ?1")
	InvestmentRound findInvestmentRoundById(int id);

}
