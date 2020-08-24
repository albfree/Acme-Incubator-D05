
package acme.features.authenticated.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractDeleteService;

@Service
public class AuthenticatedForumDeleteService implements AbstractDeleteService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;
		boolean imCreator;
		UserAccount user;
		Forum forum;
		int accId;
		int forumId;

		accId = request.getPrincipal().getAccountId();
		user = this.repository.findOneUserAccountById(accId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);
		imCreator = forum.getCreator().equals(user);

		return imCreator;
	}

	@Override
	public void bind(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "participants");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;
		Forum res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneForumById(id);
		return res;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Forum> request, final Forum entity) {

		this.repository.delete(entity);
	}

}
