
package acme.entities.activities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import acme.entities.investmentRounds.InvestmentRound;
import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Activity extends DomainEntity {

	//Serialisation identifier ----------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes ------------------------------------------------------

	@NotBlank
	private String				title;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				startDateTime;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				endDateTime;

	@NotNull
	@Valid
	private Money				budget;

	//Derived attributes -----------------------------------------------

	//Relationships ----------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private InvestmentRound		investment;

}
