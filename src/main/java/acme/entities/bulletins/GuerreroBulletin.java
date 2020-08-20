
package acme.entities.bulletins;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GuerreroBulletin extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	// Atributos de la entidad

	@NotBlank
	@Size(min = 1, max = 255)
	private String				name;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				quote;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

}
