
package acme.features.authenticated.forum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractShowService;

@Service
public class AuthenticatedForumShowService implements AbstractShowService<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
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
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

		int forumID = entity.getId();

		model.setAttribute("forumID", forumID);

	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneForumById(id);

		return result;
	}

}
