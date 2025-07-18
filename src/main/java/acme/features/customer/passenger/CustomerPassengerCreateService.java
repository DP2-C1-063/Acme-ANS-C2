
package acme.features.customer.passenger;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.passenger.Passenger;
import acme.realms.customer.Customer;

@GuiService
public class CustomerPassengerCreateService extends AbstractGuiService<Customer, Passenger> {

	@Autowired
	private CustomerPassengerRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int customerId;
		customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		status = true;
		if (super.getRequest().getMethod().equals("POST")) {
			int id = super.getRequest().getData("id", int.class);
			status = id == 0;
		}
		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Customer customer = (Customer) super.getRequest().getPrincipal().getActiveRealm();
		Passenger passenger;

		passenger = new Passenger();
		passenger.setDraftMode(true);
		passenger.setCustomer(customer);

		super.getBuffer().addData(passenger);
	}

	@Override
	public void bind(final Passenger passenger) {
		super.bindObject(passenger, "name", "email", "passport", "birth", "needs");
	}

	@Override
	public void validate(final Passenger passenger) {

	}

	@Override
	public void perform(final Passenger passenger) {
		Customer customer = (Customer) super.getRequest().getPrincipal().getActiveRealm();
		passenger.setCustomer(customer);
		passenger.setDraftMode(true);
		this.repository.save(passenger);
	}

	@Override
	public void unbind(final Passenger passenger) {
		Dataset dataset;
		dataset = super.unbindObject(passenger, "name", "email", "passport", "birth", "needs", "draftMode");
		super.getResponse().addData(dataset);

	}
}
