package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.CourierHandler;
import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Admin;
import model.Courier;
import model.Customer;
import model.OrderHeader;

public class AdminOrderList extends GridPane{
	private AdminHomePage parent;
	private Admin admin;
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
	private ProductHandler productHandler = new ProductHandler();
	
	public AdminOrderList(AdminHomePage parent, Admin admin) {
		super();
		this.parent = parent;
		this.admin = admin;
		
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
		
        ArrayList<OrderHeader> orders = orderHandler.getAllCustomerOrders();
            
        if(orders .isEmpty()) {
        	add(new Label("No Order Available"), 0, 0);
            return;
        }
        
        add(new Label("Order ID"), 0, 0);
        add(new Label("Customer ID"), 1, 0);
        add(new Label("Date"), 2, 0);
        add(new Label("Amount"), 3, 0);
        add(new Label("Status"), 4, 0);
        add(new Label("Action"), 5, 0);
        add(new Label("Courier"), 6, 0);
        add(new Label("Action"), 7, 0);
        
        int row = 1;
        
        for (OrderHeader orderHeader : orders) {
			Label idLbl = new Label(orderHeader.getIdOrder());
			Label custLbl = new Label(orderHeader.getIdCustomer());
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			Label statusLbl = new Label(orderHeader.getStatus());
			Label courierLbl = new Label(orderHeader.getCourierId());
			
			Button detailBtn = new Button("Details");
			Button assignCourier = new Button("Assign Courier");
			
			add(idLbl, 0, row);
			add(custLbl, 1, row);
            add(dateLbl, 2, row);
            add(amountLbl, 3, row);
            add(statusLbl, 4, row);
            add(detailBtn, 5, row);
            add(courierLbl, 6, row);
            add(assignCourier, 7, row);
            
            detailBtn.setOnAction(e-> {
            	parent.setCenter(new AdminOrderDetailPage(parent,admin,orderHeader));
            });
            
            //Assign Courier
            assignCourier.setOnAction(e -> {
            	CourierHandler courierHandler = new CourierHandler();
                ArrayList<Courier> couriers = courierHandler.getAllCouriers();
                
                if(couriers.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No couriers available.");
                    alert.show();
                    return;
                }
                
                // Create dialog
                Dialog<Courier> dialog = new Dialog<>();
                dialog.setTitle("Assign Courier");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                
                //ComboBox
                ComboBox<Courier> courierBox = new ComboBox<Courier>();
                courierBox.getItems().addAll(couriers);
                courierBox.setPromptText("Select Courier");
                
                VBox vbox = new VBox(10, new Label("Choose Courier: "), courierBox);
                vbox.setPadding(new Insets(10));

                dialog.getDialogPane().setContent(vbox);
                dialog.setResultConverter(button -> {
                    if(button == ButtonType.OK) {
                        return courierBox.getValue();
                    }
                    return null;
                });
                
                dialog.showAndWait().ifPresent(selectedCourier -> {
                	if(selectedCourier != null) {
                		courierHandler.assignCourier(orderHeader.getIdOrder(), selectedCourier.getIdUser());
                		Alert done = new Alert(Alert.AlertType.INFORMATION, 
                                "Courier " + selectedCourier.getFullname() + " assigned!");
                            done.show();
                            parent.setCenter(new AdminOrderList(parent, admin));
                	}
                });    
            });
            
            row++;
		}

}
}
