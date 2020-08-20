
package acme.features.anonymous.bulletins.escobarBulletin;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.bulletins.EscobarBulletin;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AnonymousEscobarBulletinRepository extends AbstractRepository {

	@Query("select e from EscobarBulletin e")
	Collection<EscobarBulletin> findMany();

}
