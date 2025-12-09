package view;

import controller.UserHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class RegisterPage extends BorderPane {

    public RegisterPage() {

        // Buat form utama menggunakan GridPane
        GridPane form = new GridPane();
        form.setVgap(15);
        form.setHgap(10);
        form.setPadding(new Insets(20));
        form.setAlignment(Pos.CENTER);

        // Judul halaman
        Label title = new Label("Registration Page");
        title.setFont(Font.font("Arial", 26));
        title.setStyle("-fx-font-weight: bold;");

        // Input field
        Label nameLbl = new Label("Full Name");
        TextField nameTf = new TextField();
        nameTf.setPromptText("Enter your full name");

        Label emailLbl = new Label("Email");
        TextField emailTf = new TextField();
        emailTf.setPromptText("Enter your email");

        Label passLbl = new Label("Password");
        PasswordField passPf = new PasswordField();
        passPf.setPromptText("Enter password");

        Label confirmLbl = new Label("Confirm Password");
        PasswordField confirmPf = new PasswordField();
        confirmPf.setPromptText("Re-enter password");

        Label phoneLbl = new Label("Phone");
        TextField phoneTf = new TextField();
        phoneTf.setPromptText("Enter phone number");

        Label addressLbl = new Label("Address");
        TextArea addressTa = new TextArea();
        addressTa.setPromptText("Enter your address");
        addressTa.setPrefRowCount(3);

        // Tombol Submit dan Login
        Button submitBtn = new Button("Submit");
        submitBtn.setPrefWidth(600);
        submitBtn.setAlignment(Pos.CENTER);
        submitBtn.setStyle(
                "-fx-background-color: #000000; -fx-text-fill: white; " +
                        "-fx-font-size: 14px; -fx-padding: 10 20;");

        Button loginBtn = new Button("Go to Login");
        loginBtn.setPrefWidth(600);
        loginBtn.setAlignment(Pos.CENTER);
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
        form.add(submitBtn, 0, 7, 2, 1);
        form.add(loginBtn, 0, 8, 2, 1);
        GridPane.setHalignment(title, javafx.geometry.HPos.CENTER);

        // Bungkus form dalam VBox untuk card style
        VBox card = new VBox(form);
        card.setPadding(new Insets(30));
        card.setAlignment(Pos.CENTER);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15;" +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 10,0,0,4);");

        this.setCenter(card);
        this.setStyle("-fx-background-color: #f0f2f5;");
        BorderPane.setAlignment(card, Pos.CENTER);

        // Action tombol Go to Login
        loginBtn.setOnAction(e -> getScene().setRoot(new LoginPage()));

        // Action tombol Submit untuk registrasi
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

            // Jika registrasi berhasil
            if ("SUCCESS".equals(result)) {
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Account created successfully!");
                alert.showAndWait();

                // Kembali ke halaman Login
                getScene().setRoot(new LoginPage());
            } else {
                // Jika gagal, tampilkan pesan error
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setHeaderText("Registration Failed");
                alert.setContentText(result);
                alert.showAndWait();
            }
        });
    }
}
