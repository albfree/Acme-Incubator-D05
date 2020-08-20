
package acme.entities.bulletins;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class EscobarBulletin extends DomainEntity {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				author;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				text;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				moment;

	@URL
	@NotBlank
	@Size(min = 1, max = 255)
	private String				link;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				country;

}
