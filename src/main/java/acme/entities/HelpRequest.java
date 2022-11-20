package acme.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.framework.datatypes.Money;
import acme.framework.entities.AbstractEntity;
import acme.roles.Teacher;
import acme.roles.Learner;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class HelpRequest extends AbstractEntity {
	
	// Serialisation identifier -----------------------------------------------
	
	protected static final long serialVersionUID = 1L;
	
	// Attributes -------------------------------------------------------------
	
	@NotNull
	protected StatusHelpRequest status;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{3}-[0-9]{3}(:[A-Z]{1,10})?$")
	protected String ticker;

	@NotBlank
	@Length(max = 255)
	protected String statement;

	@NotNull
	@Valid
	protected Money budget; 
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date creationMoment;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date initDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date finishDate;
	
	@URL
	protected String link;
	
	protected boolean publish;
	
	// Relationships ----------------------------------------------------------
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	protected Learner learner;
	
	@Valid
	@ManyToOne(optional=false)
	protected Teacher teacher;
}