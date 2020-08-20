
package acme.features.anonymous.bulletins.guerreroBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletins.GuerreroBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousGuerreroBulletinCreateService implements AbstractCreateService<Anonymous, GuerreroBulletin> {

	@Autowired
	private AnonymousGuerreroBulletinRepository repository;


	@Override
	public boolean authorise(final Request<GuerreroBulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public GuerreroBulletin instantiate(final Request<GuerreroBulletin> request) {
		assert request != null;

		GuerreroBulletin result = new GuerreroBulletin();
		Date moment = new Date(System.currentTimeMillis() - 1);

		result.setName("");
		result.setQuote("");
		result.setMoment(moment);

		return result;
	}

	@Override
	public void unbind(final Request<GuerreroBulletin> request, final GuerreroBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "name", "quote");
	}

	@Override
	public void bind(final Request<GuerreroBulletin> request, final GuerreroBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void validate(final Request<GuerreroBulletin> request, final GuerreroBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<GuerreroBulletin> request, final GuerreroBulletin entity) {
		assert request != null;
		assert entity != null;

		Date moment = new Date(System.currentTimeMillis() - 1);

		entity.setMoment(moment);
		this.repository.save(entity);
	}
}
