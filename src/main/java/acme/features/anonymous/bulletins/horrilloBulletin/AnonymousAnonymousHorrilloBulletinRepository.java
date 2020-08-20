
package acme.features.anonymous.bulletins.horrilloBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletins.HorrilloBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousAnonymousHorrilloBulletinRepository extends AbstractRepository {

	@Query("select hb from HorrilloBulletin hb")
	Collection<HorrilloBulletin> findMany();

}
