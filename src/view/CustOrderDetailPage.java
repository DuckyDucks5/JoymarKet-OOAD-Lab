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

public class CustOrderDetailPage extends GridPane {

    // Handler untuk mengambil data order dan produk
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    private ProductHandler productHandler = new ProductHandler();

    
    public CustOrderDetailPage(CustomerHomePage parent, Customer cust, OrderHeader oh) {
        // Atur padding dan jarak antar grid
		setPadding(new Insets(20));
		setVgap(10);
		setHgap(20);

        // Tampilkan informasi order
	    add(new Label("Order ID: " + oh.getIdOrder()), 0, 0);
	    add(new Label("Status: " + oh.getStatus()), 0, 1);
	    add(new Label("Date: " + oh.getOrderedAt()), 0, 2);

        // Tampilkan header tabel detail produk
	    add(new Label("Product"), 0, 4);
	    add(new Label("Qty"), 1, 4);
	    add(new Label("Subtotal"), 2, 4);

        // Ambil semua detail order
	    ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());

	    int row = 5;
        // Looping setiap detail order dan tampilkan data produk
	    for (OrderDetail orderDetail : detail) {
			Product p = productHandler.getProduct(orderDetail.getIdProduct());

	        add(new Label(p.getName()), 0, row);
	        add(new Label(String.valueOf(orderDetail.getQty())), 1, row);
	        add(new Label(String.valueOf(p.getPrice() * orderDetail.getQty())), 2, row);

	        row++;
		}
	}
}
