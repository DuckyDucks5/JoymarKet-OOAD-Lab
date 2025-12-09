package controller;

import repository.CustomerDA;
import repository.UserDA;
import model.Customer;
import model.User;

public class UserHandler {

	// Data Access Object Untuk Mengelola Tabel User Dan Customer
	private UserDA userRepo = new UserDA();

	// Register Dan Validasi Input User
	public String registerAccount(String fullname, String email, String password, String confirm, String phone, String address){
		StringBuilder sb = new StringBuilder();

		// Validasi Full Name
		if (fullname.isEmpty())
			sb.append("- Full Name cannot be empty.\n");

		// Validasi Email
		if (email.isEmpty()) {
			sb.append("- Email must be filled.\n");
		} else if (!email.endsWith("@gmail.com")) {
			sb.append("- Email must end with @gmail.com.\n");
		}

		// Validasi Password
		if (password.length() < 6) {
			sb.append("- Password must be at least 6 characters.\n");
		}
		if (!password.equals(confirm)) {
			sb.append("- Confirm password must match Password.\n");
		}

		// Validasi Nomor Telepon
		if (phone.isEmpty()) {
			sb.append("- Phone number must be filled.\n");
		} else {
			if (!phone.matches("\\d+")) {
				sb.append("- Phone number must be numeric.\n");
			}
			if (phone.length() < 10 || phone.length() > 13) {
				sb.append("- Phone number must be 10–13 digits.\n");
			}
		}

		// Validasi Alamat
		if (address.isEmpty()) {
			sb.append("- Address must be filled.\n");
		}

		// Jika Ada Error, Return Semua Pesan Error
		if (sb.length() > 0) {
			return sb.toString();
		}

		// Proses Insert Customer Baru Ke Database
		try {
			// Generate ID Customer
			String newId = userRepo.generateCustomerID();

			// Buat Object Customer Baru
			Customer customer = new Customer(newId, fullname, email, password, phone, address, 0);

			// Simpan Customer Ke Database
			Customer saved = userRepo.insertCustomer(customer);

			// Cek Apakah Insert Berhasil
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

	// Login Menggunakan Email Dan Password
	public User login(String email, String password) {
		// Validasi Input Kosong
		if (email.isBlank() || password.isBlank())
			return null;

		// Ambil User Dari Database
		return userRepo.findByEmailAndPassword(email, password);
	}

	// Update Balance User (Top Up / Reduce)
	public boolean updateBalance(String idUser, double balance) {
		// Mengupdate Balance Ke Database
		return userRepo.updateBalance(idUser, balance);
	}
	
	
	// Edit Profil Customer / User
	public String editProfile(User user, String fullName, String phone, String address) {

		StringBuilder sb = new StringBuilder();

		// Validasi Full Name
		if (fullName.isEmpty()) {
			sb.append("Full Name must be filled!\n");
		}

		// Validasi Nomor Telepon
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
				sb.append("Phone must be 10–13 digits!\n");
			}
		}

		// Validasi Address
		if (address.isEmpty()) {
			sb.append("Address must be filled!\n");
		}

		// Jika Ada Error, Return Pesan
		if (sb.length() > 0)
			return sb.toString();

		// Set Data Baru Pada Object User
		user.setFullname(fullName);
		user.setPhone(phone);
		user.setAddress(address);

		// Simpan Perubahan Ke Database
		UserDA da = new UserDA();
		if (da.updateProfileDA(user))
			return "SUCCESS";

		return "Failed to update profile!";
	}
}
