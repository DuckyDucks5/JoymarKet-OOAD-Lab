package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.CourierHandler;
import controller.DeliveryHandler;
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

public class AdminOrderListPage extends GridPane{
	private AdminHomePage parent;
	private Admin admin;
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
	private ProductHandler productHandler = new ProductHandler();
	private DeliveryHandler deliveryHandler = new DeliveryHandler();
	private CourierHandler courierHanlder = new CourierHandler();
	
	public AdminOrderListPage(AdminHomePage parent, Admin admin) {
		super();
		this.parent = parent;
		this.admin = admin;
		
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
		
        // Ambil semua order dari customer
        ArrayList<OrderHeader> orders = orderHandler.getAllCustomerOrders();
            
        // Jika tidak ada order, tampilkan pesan
        if(orders.isEmpty()) {
        	add(new Label("No Order Available"), 0, 0);
            return;
        }
        
        // Tambahkan header kolom
        add(new Label("Order ID"), 0, 0);
        add(new Label("Customer ID"), 1, 0);
        add(new Label("Date"), 2, 0);
        add(new Label("Amount"), 3, 0);
        add(new Label("Status"), 4, 0);
        add(new Label("Action"), 5, 0);
        add(new Label("Courier"), 6, 0);
        add(new Label("Action"), 7, 0);
        
        int row = 1;
        
        // Looping untuk menampilkan setiap order
        for (OrderHeader orderHeader : orders) {
			Label idLbl = new Label(orderHeader.getIdOrder());
			Label custLbl = new Label(orderHeader.getIdCustomer());
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			Label statusLbl = new Label(orderHeader.getStatus());
			
			// Ambil courier yang ditugaskan ke order ini
			String idCourier = courierHanlder.getCourier(idLbl.getText());
			
			Label courierLbl = new Label(idCourier);
			Button detailBtn = new Button("Details");
			Button assignCourierBtn = new Button("Assign Courier");
			
			// Tambahkan elemen ke GridPane
			add(idLbl, 0, row);
			add(custLbl, 1, row);
            add(dateLbl, 2, row);
            add(amountLbl, 3, row);
            add(statusLbl, 4, row);
            add(detailBtn, 5, row);
            add(courierLbl, 6, row);
            
            // Tampilkan tombol assign courier hanya jika status Pending
            if(statusLbl.getText().equals("Pending")) {
            	add(assignCourierBtn, 7, row);
            }
            
            // Action untuk tombol Detail
            detailBtn.setOnAction(e-> {
            	parent.setCenter(new AdminOrderDetailPage(parent,admin,orderHeader));
            });
            
            // Action untuk tombol Assign Courier
            assignCourierBtn.setOnAction(e -> {
            	CourierHandler courierHandler = new CourierHandler();
                ArrayList<Courier> couriers = courierHandler.getAllCouriers();
                
                // Jika tidak ada courier, tampilkan alert
                if(couriers.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No couriers available.");
                    alert.show();
                    return;
                }
                
                // Buat dialog pemilihan courier
                Dialog<Courier> dialog = new Dialog<>();
                dialog.setTitle("Assign Courier");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                
                // Tambahkan ComboBox untuk memilih courier
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
                
                // Jika courier dipilih, lakukan assignment
                dialog.showAndWait().ifPresent(selectedCourier -> {
                	if(selectedCourier != null) {
                		deliveryHandler.assignCourierToOrder(orderHeader.getIdOrder(), selectedCourier.getIdUser());
                		courierHandler.assignCourier(orderHeader.getIdOrder());
                		Alert done = new Alert(Alert.AlertType.INFORMATION, 
                                "Courier " + selectedCourier.getFullname() + " assigned!");
                        done.show();
                        // Refresh halaman order
                        parent.setCenter(new AdminOrderListPage(parent, admin));
                	}
                });    
            });
            
            row++;
		}

}
}
