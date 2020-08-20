
package acme.entities.forums;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.entities.DomainEntity;
import acme.framework.entities.UserAccount;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Forum extends DomainEntity {

	private static final long				serialVersionUID	= 1L;

	@NotBlank
	@Size(min = 1, max = 255)
	private String							title;

	@Valid
	@OneToOne(optional = false)
	private InvestmentRound					investment;

	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToMany
	private Collection<@Valid UserAccount>	participants;

}
