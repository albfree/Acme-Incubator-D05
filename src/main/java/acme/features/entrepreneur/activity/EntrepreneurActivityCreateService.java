
package acme.features.entrepreneur.activity;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.activities.Activity;
import acme.entities.customizations.Customization;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurActivityCreateService implements AbstractCreateService<Entrepreneur, Activity> {

	@Autowired
	private EntrepreneurActivityRepository repository;


	@Override
	public boolean authorise(final Request<Activity> request) {
		assert request != null;

		boolean result;
		int investmentID;
		InvestmentRound investment;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentID = request.getModel().getInteger("invId");
		investment = this.repository.findOneInvestmentRoundById(investmentID);
		entrepreneur = investment.getEntrepreneur();
		principal = request.getPrincipal();

		result = entrepreneur.getUserAccount().getId() == principal.getAccountId() && !investment.sumActivitiesBudgets();

		return result;
	}

	@Override
	public void bind(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Activity> request, final Activity entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "startDateTime", "endDateTime", "budget");

		int investmentID;

		investmentID = request.getModel().getInteger("invId");
		model.setAttribute("ivID", investmentID);
	}

	@Override
	public Activity instantiate(final Request<Activity> request) {
		assert request != null;

		Activity result = new Activity();
		int investmentID;
		InvestmentRound investment;

		investmentID = request.getModel().getInteger("invId");
		investment = this.repository.findOneInvestmentRoundById(investmentID);
		result.setInvestment(investment);

		return result;
	}

	@Override
	public void validate(final Request<Activity> request, final Activity entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("startDateTime") && !errors.hasErrors("endDateTime")) {
			errors.state(request, entity.getEndDateTime().after(entity.getStartDateTime()), "startDateTime", "entrepreneur.activity.error.start-date.before");
			errors.state(request, entity.getStartDateTime().after(new Date()), "startDateTime", "entrepreneur.activity.error.date.invalid");
			errors.state(request, entity.getEndDateTime().after(new Date()), "endDateTime", "entrepreneur.activity.error.date.invalid");
		}

		if (!errors.hasErrors("title")) {
			errors.state(request, !this.isSpamText(entity.getTitle()), "title", "entrepreneur.activity.error.spam");
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
	public void create(final Request<Activity> request, final Activity entity) {
		assert request != null;
		assert entity != null;

		Collection<Activity> activities = entity.getInvestment().getWorkProgramme();

		activities.add(entity);

		Double sum = 0.;

		for (Activity a : activities) {
			sum += a.getBudget().getAmount();
		}

		if (sum.equals(entity.getInvestment().getAmount().getAmount())) {
			Forum forum = new Forum();
			String title;

			if (request.getLocale().getLanguage().equals("en")) {
				title = "Investment Round Forum: " + entity.getInvestment().getTicker();
			} else {
				title = "Foro del Investment Round: " + entity.getInvestment().getTicker();
			}

			forum.setTitle(title);
			forum.setInvestment(entity.getInvestment());
			this.repository.save(forum);
		}

		this.repository.save(entity);
	}

}
