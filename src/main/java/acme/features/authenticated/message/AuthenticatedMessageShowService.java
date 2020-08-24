
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

		boolean result = false;
		boolean imCreator;
		boolean imParticipant;
		int messageId;
		int accId;
		Message message;
		Forum forum;
		Principal principal;
		UserAccount user;

		principal = request.getPrincipal();
		accId = principal.getAccountId();
		user = this.repository.findOneUserAccountById(accId);
		messageId = request.getModel().getInteger("id");
		message = this.repository.findOneMessageById(messageId);
		forum = message.getForum();

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
