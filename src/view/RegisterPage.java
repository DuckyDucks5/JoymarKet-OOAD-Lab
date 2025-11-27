package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import model.Customer;
import repository.UserRepo;

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

		Label genderLbl = new Label("Gender");
		RadioButton rbMale = new RadioButton("Male");
		RadioButton rbFemale = new RadioButton("Female");
		ToggleGroup tg = new ToggleGroup();
		rbMale.setToggleGroup(tg);
		rbFemale.setToggleGroup(tg);

		HBox genderBox = new HBox(12, rbMale, rbFemale);

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
		this.add(genderLbl, 0, 7);
		this.add(genderBox, 1, 7);
		this.add(submitBtn, 1, 9);

		GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);

		submitBtn.setOnAction(e -> {
			String errors = validate(nameTf.getText(), emailTf.getText(), passPf.getText(), confirmPf.getText(),
					phoneTf.getText(), addressTa.getText(), tg.getSelectedToggle());

			if (!errors.isEmpty()) {
				Alert a = new Alert(Alert.AlertType.ERROR);
				a.setHeaderText("Registration Failed");
				a.setContentText(errors);
				a.show();
			} else {
				String fullname = nameTf.getText();
				String email = emailTf.getText();
				String password = passPf.getText();
				String phone = phoneTf.getText();
				String address = addressTa.getText();
				double balance = 0; // default saldo awal

				UserRepo repo = new UserRepo();

				String newId = repo.generateCustomerID();
				Customer tempCustomer = new Customer(newId, fullname, email, password, phone, address, balance);
				Customer registered = repo.registerCustomer(tempCustomer);

				if (registered != null) {
					Alert a = new Alert(Alert.AlertType.INFORMATION);
					a.setHeaderText("Success");
					a.setContentText("Account created successfully!");
					a.showAndWait();
					getScene().setRoot(new LoginPage());
				} else {
					Alert a = new Alert(Alert.AlertType.ERROR);
					a.setHeaderText("Registration Failed");
					a.setContentText("Something went wrong while creating your account.");
					a.show();
				}
			}
		});
	}

	private String validate(String name, String email, String pass, String confirm, String phone, String address,
			Toggle gender) {
		StringBuilder sb = new StringBuilder();

		if (name.isEmpty())
			sb.append("- Full Name cannot be empty.\n");

		if (email.isEmpty())
			sb.append("- Email must be filled.\n");
		else if (!email.endsWith("@gmail.com"))
			sb.append("- Email must end with @gmail.com.\n");

		if (pass.length() < 6)
			sb.append("- Password must be at least 6 characters.\n");
		if (!pass.equals(confirm))
			sb.append("- Confirm password must match Password.\n");

		if (phone.isEmpty())
			sb.append("- Phone must be filled.\n");
		else {
			if (!phone.matches("\\d+"))
				sb.append("- Phone must be numeric.\n");
			if (phone.length() < 10 || phone.length() > 13)
				sb.append("- Phone must be 10–13 digits.\n");
		}

		if (address.isEmpty())
			sb.append("- Address must be filled.\n");

		if (gender == null)
			sb.append("- Gender must be chosen.\n");

		return sb.toString();
	}

}
