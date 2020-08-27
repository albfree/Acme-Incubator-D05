
package acme.features.administrator.overture;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.overtures.Overture;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractUpdateService;

@Service
public class AdministratorOvertureUpdateService implements AbstractUpdateService<Administrator, Overture> {

	@Autowired
	private AdministratorOvertureRepository repository;


	@Override
	public boolean authorise(final Request<Overture> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creation");
	}

	@Override
	public void unbind(final Request<Overture> request, final Overture entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "creation", "deadline", "description", "minMoney", "maxMoney", "email");
	}

	@Override
	public Overture findOne(final Request<Overture> request) {
		assert request != null;

		Overture result;
		int id;

		id = request.getModel().getInteger("id");
		result = this.repository.findOneById(id);

		return result;
	}

	@Override
	public void validate(final Request<Overture> request, final Overture entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		/*
		 * Validamos que la fecha de vencimiento no sea anterior a la fecha actual si es distinto
		 * de null, puesto que la validación del nulo ya la hace el framework por defecto por el
		 * NotNull en la propiedad de la entidad
		 */

		if (!errors.hasErrors("deadline")) {
			errors.state(request, entity.getDeadline().after(new Date()), "deadline", "administrator.overture.error.deadline");
		}

		if (!errors.hasErrors("minMoney") && !errors.hasErrors("maxMoney")) {
			errors.state(request, entity.getMinMoney().getAmount() < entity.getMaxMoney().getAmount(), "minMoney", "administrator.overture.error.maxMoneyLowerThanMin");
		}
	}

	@Override
	public void update(final Request<Overture> request, final Overture entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreation(moment);
		this.repository.save(entity);
	}

}
