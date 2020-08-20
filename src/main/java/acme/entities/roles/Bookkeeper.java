
package acme.entities.roles;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import acme.framework.entities.UserRole;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Bookkeeper extends UserRole {

	private static final long	serialVersionUID	= 1L;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				firmName;

	@NotBlank
	@Size(min = 1, max = 255)
	private String				responsibilityStatement;

}
