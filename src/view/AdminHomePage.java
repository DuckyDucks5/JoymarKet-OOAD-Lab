package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import main.Main;
import model.Admin;
import model.Customer;

public class AdminHomePage extends BorderPane {
	private Admin admin; // Menyimpan data admin yang sedang login
	
	public AdminHomePage(Admin admin) {
		this.admin = admin;
		
		// Membuat tombol navigasi untuk berbagai halaman
		Button productListCRUDBtn = new Button("Product List");
	    Button courirPageBtn = new Button("Courier Page");
	    Button orderPageBtn = new Button("Order Page");
	    Button editProfileBtn = new Button("Edit Profile");
	    Button logOutBtn = new Button("Log Out");

	    // Membuat HBox sebagai navbar dan mengatur tampilan
	    HBox navBar = new HBox(10, productListCRUDBtn, courirPageBtn, orderPageBtn, editProfileBtn, logOutBtn);
	    navBar.setAlignment(Pos.CENTER_RIGHT);
	    navBar.setPadding(new Insets(15));

	    // Membuat label judul di tengah halaman
	    Label title = new Label("Welcome Admin!");
	    title.setFont(new Font("Arial", 30));
	    BorderPane.setAlignment(title, Pos.CENTER);

	    // Menempatkan navbar di atas dan judul di tengah BorderPane
	    this.setTop(navBar);
	    this.setCenter(title);
	       
	    // Event handler tombol untuk menampilkan halaman yang sesuai saat diklik
	    productListCRUDBtn.setOnAction(e -> {
	        this.setCenter(new ProductListPage()); // Menampilkan halaman daftar produk
	    });

	    courirPageBtn.setOnAction(e -> {
	    	this.setCenter(new CourierListPage()); // Menampilkan halaman daftar kurir
	    });
	        
	    orderPageBtn.setOnAction(e -> {
	    	this.setCenter(new AdminOrderListPage(this, admin)); // Menampilkan halaman daftar order untuk admin
	    });
	        
	    editProfileBtn.setOnAction(e -> {
	        this.setCenter(new UserEditProfilePage(admin)); // Menampilkan halaman edit profil admin
	    });
	        
	    logOutBtn.setOnAction(e -> {
	    	Main.changeScene(new LoginPage()); // Logout dan kembali ke halaman login
	    });
	}
}
