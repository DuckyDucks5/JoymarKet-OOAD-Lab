package view;

import controller.CustomerHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import model.Customer;

public class CustEditProfilePage extends GridPane {

    private Customer customer;
    private Button confirmBtn;

    public CustEditProfilePage(Customer customer) {
        this.customer = customer;

        setVgap(10);
        setHgap(10);
        setPadding(new Insets(20));
        setAlignment(Pos.CENTER);

        Label title = new Label("Edit Profile");
        title.setStyle("-fx-font-size:20px; -fx-font-weight:bold;");

        TextField fullNameTf = new TextField(customer.getFullname());
        TextField phoneTf = new TextField(customer.getPhone());
        TextField addressTf = new TextField(customer.getAddress());

        confirmBtn = new Button("Confirm");
        confirmBtn.setVisible(false);

        fullNameTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));
        phoneTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));
        addressTf.setOnKeyTyped(e -> confirmBtn.setVisible(true));

        add(title, 0, 0, 2, 1);
        add(new Label("Full Name"), 0, 1);
        add(fullNameTf, 1, 1);

        add(new Label("Phone"), 0, 2);
        add(phoneTf, 1, 2);

        add(new Label("Address"), 0, 3);
        add(addressTf, 1, 3);

        add(confirmBtn, 1, 4);

        confirmBtn.setOnAction(e -> {
            CustomerHandler handler = new CustomerHandler();
            String result = handler.editProfile(
                customer,
                fullNameTf.getText(),
                phoneTf.getText(),
                addressTf.getText()
            );

            if("SUCCESS".equals(result)) {
                showAlert("Success", "Profile updated successfully!");
                confirmBtn.setVisible(false);
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