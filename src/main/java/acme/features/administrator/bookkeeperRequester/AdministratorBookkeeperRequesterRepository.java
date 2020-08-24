
package acme.features.administrator.bookkeeperRequester;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorBookkeeperRequesterRepository extends AbstractRepository {

	@Query("select ua from UserAccount ua where ua.id=?1")
	UserAccount findOneUserAccountById(int id);

	@Query("select br from BookkeeperRequester br")
	Collection<BookkeeperRequester> findManyBookkeeperRequester();

	@Query("select br from BookkeeperRequester br where br.id=?1")
	BookkeeperRequester findOneBookkeeperRequesterById(int id);

}
