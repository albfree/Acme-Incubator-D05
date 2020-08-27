
package acme.features.authenticated.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedMessageShowService implements AbstractShowService<Authenticated, Message> {

	@Autowired
	private AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		Principal principal;
		int currentUserId;
		UserAccount currentUserAccount;
		int messageId;
		Message message;
		Forum forum;
		boolean isParticipant;
		boolean isCreator;

		principal = request.getPrincipal();
		currentUserId = principal.getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneMessageById(messageId);
		forum = message.getForum();

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

		request.unbind(entity, model, "title", "creationMoment", "tags", "body", "user.identity.fullName");

	}

	@Override
	public Message findOne(final Request<Message> request) {
		assert request != null;

		Message result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneMessageById(id);

		return result;
	}

}
