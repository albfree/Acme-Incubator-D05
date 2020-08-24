
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

		boolean result = false;
		boolean imCreator;
		boolean imParticipant;
		int forumId;
		int accId;
		Forum forum;
		Principal principal;
		UserAccount user;

		principal = request.getPrincipal();
		accId = principal.getAccountId();
		user = this.repository.findOneUserAccountById(accId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);

		imParticipant = forum.getParticipants().contains(user);

		if (forum.getInvestment() != null) {
			imCreator = forum.getInvestment().getEntrepreneur().getUserAccount().equals(user);
		} else {
			imCreator = forum.getCreator() == user;
		}

		result = imCreator || imParticipant;

		return result;
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
