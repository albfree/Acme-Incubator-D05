
package acme.features.anonymous.bulletins.perezBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.bulletins.PerezBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/perez-bulletin/")
public class AnonymousPerezBulletinController extends AbstractController<Anonymous, PerezBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnonymousPerezBulletinListService	listService;

	@Autowired
	private AnonymousPerezBulletinCreateService	createService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
