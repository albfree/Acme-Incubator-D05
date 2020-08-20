
package acme.features.anonymous.bulletins.guerreroBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.bulletins.GuerreroBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/guerrero-bulletin/")
public class AnonymousGuerreroBulletinController extends AbstractController<Anonymous, GuerreroBulletin> {

	@Autowired
	private AnonymousGuerreroBulletinListService	listService;

	@Autowired
	private AnonymousGuerreroBulletinCreateService	createService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}
}
