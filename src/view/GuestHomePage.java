package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class GuestHomePage extends BorderPane {

    public GuestHomePage() {
        // Tombol Login dan Register
		Button loginBtn = new Button("Login");
		Button registerBtn = new Button("Register");

        // Navbar atas dengan tombol
		HBox navBar = new HBox(10, registerBtn, loginBtn);
		navBar.setAlignment(Pos.CENTER_RIGHT);
		navBar.setPadding(new Insets(15));

        // Judul halaman
		Label title = new Label("Welcome To JoymarkEt!");
		title.setFont(new Font("Arial", 30));
		BorderPane.setAlignment(title, Pos.CENTER);

        // Set posisi di BorderPane
		this.setTop(navBar);
		this.setCenter(title);

        // Event tombol Login
		loginBtn.setOnAction(e -> {
			this.getScene().setRoot(new LoginPage());
		});

        // Event tombol Register
		registerBtn.setOnAction(e -> {
			this.getScene().setRoot(new RegisterPage());
		});
	}
}
