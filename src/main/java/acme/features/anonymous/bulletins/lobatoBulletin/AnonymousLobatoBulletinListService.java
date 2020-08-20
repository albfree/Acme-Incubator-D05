
package acme.features.anonymous.bulletins.lobatoBulletin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.bulletins.LobatoBulletin;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Anonymous;
import acme.framework.services.AbstractListService;

@Service
public class AnonymousLobatoBulletinListService implements AbstractListService<Anonymous, LobatoBulletin> {

	@Autowired
	private AnonymousLobatoBulletinRepository repository;


	@Override
	public boolean authorise(final Request<LobatoBulletin> request) {
		assert request != null;

		return true;
	}

	@Override
	public void unbind(final Request<LobatoBulletin> request, final LobatoBulletin entity, final Model model) {

		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "moment", "scientist", "investigation");
	}

	@Override
	public Collection<LobatoBulletin> findMany(final Request<LobatoBulletin> request) {
		assert request != null;

		//Another option for this findMany is findAll
		return this.repository.findMany();
		//return null;
	}

}
