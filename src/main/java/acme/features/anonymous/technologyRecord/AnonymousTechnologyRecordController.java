
package acme.features.anonymous.technologyRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import acme.entities.records.TechnologyRecord;
import acme.framework.components.BasicCommand;
import acme.framework.controllers.AbstractController;
import acme.framework.entities.Anonymous;

@Controller
@RequestMapping("/anonymous/technology-record/")
public class AnonymousTechnologyRecordController extends AbstractController<Anonymous, TechnologyRecord> {

	// Internal state -----------------------------------------------------------------------

	@Autowired
	private AnonymousTechnologyRecordListService	listService;

	@Autowired
	private AnonymousTechnologyRecordShowService	showService;


	// Constructors -------------------------------------------------------------------------

	@PostConstruct
	private void inicialise() {
		super.addBasicCommand(BasicCommand.LIST, this.listService);
		super.addBasicCommand(BasicCommand.SHOW, this.showService);
	}

}
