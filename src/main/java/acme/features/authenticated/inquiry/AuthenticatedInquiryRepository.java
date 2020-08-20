
package acme.features.authenticated.inquiry;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.inquiries.Inquiry;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedInquiryRepository extends AbstractRepository {

	@Query("select i from Inquiry i where i.deadline > CURRENT_TIMESTAMP")
	Collection<Inquiry> findMany();

	@Query("select i from Inquiry i where i.id = ?1 and i.deadline > CURRENT_TIMESTAMP")
	Inquiry findOneById(int id);
}
