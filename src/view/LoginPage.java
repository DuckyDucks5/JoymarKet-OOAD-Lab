package view;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.Admin;
import model.Customer;
import model.User;
import repository.UserRepo;

public class LoginPage extends GridPane {

	public LoginPage() {

		setVgap(10);
		setHgap(10);
		setPadding(new Insets(20));

		Label title = new Label("Login Form");
		title.setStyle("-fx-font-size: 22px; -fx-font-weight: bold;");

		Label usernameLbl = new Label("Email");
		TextField usernameTf = new TextField();

		Label passwordLbl = new Label("Password");
		PasswordField passwordPf = new PasswordField();

		Button loginBtn = new Button("Login");
		Button registerBtn = new Button("Go to Register");

		add(title, 0, 0, 2, 1);
		add(usernameLbl, 0, 1);
		add(usernameTf, 1, 1);
		add(passwordLbl, 0, 2);
		add(passwordPf, 1, 2);
		add(loginBtn, 1, 3);
		add(registerBtn, 1, 4);

		GridPane.setMargin(loginBtn, new Insets(10, 0, 0, 0));
		GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);

		registerBtn.setOnAction(e -> {
			getScene().setRoot(new RegisterPage());
		});

		loginBtn.setOnAction(e -> {
			String email = usernameTf.getText();
			String pass = passwordPf.getText();

			if (email.isBlank() || pass.isBlank()) {
				showAlert("Error", "Email and password cannot be empty!", AlertType.ERROR);
				return;
			}

			UserRepo repo = new UserRepo();
			User loggedIn = repo.login(email, pass);

			if (loggedIn == null) {
				showAlert("Login Failed", "Incorrect email or password.", AlertType.ERROR);
				return;
			}

			if (loggedIn instanceof Admin) {
				showAlert("Success", "Logged in as Admin!", AlertType.INFORMATION);
				getScene().setRoot(new AdminHomePage());
			} else if (loggedIn instanceof Customer) {
				showAlert("Success", "Logged in as Customer!", AlertType.INFORMATION);
				Customer loggedInCustomer = (Customer) loggedIn;
				getScene().setRoot(new CustomerHomePage(loggedInCustomer));
			}
		});
	}

	private void showAlert(String title, String message, AlertType type) {
	    Alert alert = new Alert(type);
	    alert.setTitle(title);
	    alert.setHeaderText(null);
	    alert.setContentText(message);
	    alert.showAndWait();
	}
}
