
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
		int id;
		Forum forum;
		Principal principal;

		id = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(id);
		principal = request.getPrincipal();

		if (forum.getInvestment().getEntrepreneur().getUserAccount().getId() == principal.getAccountId()) {
			result = true;
		} else {
			for (UserAccount ua : forum.getParticipants()) {
				if (ua.getId() == principal.getAccountId()) {
					result = true;
					break;
				}
			}
		}

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
