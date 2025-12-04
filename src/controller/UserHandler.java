package controller;

import repository.UserDA;
import model.Customer;
import model.User;

public class UserHandler {

	private UserDA userRepo = new UserDA();

	//Register and Validation
	public String registerAccount(String fullname, String email, String password, String confirm, String phone, String address){
		StringBuilder sb = new StringBuilder();

		if (fullname.isEmpty())
			sb.append("- Full Name cannot be empty.\n");

		if (email.isEmpty()) {
			sb.append("- Email must be filled.\n");
		} else if (!email.endsWith("@gmail.com")) {
			sb.append("- Email must end with @gmail.com.\n");
		}

		if (password.length() < 6) {
			sb.append("- Password must be at least 6 characters.\n");
		}
		if (!password.equals(confirm)) {
			sb.append("- Confirm password must match Password.\n");
		}

		if (phone.isEmpty()) {
			sb.append("- Phone number must be filled.\n");
		} else {
			if (!phone.matches("\\d+")) {
				sb.append("- Phone number must be numeric.\n");
			}
			if (phone.length() < 10 || phone.length() > 13) {
				sb.append("- Phone number must be 10�13 digits.\n");
			}
		}

		if (address.isEmpty()) {
			sb.append("- Address must be filled.\n");
		}

		if (sb.length() > 0) {
			return sb.toString();
		}

		//Insert Customer
		try {
			String newId = userRepo.generateCustomerID();
			Customer customer = new Customer(newId, fullname, email, password, phone, address, 0);

			Customer saved = userRepo.insertCustomer(customer);
			if (saved != null) {
			    return "SUCCESS";
			} else {
			    return "Error while creating account.";
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Error: " + e.getMessage();
		}
	}

	//Login
	public User login(String email, String password) {
		if (email.isBlank() || password.isBlank())
			return null;

		return userRepo.findByEmailAndPassword(email, password);
	}

	//Update Balance
	public boolean updateBalance(String idUser, double balance) {
		return userRepo.updateBalance(idUser, balance);
	}
}
