
package acme.features.entrepreneur.investmentRound;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.customizations.Customization;
import acme.entities.investmentRounds.InvestmentRound;
import acme.entities.roles.Entrepreneur;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Principal;
import acme.framework.services.AbstractCreateService;

@Service
public class EntrepreneurInvestmentRoundCreateService implements AbstractCreateService<Entrepreneur, InvestmentRound> {

	@Autowired
	private EntrepreneurInvestmentRoundRepository repository;


	@Override
	public boolean authorise(final Request<InvestmentRound> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationDate");
	}

	@Override
	public void unbind(final Request<InvestmentRound> request, final InvestmentRound entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "ticker", "kindOfRound", "title", "description", "amount", "optionalLink");

	}

	@Override
	public InvestmentRound instantiate(final Request<InvestmentRound> request) {
		assert request != null;

		Principal principal = request.getPrincipal();
		InvestmentRound result = new InvestmentRound();

		Date moment = new Date(System.currentTimeMillis() - 1);
		Entrepreneur entrepreneur = this.repository.findOneEntrepreneurByAccountId(principal.getAccountId());

		result.setCreationDate(moment);
		result.setEntrepreneur(entrepreneur);

		return result;
	}

	@Override
	public void validate(final Request<InvestmentRound> request, final InvestmentRound entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean tickerIsCorrect = true;

		if (!errors.hasErrors("ticker")) {
			String[] ticker = entity.getTicker().split("-");
			String entrepreneurSector;

			if (entity.getEntrepreneur().getSector().length() >= 3) {
				entrepreneurSector = entity.getEntrepreneur().getSector().substring(0, 3).toUpperCase();
			} else {
				entrepreneurSector = entity.getEntrepreneur().getSector().toUpperCase();
			}

			if (entrepreneurSector.length() == 3 && !entrepreneurSector.equals(ticker[0]) || entrepreneurSector.length() == 2 && !ticker[0].equals(entrepreneurSector + "X")
				|| entrepreneurSector.length() == 1 && !ticker[0].equals(entrepreneurSector + "XX")) {
				tickerIsCorrect = false;
				errors.state(request, false, "ticker", "entrepreneur.investment-round.error.ticker.sector", entity.getEntrepreneur().getSector());
			}

			DateFormat dateFormat = new SimpleDateFormat("yy");
			String yearDigits = dateFormat.format(Calendar.getInstance().getTime());

			if (!ticker[1].equals(yearDigits)) {
				tickerIsCorrect = false;
				errors.state(request, false, "ticker", "entrepreneur.investment-round.error.ticker.year");
			}
		}

		if (tickerIsCorrect && this.repository.findOneInvestmentRoundByTicker(entity.getTicker()) != null) {
			errors.state(request, false, "ticker", "entrepreneur.investment-round.error.ticker.exists");
		}

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
	public void create(final Request<InvestmentRound> request, final InvestmentRound entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreationDate(moment);
		this.repository.save(entity);
	}

}
