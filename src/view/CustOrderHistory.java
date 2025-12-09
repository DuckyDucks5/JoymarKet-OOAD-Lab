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

public class CustOrderHistory extends GridPane {

	private CustomerHomePage parent; // Referensi ke halaman parent untuk navigasi
	private Customer cust; // Menyimpan data customer yang sedang login
	private OrderHeaderHandler orderHandler = new OrderHeaderHandler(); // Handler untuk mengambil data order
	private ProductHandler productHandler = new ProductHandler(); // Handler untuk mengambil data produk

	public CustOrderHistory(CustomerHomePage parent, Customer cust) {
		super();
		this.parent = parent;
		this.cust = cust;

		// Atur layout GridPane
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);

        // Ambil semua order customer
        ArrayList<OrderHeader> orders = orderHandler.getCustomerOrders(cust.getIdUser());

        // Filter hanya order yang sudah Delivered
        List<OrderHeader> deliveredOrders = orders.stream()
                .filter(o -> "Delivered".equalsIgnoreCase(o.getStatus()))
                .collect(Collectors.toList());

        // Jika tidak ada order Delivered, tampilkan pesan
        if (deliveredOrders.isEmpty()) {
        	add(new Label("No Order History"), 0, 0);
            return;
        }

        // Tambahkan header tabel
        add(new Label("Order ID"), 0, 0);
        add(new Label("Date"), 1, 0);
        add(new Label("Amount"), 2, 0);
        add(new Label("Status"), 3, 0);
        add(new Label("Action"), 4, 0);

        int row = 1;
        for (OrderHeader orderHeader : orders) {
        	// Filter hanya order dengan status Delivered
            if (!"Delivered".equalsIgnoreCase(orderHeader.getStatus())) {
                continue;
            }

            // Buat label untuk tiap kolom
        	Label idLbl = new Label(orderHeader.getIdOrder());
			Label dateLbl = new Label(orderHeader.getOrderedAt().toString());
			Label amountLbl = new Label(String.valueOf(orderHeader.getTotalAmount()));
			Label statusLbl = new Label(orderHeader.getStatus());

			// Tombol untuk melihat detail order
			Button detailBtn = new Button("Details");

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
}
