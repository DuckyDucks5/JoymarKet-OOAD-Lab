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
		Button loginBtn = new Button("Login");
		Button registerBtn = new Button("Register");

		HBox navBar = new HBox(10, registerBtn, loginBtn);
		navBar.setAlignment(Pos.CENTER_RIGHT);
		navBar.setPadding(new Insets(15));

		Label title = new Label("Welcome To JoymarkEt!");
		title.setFont(new Font("Arial", 30));

		BorderPane.setAlignment(title, Pos.CENTER);

		this.setTop(navBar);
		this.setCenter(title);

		loginBtn.setOnAction(e -> {
			this.getScene().setRoot(new LoginPage());
		});

		registerBtn.setOnAction(e -> {
			this.getScene().setRoot(new RegisterPage());
		});
	}
}