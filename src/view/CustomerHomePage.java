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
	private Customer customer; //Menyimpan data customer yang sedang login
	
	public CustomerHomePage(Customer customer) {
		this.customer = customer;
		
		// Membuat tombol navigasi
		Button productListBtn = new Button("Product List");
		Button cartBtn = new Button("Cart");
		Button orderListBtn = new Button("Order List");
		Button orderHistoryBtn = new Button("Order History");
		Button topUpBtn = new Button("Top Up");
		Button editProfileBtn = new Button("Edit Profile");
		Button logOutBtn = new Button("Log Out");

		// membuat navbar
		HBox navBar = new HBox(10, productListBtn, cartBtn, orderListBtn, orderHistoryBtn, topUpBtn, editProfileBtn, logOutBtn);
		navBar.setAlignment(Pos.CENTER_RIGHT);
		navBar.setPadding(new Insets(15));

		// label judul
		Label title = new Label("Welcome Customer!");
		title.setFont(new Font("Arial", 30));

		BorderPane.setAlignment(title, Pos.CENTER);

		// memasukkan navbar dan title ke borderPane
		this.setTop(navBar);
		this.setCenter(title);

		//Event handler untuk menampilkan halaman produk
		productListBtn.setOnAction(e -> {
			this.setCenter(new 	CustProductListPage(this, customer));
		});

		//Event handler untuk menampilkan halaman keranjang
		cartBtn.setOnAction(e -> {
			this.setCenter(new CustCartPage(this, customer));
		});
		
		//Event handler untuk menampilkan halaman order list
		orderListBtn.setOnAction(e -> {
	        	this.setCenter(new CustOrderListPage(this, customer));
		});

		//Event handler untuk menampilkan halaman order history
		orderHistoryBtn.setOnAction(e -> {
	        	this.setCenter(new CustOrderHistory(this, customer));
		});

		//Event handler untuk menampilkan halaman top up
		topUpBtn.setOnAction(e -> {
			this.setCenter(new CustTopUpPage(customer));
		});

		//Event handler untuk menampilkan halaman edit profile
		editProfileBtn.setOnAction(e -> {
	        this.setCenter(new UserEditProfilePage(customer));
		});
		
		//Event handler untuk logout
		logOutBtn.setOnAction(e -> {
		    Main.changeScene(new LoginPage());
		});

	}
}
