
package acme.features.authenticated.forum;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
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

		boolean isCreator;
		int currentUserId;
		UserAccount currentUserAccount;
		int forumId;
		Forum forum;

		currentUserId = request.getPrincipal().getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);

		if (forum.getInvestment() != null) {
			isCreator = forum.getInvestment().getEntrepreneur().getUserAccount().equals(currentUserAccount);
		} else {
			isCreator = forum.getCreator().equals(currentUserAccount);
		}

		return isCreator;
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

		request.unbind(entity, model, "title");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int forumId;

		forumId = request.getModel().getInteger("id");
		result = this.repository.findOneForumById(forumId);

		return result;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void delete(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		int forumId;
		Collection<Message> messages;

		forumId = entity.getId();
		messages = this.repository.findMessagesOfAForum(forumId);

		if (messages != null && !messages.isEmpty()) {
			this.repository.deleteAll(messages);
		}

		this.repository.delete(entity);
	}

}
