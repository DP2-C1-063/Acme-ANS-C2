
package acme.features.customer.passenger;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.passenger.Passenger;
import acme.realms.customer.Customer;

@GuiService
public class CustomerPassengerShowService extends AbstractGuiService<Customer, Passenger> {

	@Autowired
	private CustomerPassengerRepository repository;


	@Override
	public void authorise() {
		int customerId = super.getRequest().getPrincipal().getActiveRealm().getId();
		if (!super.getRequest().getData().containsKey("id"))
			super.getResponse().setAuthorised(false);
		else {
			boolean status = super.getRequest().getPrincipal().hasRealmOfType(Customer.class);

			int passengerId = super.getRequest().getData("id", int.class);
			Passenger passenger = this.repository.getPassengerById(passengerId);
			status = customerId == passenger.getCustomer().getId();
			super.getResponse().setAuthorised(status);
		}
	}

	@Override
	public void load() {
		Passenger passenger;
		int id = super.getRequest().getData("id", int.class);

		passenger = this.repository.getPassengerById(id);
		super.getBuffer().addData(passenger);
	}

	@Override
	public void unbind(final Passenger passenger) {
		Dataset dataset;

		dataset = super.unbindObject(passenger, "name", "email", "passport", "birth", "needs", "draftMode");
		super.getResponse().addData(dataset);
	}

}
