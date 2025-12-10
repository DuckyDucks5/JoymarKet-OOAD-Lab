package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class RegisterPage extends BorderPane {

    public RegisterPage() {

        // Grid utama untuk form input
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        // Judul halaman
        Label title = new Label("Registration Page");
        title.setFont(Font.font("Arial", 26));
        title.setStyle("-fx-font-weight: bold;");

        // Input Full Name
        Label nameLbl = new Label("Full Name");
        TextField nameTf = new TextField();
        nameTf.setPromptText("Enter your full name");

        // Input Email
        Label emailLbl = new Label("Email");
        TextField emailTf = new TextField();
        emailTf.setPromptText("Enter your email");

        // Input Password
        Label passLbl = new Label("Password");
        PasswordField passPf = new PasswordField();
        passPf.setPromptText("Enter password");

        // Input Confirm Password
        Label confirmLbl = new Label("Confirm Password");
        PasswordField confirmPf = new PasswordField();
        confirmPf.setPromptText("Re-enter password");

        // Input Phone
        Label phoneLbl = new Label("Phone");
        TextField phoneTf = new TextField();
        phoneTf.setPromptText("Enter phone number");

        // Input Address
        Label addressLbl = new Label("Address");
        TextArea addressTa = new TextArea();
        addressTa.setPromptText("Enter your address");
        addressTa.setPrefRowCount(3);

        // Input Gender
        Label genderLabel = new Label("Gender");

        // RadioButton untuk pilihan gender
        RadioButton maleRb = new RadioButton("Male");
        RadioButton femaleRb = new RadioButton("Female");

        ToggleGroup genderGroup = new ToggleGroup();
        maleRb.setToggleGroup(genderGroup);
        femaleRb.setToggleGroup(genderGroup);

        HBox genderBox = new HBox(15, maleRb, femaleRb);
        genderBox.setAlignment(Pos.CENTER_LEFT);

        // Tombol Submit
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(600);
        submitBtn.setStyle(
                "-fx-background-color: #000000; -fx-text-fill: white; " +
                "-fx-font-size: 14px; -fx-padding: 10 20;");

        // Tombol ke halaman login
        Button loginBtn = new Button("Go to Login");
        loginBtn.setPrefWidth(600);
        loginBtn.setStyle(
                "-fx-background-color: #2196F3; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");

        // Tambahkan semua elemen ke GridPane 
        form.add(title, 0, 0, 2, 1);
        form.add(nameLbl, 0, 1);
        form.add(nameTf, 1, 1);
        form.add(emailLbl, 0, 2);
        form.add(emailTf, 1, 2);
        form.add(passLbl, 0, 3);
        form.add(passPf, 1, 3);
        form.add(confirmLbl, 0, 4);
        form.add(confirmPf, 1, 4);
        form.add(phoneLbl, 0, 5);
        form.add(phoneTf, 1, 5);
        form.add(addressLbl, 0, 6);
        form.add(addressTa, 1, 6);
        form.add(genderLabel, 0, 7);
        form.add(genderBox, 1, 7);

        // Tombol submit & login
        form.add(submitBtn, 0, 8, 2, 1);
        form.add(loginBtn, 0, 9, 2, 1);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);

        // Bungkus form dalam card
        VBox card = new VBox(form);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        this.setCenter(card);
        this.setStyle("-fx-background-color: #f0f2f5;");
        BorderPane.setAlignment(card, Pos.CENTER);

        // Action tombol login
        loginBtn.setOnAction(e -> getScene().setRoot(new LoginPage()));

        // Action tombol Submit
        submitBtn.setOnAction(e -> {

            
            String gender = maleRb.isSelected() ? "Male" :
                            femaleRb.isSelected() ? "Female" : "";

            UserHandler controller = new UserHandler();
            String result = controller.registerAccount(
                    nameTf.getText(),
                    emailTf.getText(),
                    passPf.getText(),
                    confirmPf.getText(),
                    phoneTf.getText(),
                    addressTa.getText(),
                    gender
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
