
package acme.features.anonymous.bulletins.guerreroBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletins.GuerreroBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousGuerreroBulletinRepository extends AbstractRepository {

	@Query("select g from GuerreroBulletin g")
	Collection<GuerreroBulletin> findMany();
}
