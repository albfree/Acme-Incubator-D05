
package acme.features.anonymous.bulletins.horrilloBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletins.HorrilloBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousHorrilloBulletinCreateService implements AbstractCreateService<Anonymous, HorrilloBulletin> {

	// Internal state --------------------------------------------------------------

	@Autowired
	AnonymousAnonymousHorrilloBulletinRepository repository;


	@Override
	public boolean authorise(final Request<HorrilloBulletin> request) {
		assert request != null;
		return true;
	}

	@Override
	public HorrilloBulletin instantiate(final Request<HorrilloBulletin> request) {
		assert request != null;

		HorrilloBulletin res;
		Date time;

		time = new Date(System.currentTimeMillis() - 1);

		res = new HorrilloBulletin();
		res.setAuthor("Peter Quill");
		res.setBody("I'm Starlord");
		res.setTime(time);

		return res;
	}

	@Override
	public void validate(final Request<HorrilloBulletin> request, final HorrilloBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<HorrilloBulletin> request, final HorrilloBulletin entity) {
		assert request != null;
		assert entity != null;

		Date time;

		time = new Date(System.currentTimeMillis() - 1);
		entity.setTime(time);
		this.repository.save(entity);

	}

	@Override
	public void unbind(final Request<HorrilloBulletin> request, final HorrilloBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "author", "body");
	}

	@Override
	public void bind(final Request<HorrilloBulletin> request, final HorrilloBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

}
