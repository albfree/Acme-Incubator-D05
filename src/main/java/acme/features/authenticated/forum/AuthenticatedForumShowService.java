
package acme.features.authenticated.forum;

import java.util.ArrayList;
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
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

		int forumId = entity.getId();

		model.setAttribute("forumId", forumId);

		int accId = request.getPrincipal().getAccountId();
		UserAccount user = this.repository.findOneUserAccountById(accId);
		boolean imCreator = entity.getCreator().equals(user);

		Collection<UserAccount> possibleParticipants;
		Collection<UserAccount> participants;

		participants = entity.getParticipants();
		possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class)).collect(Collectors.toCollection(ArrayList::new));
		possibleParticipants.remove(user);

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

		model.setAttribute("imCreator", imCreator);
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
