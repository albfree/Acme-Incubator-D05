
package acme.features.authenticated.forum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractUpdateService;

@Service
public class AuthenticatedForumUpdateService implements AbstractUpdateService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;
		boolean imCreator;
		UserAccount user;
		Forum forum;
		int accId;
		int forumId;

		accId = request.getPrincipal().getAccountId();
		user = this.repository.findOneUserAccountById(accId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);
		imCreator = forum.getCreator().equals(user);

		return imCreator;
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

		Collection<UserAccount> possibleParticipants;
		Collection<UserAccount> participants;
		UserAccount me;
		int accId;

		accId = request.getPrincipal().getAccountId();
		me = this.repository.findOneUserAccountById(accId);
		participants = entity.getParticipants();
		possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class)).collect(Collectors.toCollection(ArrayList::new));

		List<String> userIds = possibleParticipants.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
		List<String> userNames = possibleParticipants.stream().map(x -> x.getUsername()).collect(Collectors.toList());
		possibleParticipants.remove(me);

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

		request.unbind(entity, model, "title");
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;
		Forum res;
		int id;

		id = request.getModel().getInteger("id");
		res = this.repository.findOneForumById(id);
		return res;
	}

	@Override
	public void validate(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void update(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;
		Collection<UserAccount> possibleParticipants;
		Collection<UserAccount> participants;

		possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class)).collect(Collectors.toCollection(ArrayList::new));
		participants = new ArrayList<UserAccount>();

		for (UserAccount ua : possibleParticipants) {
			Integer uaId = ua.getId();
			boolean checked = request.getModel().getAttribute(uaId.toString(), boolean.class);
			if (checked == true) {
				participants.add(ua);
			}
		}

		entity.setParticipants(participants);
		this.repository.save(entity);
	}

}
