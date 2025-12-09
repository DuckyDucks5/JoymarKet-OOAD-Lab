package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import main.Main;
import model.Customer;

public class CustomerHomePage extends BorderPane {
	private Customer customer;
	
	public CustomerHomePage(Customer customer) {
		this.customer = customer;
		
		Button productListBtn = new Button("Product List");
		Button cartBtn = new Button("Cart");
		Button orderListBtn = new Button("Order List");
		Button orderHistoryBtn = new Button("Order History");
		Button topUpBtn = new Button("Top Up");
		Button editProfileBtn = new Button("Edit Profile");
		Button logOutBtn = new Button("Log Out");

		HBox navBar = new HBox(10, productListBtn, cartBtn, orderListBtn, orderHistoryBtn, topUpBtn, editProfileBtn, logOutBtn);
		navBar.setAlignment(Pos.CENTER_RIGHT);
		navBar.setPadding(new Insets(15));

		Label title = new Label("Welcome Customer!");
		title.setFont(new Font("Arial", 30));

		BorderPane.setAlignment(title, Pos.CENTER);

		this.setTop(navBar);
		this.setCenter(title);

		productListBtn.setOnAction(e -> {
			this.setCenter(new 	CustProductListPage(this, customer));
		});

		cartBtn.setOnAction(e -> {
			this.setCenter(new CustCartPage(this, customer));
		});

		orderListBtn.setOnAction(e -> {
	        	this.setCenter(new CustOrderListPage(this, customer));
		});

		orderHistoryBtn.setOnAction(e -> {
	        	this.setCenter(new CustOrderHistory(this, customer));
		});

		topUpBtn.setOnAction(e -> {
			this.setCenter(new CustTopUpPage(customer));
		});

		editProfileBtn.setOnAction(e -> {
	        this.setCenter(new UserEditProfilePage(customer));
		});
		logOutBtn.setOnAction(e -> {
		    Main.changeScene(new LoginPage());
		});

	}
}
