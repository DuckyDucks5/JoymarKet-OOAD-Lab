package view;

import controller.CustomerHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Customer;

public class CustTopUpPage extends GridPane {
	private Customer customer;
	private Label balanceLbl;

	public CustTopUpPage(Customer customer) {
		this.customer = customer;

		// Mengatur Layout GridPane
		setVgap(10);
		setHgap(10);
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);

		// Label Judul Halaman
		Label title = new Label("Top Up Balance");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		// Label Untuk Menampilkan Saldo Saat Ini
		balanceLbl = new Label("Current Balance: " + customer.getBalance());
		Label amountLbl = new Label("Amount to Top Up");
		TextField amountTf = new TextField();

		// Tombol Top Up
		Button topUpBtn = new Button("Top Up");

		// Menambahkan Komponen Ke GridPane
		add(title, 0, 0, 2, 1);
		add(balanceLbl, 0, 1, 2, 1);
		add(amountLbl, 0, 2);
		add(amountTf, 1, 2);
		add(topUpBtn, 1, 3);

		// Event Tombol Top Up
		topUpBtn.setOnAction(e -> {
			String input = amountTf.getText().trim();
			if (input.isEmpty()) {
				showAlert("Error", "Amount must be filled!"); // Validasi Kosong
				return;
			}

			double amount;
			try {
				amount = Double.parseDouble(input); // Validasi Numerik
			} catch (NumberFormatException ex) {
				showAlert("Error", "Amount must be numeric!");
				return;
			}

			// Memanggil Controller Untuk Proses Top Up
			CustomerHandler controller = new CustomerHandler();
			String result = controller.topUpBalance(customer, amount);

			// Menangani Hasil Top Up
			if ("SUCCESS".equals(result)) {
				balanceLbl.setText("Current Balance: " + customer.getBalance());
				showAlert("Success", "Top up successful!\nNew Balance: " + customer.getBalance());
				amountTf.clear();
			} else {
				showAlert("Error", result);
			}
		});
	}

	// Method Untuk Menampilkan Alert
	private void showAlert(String title, String message) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setHeaderText(title);
		a.setContentText(message);
		a.showAndWait();
	}
}
