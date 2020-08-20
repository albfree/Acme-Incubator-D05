
package acme.features.anonymous.bulletins.lobatoBulletin;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletins.LobatoBulletin;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractCreateService;

@Service
public class AnonymousLobatoBulletinCreateService implements AbstractCreateService<Anonymous, LobatoBulletin> {

	@Autowired
	private AnonymousLobatoBulletinRepository repository;


	@Override
	public boolean authorise(final Request<LobatoBulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public void bind(final Request<LobatoBulletin> request, final LobatoBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<LobatoBulletin> request, final LobatoBulletin entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "scientist", "investigation");
	}

	@Override
	public LobatoBulletin instantiate(final Request<LobatoBulletin> request) {
		assert request != null;
		LobatoBulletin bulletin = new LobatoBulletin();
		bulletin.setScientist("");
		bulletin.setInvestigation("");
		bulletin.setMoment(new Date(System.currentTimeMillis() - 1));

		return bulletin;
	}

	@Override
	public void validate(final Request<LobatoBulletin> request, final LobatoBulletin entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

	}

	@Override
	public void create(final Request<LobatoBulletin> request, final LobatoBulletin entity) {
		assert request != null;
		assert entity != null;

		entity.setMoment(new Date(System.currentTimeMillis() - 1));

		this.repository.save(entity);
	}

}
