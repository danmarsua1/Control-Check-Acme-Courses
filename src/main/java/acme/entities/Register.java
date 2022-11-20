package acme.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Register extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@Min(1)
	@Positive
	private Integer learningTime;
	
	@NotBlank
	private String unity;
	
	// Derived attributes -----------------------------------------------------

	// Relationships ----------------------------------------------------------
	
	@NotNull
	@Valid
	@ManyToOne(optional=false)
	protected Course course;
	
	@Valid
	@OneToOne(optional=true)
	protected TheoryTutorial theoryTutorial;
	
	@Valid
	@OneToOne(optional=true)
	protected LabTutorial labTutorial;

}
