
package acme.features.authenticated.message;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizations.Customization;
import acme.entities.forums.Forum;
import acme.entities.messages.Message;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Authenticated;
import acme.framework.entities.Principal;
import acme.framework.entities.UserAccount;
import acme.framework.services.AbstractCreateService;

@Service
public class AuthenticatedMessageCreateService implements AbstractCreateService<Authenticated, Message> {

	@Autowired
	AuthenticatedMessageRepository repository;


	@Override
	public boolean authorise(final Request<Message> request) {
		assert request != null;

		Principal principal;
		int currentUserId;
		UserAccount currentUserAccount;
		int forumId;
		Forum forum;
		boolean isCreator;
		boolean isParticipant;

		principal = request.getPrincipal();
		currentUserId = principal.getAccountId();
		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		forumId = request.getModel().getInteger("forumId");
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
	public void bind(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationMoment", "forum", "user");

	}

	@Override
	public void unbind(final Request<Message> request, final Message entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "tags", "body");

		model.setAttribute("confirmation", false);
		model.setAttribute("forumId", request.getModel().getAttribute("forumId"));
	}

	@Override
	public Message instantiate(final Request<Message> request) {
		assert request != null;

		Message result;
		int currentUserId;
		UserAccount currentUserAccount;
		int forumId;
		Forum forum;

		currentUserId = request.getPrincipal().getAccountId();
		forumId = request.getModel().getInteger("forumId");

		currentUserAccount = this.repository.findOneUserAccountById(currentUserId);
		forum = this.repository.findOneForumById(forumId);
		result = new Message();

		result.setUser(currentUserAccount);
		result.setForum(forum);

		return result;
	}

	@Override
	public void validate(final Request<Message> request, final Message entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean confirmation = request.getModel().getBoolean("confirmation");

		errors.state(request, confirmation, "confirmation", "acme.validation.message.confirmation");

		if (!errors.hasErrors("title")) {
			errors.state(request, !this.isSpamText(entity.getTitle()), "title", "authenticated.message.error.spam");
		}

		if (!errors.hasErrors("tags")) {
			errors.state(request, !this.isSpamText(entity.getTags()), "tags", "authenticated.message.error.spam");
		}

		if (!errors.hasErrors("body")) {
			errors.state(request, !this.isSpamText(entity.getBody()), "body", "authenticated.message.error.spam");
		}

	}

	private boolean isSpamText(final String textToCheck) {
		boolean result = false;
		Double numSpWordsInText = 0.;
		Integer numOfWords = textToCheck.split(" ").length;
		List<Customization> customization = this.repository.findCustomizations();

		String spamWords = customization.get(0).getSpamWords();

		String[] spamWordsArray = spamWords.split(";");

		List<String> spamWordsList = Arrays.asList(spamWordsArray);

		for (String sw : spamWordsList) {

			numSpWordsInText = numSpWordsInText + this.timesAppearSpamWord(textToCheck.toLowerCase(), sw.toLowerCase(), 0.);

			Double percentage = numSpWordsInText / numOfWords * 100;

			if (percentage > customization.get(0).getThreshold()) {
				result = true;
				break;
			}

		}

		return result;
	}

	private double timesAppearSpamWord(final String textToCheck, final String spamWord, Double numSpWord) {

		if (textToCheck.contains(spamWord)) {
			Integer index = textToCheck.indexOf(spamWord) + spamWord.length();
			numSpWord += 1;
			return this.timesAppearSpamWord(textToCheck.substring(index).trim(), spamWord, numSpWord);
		}

		return numSpWord;
	}

	@Override
	public void create(final Request<Message> request, final Message entity) {
		assert request != null;
		assert entity != null;

		Date moment;
		moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreationMoment(moment);

		this.repository.save(entity);
	}

}
