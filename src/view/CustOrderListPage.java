package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Customer;
import model.OrderHeader;

public class CustOrderListPage extends GridPane{
	
	private CustomerHomePage parent;
	private Customer cust;
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
	private ProductHandler productHandler = new ProductHandler();
	
	public CustOrderListPage(CustomerHomePage parent, Customer cust) {
		super();
		this.parent = parent;
		this.cust = cust;
		
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
		
        ArrayList<OrderHeader> orders = orderHandler.getCustomerOrders(cust.getIdUser());
        
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
			
			Button detailBtn = new Button("Details");
			
			add(idLbl, 0, row);
            add(dateLbl, 1, row);
            add(amountLbl, 2, row);
            add(statusLbl, 3, row);
            add(detailBtn, 4, row);
            
            detailBtn.setOnAction(e-> {
            	parent.setCenter(new CustOrderDetailPage(parent,cust,orderHeader));
            });
            
            row++;
		}
	}
	
	
	
}
