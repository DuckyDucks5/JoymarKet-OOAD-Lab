package view;

import controller.CustomerController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Customer;

public class TopUpPage extends GridPane {
	private Customer customer;
	private Label balanceLbl;

	public TopUpPage(Customer customer) {
		this.customer = customer;

		setVgap(10);
		setHgap(10);
		setPadding(new Insets(20));
		setAlignment(Pos.CENTER);

		Label title = new Label("Top Up Balance");
		title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

		balanceLbl = new Label("Current Balance: " + customer.getBalance());
		Label amountLbl = new Label("Amount to Top Up");
		TextField amountTf = new TextField();

		Button topUpBtn = new Button("Top Up");

		add(title, 0, 0, 2, 1);
		add(balanceLbl, 0, 1, 2, 1);
		add(amountLbl, 0, 2);
		add(amountTf, 1, 2);
		add(topUpBtn, 1, 3);

		topUpBtn.setOnAction(e -> {
			String input = amountTf.getText().trim();
			if (input.isEmpty()) {
				showAlert("Error", "Amount must be filled!");
				return;
			}

			double amount;
			try {
				amount = Double.parseDouble(input);
			} catch (NumberFormatException ex) {
				showAlert("Error", "Amount must be numeric!");
				return;
			}

			CustomerController controller = new CustomerController();
			String result = controller.topUp(customer, amount);

			if ("SUCCESS".equals(result)) {
				balanceLbl.setText("Current Balance: " + customer.getBalance());
				showAlert("Success", "Top up successful!\nNew Balance: " + customer.getBalance());
				amountTf.clear();
			} else {
				showAlert("Error", result);
			}
		});
	}

	private void showAlert(String title, String message) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setHeaderText(title);
		a.setContentText(message);
		a.showAndWait();
	}
}
