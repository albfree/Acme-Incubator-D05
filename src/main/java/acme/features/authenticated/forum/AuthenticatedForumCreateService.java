
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
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedForumCreateService implements AbstractCreateService<Authenticated, Forum> {

	@Autowired
	AuthenticatedForumRepository repository;


	@Override
	public boolean authorise(final Request<Forum> request) {
		assert request != null;

		return true;
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

		Collection<UserAccount> participants;
		int userAccountId;
		UserAccount creator;

		userAccountId = request.getPrincipal().getAccountId();
		creator = this.repository.findOneUserAccountById(userAccountId);
		participants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class) && !x.getUsername().equals("administrator")).collect(Collectors.toList());
		participants.remove(creator);

		List<String> userIds = participants.stream().map(x -> String.valueOf(x.getId())).collect(Collectors.toList());
		List<String> userNames = participants.stream().map(x -> x.getUsername()).collect(Collectors.toList());

		String[] ids = userIds.stream().toArray(i -> new String[i]);
		String[] names = userNames.stream().toArray(i -> new String[i]);

		model.setAttribute("ids", ids);
		model.setAttribute("names", names);

		for (UserAccount ua : participants) {
			Integer uaId = ua.getId();
			model.setAttribute(uaId.toString(), false);
		}

		request.unbind(entity, model, "title");
	}

	@Override
	public Forum instantiate(final Request<Forum> request) {
		assert request != null;

		Forum result;
		int userAccountId;
		UserAccount creator;

		result = new Forum();
		userAccountId = request.getPrincipal().getAccountId();
		creator = this.repository.findOneUserAccountById(userAccountId);

		result.setCreator(creator);

		if (request.isMethod(HttpMethod.POST)) {

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
	public void create(final Request<Forum> request, final Forum entity) {
		assert request != null;
		assert entity != null;

		Collection<UserAccount> possibleParticipants;
		Collection<UserAccount> participants;

		possibleParticipants = this.repository.findManyUserAccount().stream().filter(x -> x.hasRole(Authenticated.class) && !x.getUsername().equals("administrator")).collect(Collectors.toList());
		possibleParticipants.remove(entity.getCreator());
		participants = new ArrayList<UserAccount>();

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
