
package acme.features.authenticated.bookkeeperRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.components.Response;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.helpers.PrincipalHelper;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedBookkeeperRequesterCreateService implements AbstractCreateService<Authenticated, BookkeeperRequester> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedBookkeeperRequesterRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequester> request) {
		assert request != null;

		Principal principal = request.getPrincipal();
		//Si ya tiene el rol de contable o ha solicitado ser contable, no accesible
		Boolean res = !(principal.hasRole("acme.entities.roles.Bookkeeper") || principal.hasRole("acme.entities.roles.BookkeeperRequester"));

		return res;
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
	public BookkeeperRequester instantiate(final Request<BookkeeperRequester> request) {
		assert request != null;

		BookkeeperRequester result;
		Principal principal;
		int userAccountId;
		UserAccount userAccount;

		principal = request.getPrincipal();
		userAccountId = principal.getAccountId();
		userAccount = this.repository.findOneUserAccountById(userAccountId);

		result = new BookkeeperRequester();
		result.setUserAccount(userAccount);

		return result;
	}

	@Override
	public void validate(final Request<BookkeeperRequester> request, final BookkeeperRequester entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
	}

	@Override
	public void create(final Request<BookkeeperRequester> request, final BookkeeperRequester entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
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
