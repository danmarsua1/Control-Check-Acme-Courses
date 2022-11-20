package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.entities.AbstractEntity;
import acme.roles.Teacher;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Course extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

		protected static final long	serialVersionUID	= 1L;

		// Attributes -------------------------------------------------------------

		@NotBlank
		@Column(unique = true)
		@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(:[A-Z]{1,10})?$") 
		protected String ticker;

		@NotBlank
		@Length(max = 76)
		protected String caption;

		@NotBlank
		@Length(max = 255)
		protected String abstractText;

		@URL
		protected String link;
		
		protected boolean publish;

		// Derived attributes -----------------------------------------------------

		// Relationships ----------------------------------------------------------
		
		@NotNull
		@Valid
		@ManyToOne(optional = false)
		protected Teacher teacher;

}
