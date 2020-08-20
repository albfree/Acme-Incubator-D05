
package acme.features.authenticated.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		int id;
		Message message;
		Principal principal;

		id = request.getModel().getInteger("id");
		message = this.repository.findOneMessageById(id);
		principal = request.getPrincipal();

		if (message.getForum().getInvestment().getEntrepreneur().getUserAccount().getId() == principal.getAccountId()) {
			result = true;
		} else {
			for (UserAccount ua : message.getForum().getParticipants()) {
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
