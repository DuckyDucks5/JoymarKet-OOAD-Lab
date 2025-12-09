package view;

import java.util.ArrayList;

import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Admin;
import model.Customer;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class AdminOrderDetailPage extends GridPane{
    // Handler untuk mengelola data order
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    // Handler untuk mengelola data produk
    private ProductHandler productHandler = new ProductHandler();
	
    // Konstruktor halaman detail order untuk admin
    public AdminOrderDetailPage(AdminHomePage parent, Admin admin, OrderHeader oh) {
        // Mengatur padding dan jarak antar elemen GridPane
		  setPadding(new Insets(20));
		  setVgap(10);
		  setHgap(20);
		  
          // Menampilkan informasi dasar order
	      add(new Label("Order ID: " + oh.getIdOrder()), 0, 0);
	      add(new Label("Status: " + oh.getStatus()), 0, 1);
	      add(new Label("Date: " + oh.getOrderedAt()), 0, 2);
          // Info kurir dikomentari
//	      add(new Label("Courier ID: " + oh.getCourierId()), 0, 3);

          // Judul kolom untuk daftar produk
	      add(new Label("Product"), 0, 5);
	      add(new Label("Qty"), 1, 5);
	      add(new Label("Subtotal"), 2, 5);
	      
          // Mengambil detail order dari database
	      ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());
	      
	      int row = 6; // Baris awal untuk menampilkan produk
	      
	      // Loop untuk menampilkan setiap produk dalam order
	      for (OrderDetail orderDetail : detail) {
              // Mengambil data produk berdasarkan ID
			Product p = productHandler.getProduct(orderDetail.getIdProduct());
			
            // Menampilkan nama produk, jumlah, dan subtotal
			 add(new Label(p.getName()), 0, row);
	         add(new Label(String.valueOf(orderDetail.getQty())), 1, row);
	         add(new Label(String.valueOf(p.getPrice() * orderDetail.getQty())), 2, row);
	         row++; // Pindah ke baris berikutnya
		}
	}

}
