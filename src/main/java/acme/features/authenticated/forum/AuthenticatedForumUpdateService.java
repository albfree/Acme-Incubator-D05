
package acme.features.authenticated.forum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.forums.Forum;
import acme.framework.components.Errors;
import acme.framework.components.HttpMethod;
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

		boolean isCreator;
		int currentUserId;
		UserAccount currentUserAccount;
		Forum forum;
		int forumId;

		currentUserId = request.getPrincipal().getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		forumId = request.getModel().getInteger("id");
		forum = this.repository.findOneForumById(forumId);

		if (forum.getInvestment() != null) {
			isCreator = forum.getInvestment().getEntrepreneur().getUserAccount().equals(currentUserAccount);
		} else {
			isCreator = forum.getCreator().equals(currentUserAccount);
		}

		return isCreator;
	}

	@Override
	public void bind(final Request<Forum> request, final Forum entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "investment", "creator");
	}

	@Override
	public void unbind(final Request<Forum> request, final Forum entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title");

		Collection<UserAccount> possibleParticipants;
		Collection<UserAccount> participants;
		UserAccount currentUserAccount;
		int currentUserId;

		currentUserId = request.getPrincipal().getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
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

		model.setAttribute("imCreator", true);
	}

	@Override
	public Forum findOne(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int forumId;

		forumId = request.getModel().getInteger("id");
		result = this.repository.findOneForumById(forumId);

		if (request.isMethod(HttpMethod.POST)) {
			request.getModel().setAttribute("imCreator", true);
			UserAccount creator;

			if (result.getInvestment() != null) {
				creator = result.getInvestment().getEntrepreneur().getUserAccount();
			} else {
				creator = result.getCreator();
			}

			Collection<UserAccount> participants;
			participants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class) && !x.getUsername().equals("administrator")).collect(Collectors.toList());
			participants.remove(creator);

			List<String> userIds = participants.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
			List<String> userNames = participants.stream().map(x -> x.getUsername()).collect(Collectors.toList());

			String[] ids = userIds.stream().toArray(i -> new String[i]);
			String[] names = userNames.stream().toArray(i -> new String[i]);

			request.getModel().setAttribute("ids", ids);
			request.getModel().setAttribute("names", names);

			for (UserAccount ua : participants) {
				Integer uaId = ua.getId();

				if (request.getModel().getAttribute(uaId.toString(), boolean.class)) {
					request.getModel().setAttribute(uaId.toString(), true);
				} else {
					request.getModel().setAttribute(uaId.toString(), false);
				}
			}
		}

		return result;
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

		possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class) && !x.getUsername().equals("administrator")).collect(Collectors.toList());
		participants = new ArrayList<UserAccount>();

		if (entity.getInvestment() != null) {
			possibleParticipants.remove(entity.getInvestment().getEntrepreneur().getUserAccount());
		} else {
			possibleParticipants.remove(entity.getCreator());
		}

		for (UserAccount ua : possibleParticipants) {
			Integer uaId = ua.getId();

			if (request.getModel().getAttribute(uaId.toString(), boolean.class)) {
				participants.add(ua);
			}
		}

		entity.setParticipants(participants);

		this.repository.save(entity);
	}

}
