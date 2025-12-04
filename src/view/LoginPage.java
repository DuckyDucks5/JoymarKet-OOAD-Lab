package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import model.Admin;
import model.Customer;
import model.User;

public class LoginPage extends BorderPane {

    public LoginPage() {

        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        Label title = new Label("Login Form");
        title.setFont(Font.font("Arial", 26));
        title.setStyle("-fx-font-weight: bold;");

        Label usernameLbl = new Label("Email");
        usernameLbl.setFont(Font.font(14));
        TextField usernameTf = new TextField();
        usernameTf.setPromptText("Enter your email");

        Label passwordLbl = new Label("Password");
        passwordLbl.setFont(Font.font(14));
        PasswordField passwordPf = new PasswordField();
        passwordPf.setPromptText("Enter your password");

        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(400);
        loginBtn.setAlignment(Pos.CENTER);
        loginBtn.setStyle(
                "-fx-background-color: #000000; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20;");

        Button registerBtn = new Button("Go to Register");
        registerBtn.setPrefWidth(400);
        registerBtn.setAlignment(Pos.CENTER);
        registerBtn.setStyle(
                "-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 8 20;");

        form.add(title, 0, 0, 2, 1);
        form.add(usernameLbl, 0, 1);
        form.add(usernameTf, 1, 1);
        form.add(passwordLbl, 0, 2);
        form.add(passwordPf, 1, 2);
        form.add(loginBtn, 0, 3, 2, 1);
        form.add(registerBtn, 0, 4, 2, 1);

        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);
        form.setMaxWidth(350);

        VBox card = new VBox(form);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 8,0,0,3);");

        this.setCenter(card);
        BorderPane.setAlignment(card, Pos.CENTER);
        this.setStyle("-fx-background-color: #f0f2f5;");

        registerBtn.setOnAction(e -> getScene().setRoot(new RegisterPage()));

        loginBtn.setOnAction(e -> {
            String email = usernameTf.getText().trim();
            String pass = passwordPf.getText().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                showAlert("Error", "Email and password cannot be empty!", AlertType.ERROR);
                return;
            }

            UserHandler controller = new UserHandler();
            User loggedIn = controller.login(email, pass);

            if (loggedIn == null) {
                showAlert("Login Failed", "Incorrect email or password.", AlertType.ERROR);
                return;
            }

            if (loggedIn instanceof Admin) {
                showAlert("Success", "Logged in as Admin!", AlertType.INFORMATION);
                getScene().setRoot(new AdminHomePage());
            } else if (loggedIn instanceof Customer) {
                showAlert("Success", "Logged in as Customer!", AlertType.INFORMATION);
                getScene().setRoot(new CustomerHomePage((Customer) loggedIn));
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
