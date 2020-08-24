
package acme.features.administrator.bookkeeperRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Administrator;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractDeleteService;

@Service
public class AdministratorBookkeeperRequesterDeleteService implements AbstractDeleteService<Administrator, BookkeeperRequester> {

	@Autowired
	private AdministratorBookkeeperRequesterRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequester> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<BookkeeperRequester> request, final BookkeeperRequester entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
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

	@Override
	public void validate(final Request<BookkeeperRequester> request, final BookkeeperRequester entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void delete(final Request<BookkeeperRequester> request, final BookkeeperRequester entity) {
		assert request != null;
		assert entity != null;

		this.repository.delete(entity);
	}

	@Override
	public void onSuccess(final Request<BookkeeperRequester> request, final Response<BookkeeperRequester> response) {
		assert request != null;
		assert response != null;

		if (request.isMethod(HttpMethod.POST)) {
			PrincipalHelper.handleUpdate();
		}
	}

}
