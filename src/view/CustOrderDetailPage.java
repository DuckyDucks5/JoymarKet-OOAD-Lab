package view;

import java.util.ArrayList;

import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class CustOrderDetailPage extends GridPane {

    // Handler untuk mengambil data order dan produk
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    private ProductHandler productHandler = new ProductHandler();

    // Lebar kolom konsisten
    private final int COL1 = 200;
    private final int COL2 = 80;
    private final int COL3 = 120;
    
    public CustOrderDetailPage(CustomerHomePage parent, Customer cust, OrderHeader oh) {
        // Atur padding dan jarak antar grid
		setPadding(new Insets(25));
		setVgap(15);
		setHgap(20);

        // Tampilkan informasi order
	    Label idLbl = new Label("Order ID: " + oh.getIdOrder());
	    idLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    Label statusLbl = new Label("Status: " + oh.getStatus());
	    statusLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    Label dateLbl = new Label("Date: " + oh.getOrderedAt());
	    dateLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
	    
	    add(idLbl, 0, 0);
	    add(statusLbl, 0, 1);
	    add(dateLbl, 0, 2);

        // Tampilkan header tabel detail produk
	    addHeaderLabel("Product", 0, COL1);
	    addHeaderLabel("Qty", 1, COL2);
	    addHeaderLabel("Subtotal", 2, COL3);

        // Ambil semua detail order
	    ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());

	    int row = 5;
        // Looping setiap detail order dan tampilkan data produk
	    for (OrderDetail orderDetail : detail) {
			Product p = productHandler.getProduct(orderDetail.getIdProduct());

	        Label nameLbl = new Label(p.getName());
	        nameLbl.setPrefWidth(COL1);
	        Label qtyLbl = new Label(String.valueOf(orderDetail.getQty()));
	        qtyLbl.setPrefWidth(COL2);
	        Label subTotalLbl = new Label(String.valueOf(p.getPrice() * orderDetail.getQty()));
	        subTotalLbl.setPrefWidth(COL3);
	        
	        add(nameLbl, 0, row);
	        add(qtyLbl, 1, row);
	        add(subTotalLbl, 2, row);

	        row++;
		}
	}

    // Method untuk membuat header label dengan format konsisten
    private void addHeaderLabel(String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setPrefWidth(width);
        add(lbl, col, 4);
    }
}
