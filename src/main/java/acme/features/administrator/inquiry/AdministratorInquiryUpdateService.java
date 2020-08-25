
package acme.features.administrator.inquiry;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.inquiries.Inquiry;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorInquiryUpdateService implements AbstractUpdateService<Administrator, Inquiry> {

	@Autowired
	private AdministratorInquiryRepository repository;


	@Override
	public boolean authorise(final Request<Inquiry> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Inquiry> request, final Inquiry entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Inquiry> request, final Inquiry entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation", "deadline", "description", "minMoney", "maxMoney", "email");
	}

	@Override
	public Inquiry findOne(final Request<Inquiry> request) {
		assert request != null;

		Inquiry result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Inquiry> request, final Inquiry entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		if (!errors.hasErrors("deadline")) {
			errors.state(request, entity.getDeadline().after(new Date()), "deadline", "administrator.inquiry.error.deadline");
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMinMoney().getAmount() < entity.getMaxMoney().getAmount(), "minMoney", "administrator.inquiry.error.maxMoneyLowerThanMin");
		}
	}

	@Override
	public void update(final Request<Inquiry> request, final Inquiry entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreation(moment);
		this.repository.save(entity);
	}

}
