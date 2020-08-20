
package acme.features.administrator.customization;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.customizations.Customization;
import acme.framework.repositories.AbstractRepository;

@Repository
public interface AdministratorCustomizationRepository extends AbstractRepository {

	@Query("select c from Customization c where c.id=?1")
	Customization findOneById(int id);

	@Query("select c from Customization c")
	Collection<Customization> findMany();

}
