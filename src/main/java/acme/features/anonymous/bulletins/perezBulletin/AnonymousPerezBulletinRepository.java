
package acme.features.anonymous.bulletins.perezBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletins.PerezBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousPerezBulletinRepository extends AbstractRepository {

	@Query("select p from PerezBulletin p")
	Collection<PerezBulletin> findMany();

}
