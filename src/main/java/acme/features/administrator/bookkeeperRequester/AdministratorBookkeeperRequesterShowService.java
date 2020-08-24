
package acme.features.administrator.bookkeeperRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractShowService;

@Service
public class AdministratorBookkeeperRequesterShowService implements AbstractShowService<Administrator, BookkeeperRequester> {

	@Autowired
	private AdministratorBookkeeperRequesterRepository repository;


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
	public BookkeeperRequester findOne(final Request<BookkeeperRequester> request) {
		assert request != null;

		BookkeeperRequester result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneBookkeeperRequesterById(id);

		return result;
	}

}
