
package acme.entities.notices;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Notice extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	// Atributos de la entidad

	@URL
	@NotBlank
	@Size(min = 1, max = 255)
	private String				headerPicture;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				title;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				creationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date				deadline;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				body;

	@URL
	@Size(max = 255)
	private String				firstOptionalLink;

	@URL
	@Size(max = 255)
	private String				secondOptionalLink;

}
