package view;

import java.util.ArrayList;

import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Customer;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class CourierViewDetailAllOrderPage extends GridPane{

	private OrderHeader orderHeader;
	private CourierHomePage parent;
	
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    private ProductHandler productHandler = new ProductHandler();
	
    public CourierViewDetailAllOrderPage(CourierHomePage parent, OrderHeader oh) {
    	this.parent = parent;
    	this.orderHeader = oh;
    	
		setPadding(new Insets(20));
		setVgap(10);
		setHgap(20);
	  
		add(new Label("Order ID: " + oh.getIdOrder()), 0, 0);
		add(new Label("Status: " + oh.getStatus()), 0, 1);
		add(new Label("Date: " + oh.getOrderedAt()), 0, 2);
	
		add(new Label("Product"), 0, 4);
		add(new Label("Qty"), 1, 4);
		add(new Label("Subtotal"), 2, 4);
		  
		ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());
		  
		int row = 5;
		  
		for (OrderDetail orderDetail : detail) {
			Product p = productHandler.getProduct(orderDetail.getIdProduct());
		
			add(new Label(p.getName()), 0, row);
			add(new Label(String.valueOf(orderDetail.getQty())), 1, row);
			add(new Label(String.valueOf(p.getPrice() * orderDetail.getQty())), 2, row);
	
			row++;
		}
	}

}
