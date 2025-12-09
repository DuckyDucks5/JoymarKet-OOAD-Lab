package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.DeliveryHandler;
import controller.OrderHeaderHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Courier;
import model.OrderHeader;

public class CourierAssignedDeliveryPage extends GridPane{

	private Courier courier;
	private CourierHomePage parent;
	DeliveryHandler deliveryHandler = new DeliveryHandler();
	OrderHeaderHandler orderHandler = new OrderHeaderHandler();
	
	public CourierAssignedDeliveryPage(CourierHomePage parent, Courier courier) {
		this.courier = courier;
		this.parent = parent;
		
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
		
        ArrayList<OrderHeader> orders = deliveryHandler.getDeliveries(courier.getIdUser());
        
        List<OrderHeader> notDelivered = orders.stream()
                .filter(o -> !"Delivered".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        
        if(notDelivered .isEmpty()) {
        	add(new Label("No Order Available"), 0, 0);
            return;
        }
        
        add(new Label("Order ID"), 0, 0);
        add(new Label("Date"), 1, 0);
        add(new Label("Amount"), 2, 0);
        add(new Label("Status"), 3, 0);
        add(new Label("Action"), 4, 0);
        
        int row = 1;
        
        for (OrderHeader orderHeader : notDelivered) {
			Label idLbl = new Label(orderHeader.getIdOrder());
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			Label statusLbl = new Label(orderHeader.getStatus());
			
			Button completeBtn = new Button("Complete");
			
			add(idLbl, 0, row);
            add(dateLbl, 1, row);
            add(amountLbl, 2, row);
            add(statusLbl, 3, row);
            
            if (statusLbl.getText().equalsIgnoreCase("Waiting for Delivery")) {
                add(completeBtn, 4, row);
            }
            
            completeBtn.setOnAction(e-> {
            	String idOrder = idLbl.getText();
            	
            	orderHandler.updateStatus(idOrder, "Delivered");
            	showAlert("Success", idOrder+ " was delivered successfully!");
            	
            	parent.setCenter(new CourierAssignedDeliveryPage(parent, courier));
            });
            
            
            
            row++;
		}
		
	}
	
	private void showAlert(String title, String message) {
		Alert a = new Alert(Alert.AlertType.INFORMATION);
		a.setHeaderText(title);
		a.setContentText(message);
		a.showAndWait();
	}

}
