package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class RegisterPage extends GridPane {

	public RegisterPage() {
		this.setVgap(10);
		this.setHgap(10);
		this.setPadding(new Insets(20));

		Label title = new Label("Registration Page");
		title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

		Label nameLbl = new Label("Full Name");
		TextField nameTf = new TextField();

		Label emailLbl = new Label("Email");
		TextField emailTf = new TextField();

		Label passLbl = new Label("Password");
		PasswordField passPf = new PasswordField();

		Label confirmLbl = new Label("Confirm Password");
		PasswordField confirmPf = new PasswordField();

		Label phoneLbl = new Label("Phone");
		TextField phoneTf = new TextField();

		Label addressLbl = new Label("Address");
		TextArea addressTa = new TextArea();
		addressTa.setPrefRowCount(2);

		Button submitBtn = new Button("Submit");

		this.add(title, 0, 0, 2, 1);
		this.add(nameLbl, 0, 1);
		this.add(nameTf, 1, 1);
		this.add(emailLbl, 0, 2);
		this.add(emailTf, 1, 2);
		this.add(passLbl, 0, 3);
		this.add(passPf, 1, 3);
		this.add(confirmLbl, 0, 4);
		this.add(confirmPf, 1, 4);
		this.add(phoneLbl, 0, 5);
		this.add(phoneTf, 1, 5);
		this.add(addressLbl, 0, 6);
		this.add(addressTa, 1, 6);
		this.add(submitBtn, 1, 8);

		GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);

		submitBtn.setOnAction(e -> {
			UserHandler controller = new UserHandler();
            String result = controller.registerAccount(
                    nameTf.getText(),
                    emailTf.getText(),
                    passPf.getText(),
                    confirmPf.getText(),
                    phoneTf.getText(),
                    addressTa.getText()
            );

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);

            if ("SUCCESS".equals(result)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Account created successfully!");
                alert.showAndWait();
                getScene().setRoot(new LoginPage());
            } else {
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Registration Failed");
                alert.setContentText(result);
                alert.showAndWait();
            }
		});
	}
}