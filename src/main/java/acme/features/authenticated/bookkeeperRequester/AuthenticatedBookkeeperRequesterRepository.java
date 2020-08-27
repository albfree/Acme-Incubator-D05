
package acme.features.authenticated.bookkeeperRequester;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AuthenticatedBookkeeperRequesterRepository extends AbstractRepository {

	@Query("select br from BookkeeperRequester br where br.userAccount.id = ?1")
	BookkeeperRequester findOneBookkeeperRequesterById(int id);

	@Query("select ua from UserAccount ua where ua.id = ?1")
	UserAccount findOneUserAccountById(int id);

}
