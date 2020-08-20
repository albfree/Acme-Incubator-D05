
package acme.features.anonymous.bulletins.lobatoBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletins.LobatoBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousLobatoBulletinRepository extends AbstractRepository {

	@Query("select g from LobatoBulletin g")
	Collection<LobatoBulletin> findMany();
}
