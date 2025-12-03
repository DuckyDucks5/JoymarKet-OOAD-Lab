package controller;

import model.Customer;
import repository.CustomerDA;

public class CustomerHandler {
	private CustomerDA da = new CustomerDA();

	public String topUpBalance(Customer customer, double amount) {
		if (amount < 10000) {
			return "Invalid Amount. Minimum top-up amount is 10,000!";
		}

		customer.setBalance(customer.getBalance() + amount);

		boolean saved = da.saveDA(customer);

		if (saved) {
			return "SUCCESS";
		} else {
			return "Failed to update balance!";
		}
	}

	public String editProfile(Customer customer, String fullName, String phone, String address) {

		StringBuilder sb = new StringBuilder();

		if (fullName.isEmpty()) {
			sb.append("Full Name must be filled!\n");
		}

		if (phone.isEmpty()) {
			sb.append("Phone must be filled!\n");
		} else {
			boolean numeric = true;
			for (char c : phone.toCharArray()) {
				if (!Character.isDigit(c)) {
					numeric = false;
					break;
				}
			}

			if (!numeric) {
				sb.append("Phone must be numeric!\n");
			}

			if (phone.length() < 10 || phone.length() > 13) {
				sb.append("Phone must be 10�13 digits!\n");
			}
		}

		if (address.isEmpty()) {
			sb.append("Address must be filled!\n");
		}

		if (sb.length() > 0)
			return sb.toString();

		customer.setFullname(fullName);
		customer.setPhone(phone);
		customer.setAddress(address);

		CustomerDA da = new CustomerDA();
		if (da.updateProfileDA(customer))
			return "SUCCESS";

		return "Failed to update profile!";
	}
	
	//kurangi balance apabila checkout product
	public String reduceBalance(Customer customer, double amount) {
		
		customer.setBalance(customer.getBalance() - amount);

		boolean saved = da.saveDA(customer);

		if (saved) {
			return "SUCCESS";
		} else {
			return "Failed to update balance!";
		}
	}
}