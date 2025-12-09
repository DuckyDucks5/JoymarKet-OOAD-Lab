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
	
	public CourierHomePage(Courier courier) {
		this.courier = courier;
		Button orderListCRUDBtn = new Button("Order List");
		Button assignedDeliveriesBtn = new Button("Assigned Delivery");
//		Button editDeliveryStatusBtn = new Button("Order List");
		
		HBox navBar = new HBox(10, orderListCRUDBtn, assignedDeliveriesBtn);
	    navBar.setAlignment(Pos.CENTER_RIGHT);
	    navBar.setPadding(new Insets(15));

	    Label title = new Label("Welcome Courier!");
	    title.setFont(new Font("Arial", 30));

	    BorderPane.setAlignment(title, Pos.CENTER);

	    this.setTop(navBar);
	    this.setCenter(title);
       
	    orderListCRUDBtn.setOnAction(e -> {
	    	this.setCenter(new CourierViewAllOrderPage(this, courier));
	    });

	    assignedDeliveriesBtn.setOnAction(e -> {
	    	this.setCenter(new CourierAssignedDeliveryPage(this, courier));
        });
	      
	}
	
}
