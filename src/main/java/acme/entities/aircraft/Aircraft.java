
package acme.entities.aircraft;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidAircraft;
import acme.entities.airlines.Airlines;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@ValidAircraft
public class Aircraft extends AbstractEntity {

	private static final long	serialVersionUID	= 1L;
	@Mandatory
	@Automapped
	@ValidString(min = 1, max = 50)
	private String				model;
	@Mandatory
	@ValidString(min = 1, max = 50)
	@Column(unique = true)
	private String				registrationNumber;
	@Mandatory
	@ValidNumber(min = 1, max = 255)
	@Automapped
	private Integer				capacity;
	@Mandatory
	@ValidNumber(min = 20000, max = 50000)
	@Automapped
	private Integer				cargoWeight;
	@Mandatory
	@Valid
	@Automapped
	private AircraftStatus		status;
	@Optional
	@Automapped
	@ValidString(min = 0, max = 255)
	private String				details;
	@Mandatory
	@Valid
	@ManyToOne
	private Airlines			airline;
}
