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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;
import model.OrderHeader;

public class CustOrderListPage extends GridPane{
	
	private CustomerHomePage parent; // Referensi ke halaman parent untuk navigasi
	private Customer cust; // Menyimpan data customer yang sedang login
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler(); // Handler untuk mengambil data order
	private ProductHandler productHandler = new ProductHandler(); // Handler untuk mengambil data produk

	
	// Lebar kolom 
	private final int COL1 = 120;
	private final int COL2 = 160;
	private final int COL3 = 100;
	private final int COL4 = 120;
	private final int COL5 = 150;
	
	public CustOrderListPage(CustomerHomePage parent, Customer cust) {
		super();
		this.parent = parent;
		this.cust = cust;
		
        // Atur padding dan jarak antar elemen GridPane
		setPadding(new Insets(25));
        setVgap(15);
        setHgap(20);
		
        // Ambil semua order customer dari database
        ArrayList<OrderHeader> orders = orderHandler.getCustomerOrders(cust.getIdUser());
        
        // Filter order yang belum Delivered
        List<OrderHeader> notDelivered = orders.stream()
                .filter(o -> !"Delivered".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        // Jika tidak ada order, tampilkan pesan
        if(notDelivered.isEmpty()) {
        	Label emptyLbl = new Label("No Order Available");
        	emptyLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        	add(emptyLbl, 0, 0);
            return;
        }
        
        // Header tabel order
        addHeaderLabel("Order ID", 0, COL1);
        addHeaderLabel("Date", 1, COL2);
        addHeaderLabel("Amount", 2, COL3);
        addHeaderLabel("Status", 3, COL4);
        addHeaderLabel("Action", 4, COL5);
        
        int row = 1;
        
        // Loop untuk menampilkan setiap order
        for (OrderHeader orderHeader : notDelivered) {
			Label idLbl = new Label(orderHeader.getIdOrder());
			idLbl.setPrefWidth(COL1);
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			dateLbl.setPrefWidth(COL2);
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			amountLbl.setPrefWidth(COL3);
			Label statusLbl = new Label(orderHeader.getStatus());
			statusLbl.setPrefWidth(COL4);
			
			// Beri warna berbeda berdasarkan status
			switch (orderHeader.getStatus()) {
				case "Pending":
					statusLbl.setStyle("-fx-text-fill: orange; -fx-font-weight: bold;");
					break;
				case "In Progress":
					statusLbl.setStyle("-fx-text-fill: blue; -fx-font-weight: bold;");
					break;
				case "Delivered":
					statusLbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
					break;
			}
			
            // Tombol untuk melihat detail order
			Button detailBtn = new Button("Details");
			detailBtn.setPrefWidth(COL5);
			detailBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");
			
			add(idLbl, 0, row);
            add(dateLbl, 1, row);
            add(amountLbl, 2, row);
            add(statusLbl, 3, row);
            add(detailBtn, 4, row);
            
            // Event handler untuk tombol detail
            detailBtn.setOnAction(e-> {
            	parent.setCenter(new CustOrderDetailPage(parent,cust,orderHeader));
            });
            
            row++;
		}
	}
	
	// Method untuk membuat label header konsisten
	private void addHeaderLabel(String text, int col, int width) {
		Label lbl = new Label(text);
		lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		lbl.setPrefWidth(width);
		add(lbl, col, 0);
	}
}
