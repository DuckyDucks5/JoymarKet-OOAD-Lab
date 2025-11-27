package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;

public class AdminHomePage extends BorderPane {
	public AdminHomePage() {
		Button productListCRUDBtn = new Button("Product List");
	    Button courirPageBtn = new Button("Courir Page");
	    Button orderPageBtn = new Button("Order Page");

	       HBox navBar = new HBox(10, productListCRUDBtn, courirPageBtn, orderPageBtn);
	       navBar.setAlignment(Pos.CENTER_RIGHT);
	       navBar.setPadding(new Insets(15));

	       Label title = new Label("Welcome Admin!");
	       title.setFont(new Font("Arial", 30));

	       BorderPane.setAlignment(title, Pos.CENTER);

	       this.setTop(navBar);
	       this.setCenter(title);

	       productListCRUDBtn.setOnAction(e -> {
//	           this.getScene().setRoot(new ProductListPage());
	       });

	        courirPageBtn.setOnAction(e -> {
//	            this.getScene().setRoot(new CourirPage());
	        });
	        
	        orderPageBtn.setOnAction(e -> {
//	        	this.getScene().setRoot(new OrderPage());
	        });
	}
}
