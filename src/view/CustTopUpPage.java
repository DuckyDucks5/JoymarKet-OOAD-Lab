package view;

import controller.CustomerHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;

public class CustTopUpPage extends GridPane {
	private Customer customer; // menyimpan data customer yang sedang login
	private Label balanceLbl;

	public CustTopUpPage(Customer customer) {
		this.customer = customer;

		// Mengatur Layout GridPane
		setVgap(15);
		setHgap(15);
		setPadding(new Insets(25));
		setAlignment(Pos.CENTER);

		// Label Judul Halaman
		Label title = new Label("Top Up Balance");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 22));

		// Label Untuk Menampilkan Saldo Saat Ini
		balanceLbl = new Label("Current Balance: " + customer.getBalance());
		balanceLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));

		Label amountLbl = new Label("Amount to Top Up");
		amountLbl.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
		TextField amountTf = new TextField();
		amountTf.setPrefWidth(150);

		// Tombol Top Up
		Button topUpBtn = new Button("Top Up");
		topUpBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");
		topUpBtn.setPrefWidth(100);

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
