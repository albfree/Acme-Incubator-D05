
package acme.features.anonymous.bulletins.escobarBulletin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.bulletins.EscobarBulletin;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/escobar-bulletin/")
public class AnonymousEscobarBulletinController extends AbstractController<Anonymous, EscobarBulletin> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnonymousEscobarBulletinListService		listService;
	@Autowired
	private AnonymousEscobarBulletinCreateService	createService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
