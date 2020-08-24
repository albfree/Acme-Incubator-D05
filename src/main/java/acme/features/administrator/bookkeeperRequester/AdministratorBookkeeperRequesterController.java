
package acme.features.administrator.bookkeeperRequester;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.roles.BookkeeperRequester;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Administrator;

@Controller
@RequestMapping("/administrator/bookkeeper-requester/")
public class AdministratorBookkeeperRequesterController extends AbstractController<Administrator, BookkeeperRequester> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorBookkeeperRequesterListService		listService;

	@Autowired
	private AdministratorBookkeeperRequesterShowService		showService;

	@Autowired
	private AdministratorBookkeeperRequesterDeleteService	deleteService;

	@Autowired
	private AdministratorBookkeeperRequesterCreateService	createService;


	// Constructors -----------------------------------------------------------

	@PostConstruct
	private void initialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
		super.addBasicCommand(BasicCommand.DELETE, this.deleteService);
		super.addBasicCommand(BasicCommand.CREATE, this.createService);
	}

}
