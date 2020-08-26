
package acme.features.authenticated.forum;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.forums.Forum;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Authenticated;

@Controller
@RequestMapping("/authenticated/forum/")
public class AuthenticatedForumController extends AbstractController<Authenticated, Forum> {

	@Autowired
	private AuthenticatedForumListService	listService;

	@Autowired
	private AuthenticatedForumShowService	showService;

	@Autowired
	private AuthenticatedForumCreateService	createService;

	@Autowired
	private AuthenticatedForumUpdateService	updateService;

	@Autowired
	private AuthenticatedForumDeleteService	deleteService;


	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
		super.addBasicCommand(BasicCommand.UPDATE, this.updateService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
	}

}
