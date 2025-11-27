package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Customer;
import repository.UserRepo;

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

        balanceLbl = new Label("Current Balance: " + customer.getBalance()); // pakai field class
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

            if (amount < 10000) {
                showAlert("Error", "Minimum top-up amount is 10,000!");
                return;
            }

            double newBalance = customer.getBalance() + amount;

            UserRepo repo = new UserRepo();
            boolean updated = repo.updateBalance(customer.getIdUser(), newBalance);

            if (updated) {
                customer.setBalance(newBalance);
                balanceLbl.setText("Current Balance: " + customer.getBalance());

                showAlert("Success", "Top up successful!\nNew Balance: " + newBalance);
                amountTf.clear();
            } else {
                showAlert("Error", "Failed to update balance. Please try again.");
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
