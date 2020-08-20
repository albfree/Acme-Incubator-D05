
package acme.entities.challenges;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import acme.framework.datatypes.Money;
import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Challenge extends DomainEntity {

	//Serialisation identifier ----------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Attributes ------------------------------------------------------

	@NotBlank
	@Size(min = 1, max = 255)
	private String				title;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				deadline;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				description;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				rookieGoal;

	@NotNull
	@Valid
	private Money				rookieReward;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				averageGoal;

	@NotNull
	@Valid
	private Money				averageReward;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				expertGoal;

	@NotNull
	@Valid
	private Money				expertReward;

	//Derived attributes -----------------------------------------------

	//Relationships ----------------------------------------------------

}
