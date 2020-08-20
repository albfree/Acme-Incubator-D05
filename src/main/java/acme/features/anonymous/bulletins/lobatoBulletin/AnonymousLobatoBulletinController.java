
package acme.features.anonymous.bulletins.lobatoBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.bulletins.LobatoBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/lobato-bulletin/")
public class AnonymousLobatoBulletinController extends AbstractController<Anonymous, LobatoBulletin> {

	@Autowired
	private AnonymousLobatoBulletinListService		listService;

	@Autowired
	private AnonymousLobatoBulletinCreateService	createService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}
}
