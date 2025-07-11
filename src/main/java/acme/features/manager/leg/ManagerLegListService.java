
package acme.features.manager.leg;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flight.Flight;
import acme.entities.leg.Leg;
import acme.realms.manager.Manager;

@GuiService
public class ManagerLegListService extends AbstractGuiService<Manager, Leg> {
	// Internal state

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface


	@Override
	public void authorise() {
		boolean status;
		int masterId;
		Flight flight;
		Manager manager;

		masterId = super.getRequest().getData("masterId", int.class);
		flight = this.repository.findFlightById(masterId);
		manager = flight == null ? null : flight.getManager();
		status = flight != null && super.getRequest().getPrincipal().hasRealm(manager);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		List<Leg> legs;
		int masterId;
		boolean flightPublished;

		masterId = super.getRequest().getData("masterId", int.class);
		flightPublished = this.repository.findFlightById(masterId).isPublished();

		legs = this.repository.findAllLegsByMasterId(masterId);
		super.getResponse().addGlobal("masterId", masterId);
		super.getResponse().addGlobal("flightPublished", flightPublished);
		super.getBuffer().addData(legs);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "departureAirport", "arrivalAirport");
		super.addPayload(dataset, leg, "status", "duration", "aircraft", "flight", "number");
		super.getResponse().addGlobal("flightPublished", leg.getFlight().isPublished());

		dataset.put("masterId", masterId);
		super.getResponse().addGlobal("masterId", masterId);

		super.getResponse().addData(dataset);
	}

}
