package controller;

import model.Customer;
import repository.CustomerDA;

public class CustomerHandler {

	// Data Access Object Untuk Operasi Database Customer
	private CustomerDA da = new CustomerDA();

	// Top Up Balance Customer
	public String topUpBalance(Customer customer, double amount) {

		// Validasi Minimal Top-Up
		if (amount < 10000) {
			return "Invalid Amount. Minimum top-up amount is 10,000!";
		}

		// Update Balance Customer
		customer.setBalance(customer.getBalance() + amount);

		// Menyimpan Perubahan Ke Database
		boolean saved = da.saveDA(customer);

		// Mengembalikan Hasil
		if (saved) {
			return "SUCCESS";
		} else {
			return "Failed to update balance!";
		}
	}
	
	// Mengurangi Balance Saat Checkout Produk
	public String reduceBalance(Customer customer, double amount) {
		
		// Mengupdate Balance Customer
		customer.setBalance(customer.getBalance() - amount);

		// Menyimpan Perubahan Ke Database
		boolean saved = da.saveDA(customer);

		// Mengembalikan Hasil
		if (saved) {
			return "SUCCESS";
		} else {
			return "Failed to update balance!";
		}
	}
}
