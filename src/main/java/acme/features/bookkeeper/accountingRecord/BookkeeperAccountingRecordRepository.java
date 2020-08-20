
package acme.features.bookkeeper.accountingRecord;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.records.AccountingRecord;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface BookkeeperAccountingRecordRepository extends AbstractRepository {

	@Query("select ar from AccountingRecord ar where ar.id = ?1")
	AccountingRecord findOneAccountingRecordById(int id);

	@Query("select ar from AccountingRecord ar where ar.investmentRound.id = ?1")
	Collection<AccountingRecord> findAccountingRecordsByInvestmentRoundID(int investmentRoundId);

	@Query("select iv from InvestmentRound iv where iv.id = ?1")
	InvestmentRound findOneInvestmentRoundById(int id);

}
