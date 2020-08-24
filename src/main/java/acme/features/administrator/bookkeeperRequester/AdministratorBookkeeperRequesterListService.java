
package acme.features.administrator.bookkeeperRequester;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractListService;

@Service
public class AdministratorBookkeeperRequesterListService implements AbstractListService<Administrator, BookkeeperRequester> {

	@Autowired
	AdministratorBookkeeperRequesterRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequester> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<BookkeeperRequester> request, final BookkeeperRequester entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "firmName", "responsibilityStatement");
	}

	@Override
	public Collection<BookkeeperRequester> findMany(final Request<BookkeeperRequester> request) {
		assert request != null;

		Collection<BookkeeperRequester> result;

		result = this.repository.findManyBookkeeperRequester();

		return result;
	}

}
