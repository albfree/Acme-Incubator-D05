
package acme.features.authenticated.forum;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

		Principal principal;
		int currentUserId;
		UserAccount currentUserAccount;
		int forumId;
		Forum forum;
		boolean isParticipant;
		boolean isCreator;

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
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

		int forumId = entity.getId();

		model.setAttribute("forumId", forumId);

		int currentUserId = request.getPrincipal().getAccountId();
		UserAccount currentUserAccount = this.repository.findOneUserAccountById(currentUserId);

		boolean isCreator;

		if (entity.getInvestment() != null) {
			isCreator = entity.getInvestment().getEntrepreneur().getUserAccount().equals(currentUserAccount);
		} else {
			isCreator = entity.getCreator().equals(currentUserAccount);
		}

		model.setAttribute("imCreator", isCreator);

		if (isCreator) {
			Collection<UserAccount> possibleParticipants;
			Collection<UserAccount> participants;

			possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class) && !x.getUsername().equals("administrator")).collect(Collectors.toList());
			participants = entity.getParticipants();
			possibleParticipants.remove(currentUserAccount);

			List<String> userIds = possibleParticipants.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
			List<String> userNames = possibleParticipants.stream().map(x -> x.getUsername()).collect(Collectors.toList());

			String[] ids = userIds.stream().toArray(i -> new String[i]);
			String[] names = userNames.stream().toArray(i -> new String[i]);

			model.setAttribute("names", names);
			model.setAttribute("ids", ids);

			for (UserAccount ua : possibleParticipants) {
				Integer uaId = ua.getId();
				if (participants.contains(ua)) {
					model.setAttribute(uaId.toString(), true);
				} else {
					model.setAttribute(uaId.toString(), false);
				}

			}
		}
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
