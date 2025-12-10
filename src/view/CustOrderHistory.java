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

public class CustOrderHistory extends GridPane {

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

	public CustOrderHistory(CustomerHomePage parent, Customer cust) {
		super();
		this.parent = parent;
		this.cust = cust;

		// Atur layout GridPane
		setPadding(new Insets(25));
        setVgap(15);
        setHgap(20);

        // Ambil semua order customer
        ArrayList<OrderHeader> orders = orderHandler.getCustomerOrders(cust.getIdUser());

        // Filter hanya order yang sudah Delivered
        List<OrderHeader> deliveredOrders = orders.stream()
                .filter(o -> "Delivered".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        // Jika tidak ada order Delivered, tampilkan pesan
        if (deliveredOrders.isEmpty()) {
        	Label emptyLbl = new Label("No Order History");
        	emptyLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        	add(emptyLbl, 0, 0);
            return;
        }

        // Tambahkan header tabel
        addHeaderLabel("Order ID", 0, COL1);
        addHeaderLabel("Date", 1, COL2);
        addHeaderLabel("Amount", 2, COL3);
        addHeaderLabel("Status", 3, COL4);
        addHeaderLabel("Action", 4, COL5);

        int row = 1;
        for (OrderHeader orderHeader : orders) {
        	// Filter hanya order dengan status Delivered
            if (!"Delivered".equalsIgnoreCase(orderHeader.getStatus())) {
                continue;
            }

            // Buat label untuk tiap kolom
        	Label idLbl = new Label(orderHeader.getIdOrder());
        	idLbl.setPrefWidth(COL1);
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			dateLbl.setPrefWidth(COL2);
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			amountLbl.setPrefWidth(COL3);
			Label statusLbl = new Label(orderHeader.getStatus());
			statusLbl.setPrefWidth(COL4);
			
			// Beri warna status Delivered
			statusLbl.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");

			// Tombol untuk melihat detail order
			Button detailBtn = new Button("Details");
			detailBtn.setPrefWidth(COL5);
			detailBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");

			// Tambahkan ke GridPane
			add(idLbl, 0, row);
            add(dateLbl, 1, row);
            add(amountLbl, 2, row);
            add(statusLbl, 3, row);
            add(detailBtn, 4, row);

            // Event handler tombol detail
            detailBtn.setOnAction(e -> {
            	parent.setCenter(new CustOrderDetailPage(parent, cust, orderHeader));
            });

            row++; // Pindah ke baris berikutnya
		}
	}

	// Method untuk membuat header label konsisten
	private void addHeaderLabel(String text, int col, int width) {
		Label lbl = new Label(text);
		lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		lbl.setPrefWidth(width);
		add(lbl, col, 0);
	}
}
