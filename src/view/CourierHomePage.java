package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import model.Courier;

public class CourierHomePage extends BorderPane {
	private Courier courier;
	
	public CourierHomePage() {
		Button productListCRUDBtn = new Button("Product List");
		Button assignedDeliveriesBtn = new Button("Assigned Delivery");
//		Button editDeliveryStatusBtn = new Button("Order List");
		
		HBox navBar = new HBox(10, productListCRUDBtn, assignedDeliveriesBtn);
	    navBar.setAlignment(Pos.CENTER_RIGHT);
	    navBar.setPadding(new Insets(15));

	    Label title = new Label("Welcome Courier!");
	    title.setFont(new Font("Arial", 30));

	    BorderPane.setAlignment(title, Pos.CENTER);

	    this.setTop(navBar);
	    this.setCenter(title);
       
	    productListCRUDBtn.setOnAction(e -> {
	    	this.setCenter(new ProductListPage());
	    });

	    assignedDeliveriesBtn.setOnAction(e -> {
//        	this.setCenter(new CourierListPage());
        });
	      
	}
	
}
