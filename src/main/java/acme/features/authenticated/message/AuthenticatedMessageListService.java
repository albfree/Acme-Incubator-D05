
package acme.features.authenticated.message;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractListService;

@Service
public class AuthenticatedMessageListService implements AbstractListService<Authenticated, Message> {

	@Autowired
	private AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		assert request != null;

		Principal principal;
		int currentUserId;
		UserAccount currentUserAccount;
		int forumId;
		Forum forum;
		boolean isCreator;
		boolean isParticipant;

		principal = request.getPrincipal();
		currentUserId = principal.getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);

		isParticipant = forum.getParticipants().contains(currentUserAccount);

		if (forum.getInvestment() != null) {
			isCreator = forum.getInvestment().getEntrepreneur().getUserAccount().equals(currentUserAccount);
		} else {
			isCreator = forum.getCreator().equals(currentUserAccount);
		}

		return isParticipant || isCreator;
	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "creationMoment", "title");

	}

	@Override
	public Collection<Message> findMany(final Request<Message> request) {
		assert request != null;

		Collection<Message> result;
		int forumId;

		forumId = request.getModel().getInteger("id");

		result = this.repository.findMessagesOfAForum(forumId);

		return result;
	}

}
