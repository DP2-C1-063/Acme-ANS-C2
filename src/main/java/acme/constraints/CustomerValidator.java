
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.realms.customer.Customer;
import acme.realms.customer.CustomerRepository;

public class CustomerValidator extends AbstractValidator<ValidCustomer, Customer> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private CustomerRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidCustomer annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Customer customer, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (customer == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {

			boolean uniqueCustomer;
			Customer existingCustomer;

			existingCustomer = this.repository.findCustomerByIdentifier(customer.getIdentifier());
			uniqueCustomer = existingCustomer == null || existingCustomer.equals(customer);

			super.state(context, uniqueCustomer, "identifier", "acme.validation.customer.duplicated-identifier.message");
		}

		result = !super.hasErrors(context);

		return result;
	}
}
