
package acme.features.administrator.notice;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.notices.Notice;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Administrator;
import acme.framework.services.AbstractCreateService;

@Service
public class AdministratorNoticeCreateService implements AbstractCreateService<Administrator, Notice> {

	@Autowired
	private AdministratorNoticeRepository repository;


	@Override
	public boolean authorise(final Request<Notice> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors, "creationDate");
	}

	@Override
	public void unbind(final Request<Notice> request, final Notice entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "headerPicture", "title", "deadline", "body", "firstOptionalLink", "secondOptionalLink");
		model.setAttribute("accept", "false");
	}

	@Override
	public Notice instantiate(final Request<Notice> request) {
		assert request != null;

		Notice result = new Notice();

		return result;
	}

	@Override
	public void validate(final Request<Notice> request, final Notice entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		boolean isAccepted = request.getModel().getBoolean("accept");

		// Validamos que el check de confirmación esté marcado

		errors.state(request, isAccepted, "accept", "administrator.notice.error.must-accept");

		/*
		 * Validamos que la fecha de vencimiento no sea anterior a la fecha actual si es distinto
		 * de null, puesto que la validación del nulo ya la hace el framework por defecto por el
		 * NotNull en la propiedad de la entidad
		 */

		if (!errors.hasErrors("deadline")) {
			errors.state(request, entity.getDeadline().after(new Date()), "deadline", "administrator.notice.error.deadline");
		}
	}

	@Override
	public void create(final Request<Notice> request, final Notice entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setCreationDate(moment);
		this.repository.save(entity);
	}

}
