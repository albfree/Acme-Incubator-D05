
package acme.entities.records;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.framework.entities.DomainEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ToolRecord extends DomainEntity {

	// Serialization identifier --------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//Atributes ------------------------------------------------------------

	@NotBlank
	@Size(min = 1, max = 255)
	private String				title;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				activitySector;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				inventorsName;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				description;

	@NotBlank
	@URL
	@Size(min = 1, max = 255)
	private String				website;

	@NotBlank
	@Email
	private String				email;

	@NotBlank
	@Pattern(regexp = "^(open-source|closed-source)$")
	private String				indication;

	@Min(-5)
	@Max(5)
	private Integer				starsRate;

}
