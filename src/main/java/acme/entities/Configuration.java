package acme.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.framework.entities.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Configuration extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long serialVersionUID = 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^$|(^[^\\,]+([\\,][^\\,]+)*$)")
	protected String acceptedCurrencies;

	@NotBlank
	protected String currency;

	@NotBlank
	@Column(length = 1000)
	private String spamRecords;

	@NotNull
	@Range(min = 0, max = 1)
	private Double spamThreshold;

	@NotNull
	@Min(1)
	private Double spamBooster;

	// Derived attributes -----------------------------------------------------

	public boolean isAcceptedCurrency(final String currency) {
		final String upperCaseCurrency = currency.toUpperCase();
		boolean accepted = false;

		// Manage likely currencies
		for (final String acceptedCurrency : this.acceptedCurrencies.toUpperCase().split(",")) {
			if (upperCaseCurrency.equals(acceptedCurrency)) {
				accepted = true;
				break;
			}
		}

		return accepted;
	}

}
