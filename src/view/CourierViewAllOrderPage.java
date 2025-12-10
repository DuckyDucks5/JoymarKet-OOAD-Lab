package view;

import java.util.ArrayList;
import controller.DeliveryHandler;
import controller.OrderHeaderHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Courier;
import model.OrderHeader;

public class CourierViewAllOrderPage extends VBox {

	private Courier courier; // Menyimpan data courier yang sedang login
	private CourierHomePage parent; // Referensi ke halaman parent untuk navigasi

	private DeliveryHandler deliveryHandler = new DeliveryHandler(); // Handler untuk mengambil data delivery

	// Lebar kolom konsisten agar tabel sejajar
	private final int COL1 = 120; 
	private final int COL2 = 160; 
	private final int COL3 = 100;  
	private final int COL4 = 120; 
	private final int COL5 = 150; 

	public CourierViewAllOrderPage(CourierHomePage parent, Courier courier) {
		this.parent = parent;
		this.courier = courier;

		// Set layout utama VBox
		setSpacing(20);
		setPadding(new Insets(25));
		Label title = new Label("Courier Order List");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
		getChildren().add(title);

		// Ambil semua order yang terassign ke courier ini
		ArrayList<OrderHeader> orders = deliveryHandler.getAllDeliveries(courier.getIdUser());

		// Bila tidak ada order, tampilkan pesan
		if (orders.isEmpty()) {
			Label empty = new Label("No Order Available");
			empty.setFont(Font.font("Arial", FontWeight.BOLD, 16));
			getChildren().add(empty);
			return;
		}

		// Membuat header tabel menggunakan GridPane
		GridPane header = new GridPane();
		header.setHgap(10);
		header.setPadding(new Insets(5, 5, 10, 5));

		addHeaderLabel(header, "Order ID", 0, COL1);
		addHeaderLabel(header, "Date", 1, COL2);
		addHeaderLabel(header, "Amount", 2, COL3);
		addHeaderLabel(header, "Status", 3, COL4);
		addHeaderLabel(header, "Action", 4, COL5);

		header.setStyle("-fx-background-color: #E5E7EB; -fx-padding: 10; -fx-background-radius: 6;");
		getChildren().add(header);

		// Looping setiap order dan render row tabel menggunakan GridPane
		for (OrderHeader oh : orders) {

			// Container row untuk tiap order
			GridPane row = new GridPane();
			row.setHgap(10);
			row.setPadding(new Insets(8));
			row.setStyle("-fx-background-color: #F9FAFB; -fx-background-radius: 6;");

			// Label ID Order
			Label idLbl = new Label(oh.getIdOrder());
			idLbl.setPrefWidth(COL1);
			row.add(idLbl, 0, 0);

			// Label tanggal order
			Label dateLbl = new Label(oh.getOrderedAt().toString());
			dateLbl.setPrefWidth(COL2);
			row.add(dateLbl, 1, 0);

			// Label total amount
			Label amountLbl = new Label(String.valueOf(oh.getTotalAmount()));
			amountLbl.setPrefWidth(COL3);
			row.add(amountLbl, 2, 0);

			// Label status dengan warna berbeda berdasarkan status
			Label statusLbl = new Label(oh.getStatus());
			statusLbl.setPrefWidth(COL4);

			switch (oh.getStatus()) {
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
			row.add(statusLbl, 3, 0);

			// Tombol untuk melihat detail order
			Button detailBtn = new Button("View Detail");
			detailBtn.setPrefWidth(COL5);
			detailBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");

			// Event handler tombol detail
			detailBtn.setOnAction(e -> {
				// Navigasi ke halaman detail order dengan scroll pane
				ScrollPane scroll = new ScrollPane(new CourierViewDetailAllOrderPage(parent, oh, courier));
				scroll.setFitToWidth(true);
				scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
				scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

				parent.setCenter(scroll);
			});

			row.add(detailBtn, 4, 0);

			getChildren().add(row); // Tambahkan row ke VBox
		}
	}

	// Method untuk membuat label header dengan format konsisten
	private void addHeaderLabel(GridPane grid, String text, int col, int width) {
		Label lbl = new Label(text);
		lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		lbl.setPrefWidth(width);
		grid.add(lbl, col, 0);
	}
}
