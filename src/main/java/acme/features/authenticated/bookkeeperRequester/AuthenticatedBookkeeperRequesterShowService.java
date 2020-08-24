
package acme.features.authenticated.bookkeeperRequester;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedBookkeeperRequesterShowService implements AbstractShowService<Authenticated, BookkeeperRequester> {

	@Autowired
	private AuthenticatedBookkeeperRequesterRepository repository;


	@Override
	public boolean authorise(final Request<BookkeeperRequester> request) {
		assert request != null;

		Principal principal = request.getPrincipal();
		//Si ya tiene el rol de contable o ha solicitado ser contable, no accesible
		Boolean res = principal.hasRole("acme.entities.roles.Bookkeeper") || principal.hasRole("acme.entities.roles.BookkeeperRequester");

		return res;
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

		Principal principal = request.getPrincipal();
		result = this.repository.findOneBookkeeperRequesterById(principal.getAccountId());

		return result;
	}

}
