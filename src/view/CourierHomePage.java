package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import main.Main;
import model.Courier;

public class CourierHomePage extends BorderPane {
	private Courier courier;
	
	public CourierHomePage(Courier courier) {
		this.courier = courier;
		
		// Membuat Tombol Untuk Navigasi Halaman
		Button orderListCRUDBtn = new Button("Assigned Order List");
		Button editProfileBtn = new Button("Edit Profile");
		Button logOutBtn = new Button("Log Out");
		
		// Membuat NavBar Horizontal Untuk Menampung Tombol
		HBox navBar = new HBox(10, orderListCRUDBtn, editProfileBtn, logOutBtn);
	    navBar.setAlignment(Pos.CENTER_RIGHT); // Mengatur Posisi Tombol Di Sebelah Kanan
	    navBar.setPadding(new Insets(15)); // Memberikan Jarak Dalam NavBar

	    // Membuat Label Judul Halaman
	    Label title = new Label("Welcome Courier!");
	    title.setFont(new Font("Arial", 30)); // Mengatur Font Dan Ukuran Judul

	    BorderPane.setAlignment(title, Pos.CENTER); // Menengahkan Label Di BorderPane

	    // Menempatkan NavBar Dan Judul Di Layout BorderPane
	    this.setTop(navBar);
	    this.setCenter(title);
       
	    // Event Tombol Untuk Menampilkan Halaman Assigned Order List
	    orderListCRUDBtn.setOnAction(e -> {
	    	this.setCenter(new CourierViewAllOrderPage(this, courier));
	    });

	    // Event Tombol Untuk Mengedit Profil User
	    editProfileBtn.setOnAction(e -> {
	        this.setCenter(new UserEditProfilePage(courier));
		});
	    
	    // Event Tombol Log Out Untuk Kembali Ke Halaman Login
	    logOutBtn.setOnAction(e -> {
	    	Main.changeScene(new LoginPage());
	    });
	      
	}
	
}
