
package acme.features.entrepreneur.investmentRound;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizations.Customization;
import acme.entities.forums.Forum;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractUpdateService;

@Service
public class EntrepreneurInvestmentRoundUpdateService implements AbstractUpdateService<Entrepreneur, InvestmentRound> {

	@Autowired
	private EntrepreneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		boolean result;
		int investmentID;
		InvestmentRound investment;
		Entrepreneur entrepreneur;
		Principal principal;

		investmentID = request.getModel().getInteger("id");
		investment = this.repository.findOneInvestmentRoundById(investmentID);
		entrepreneur = investment.getEntrepreneur();
		principal = request.getPrincipal();

		result = entrepreneur.getUserAccount().getId() == principal.getAccountId() && !investment.sumActivitiesBudgets();

		return result;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationDate", "ticker", "workProgramme", "entrepreneur");
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "creationDate", "kindOfRound", "title", "description", "amount", "optionalLink");
	}

	@Override
	public InvestmentRound findOne(final Request<InvestmentRound> request) {
		assert request != null;

		InvestmentRound result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneInvestmentRoundById(id);

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("title")) {
			errors.state(request, !this.isSpamText(entity.getTitle()), "title", "entrepreneur.investment-round.error.spam");
		}

		if (!errors.hasErrors("description")) {
			errors.state(request, !this.isSpamText(entity.getDescription()), "description", "entrepreneur.investment-round.error.spam");
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
	public void update(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		if (entity.sumActivitiesBudgets()) {
			Forum forum = new Forum();
			String title;

			if (request.getLocale().getLanguage().equals("en")) {
				title = "Investment Round Forum: " + entity.getTicker();
			} else {
				title = "Foro del Investment Round: " + entity.getTicker();
			}

			forum.setTitle(title);
			forum.setInvestment(entity);
			this.repository.save(forum);
		}

		this.repository.save(entity);
	}

}
