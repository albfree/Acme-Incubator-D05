
package acme.features.anonymous.bulletins.horrilloBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.bulletins.HorrilloBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/horrillo-bulletin/")
public class AnonymousHorrilloBulletinController extends AbstractController<Anonymous, HorrilloBulletin> {

	// Internal state -----------------------------------------------------------------------

	@Autowired
	private AnonymousHorrilloBulletinListService	listService;

	@Autowired
	private AnonymousHorrilloBulletinCreateService	createService;


	// Constructors -------------------------------------------------------------------------

	@PostConstruct
	private void inicialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
