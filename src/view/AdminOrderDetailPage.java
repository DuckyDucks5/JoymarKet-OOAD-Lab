package view;

import java.util.ArrayList;

import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Admin;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class AdminOrderDetailPage extends VBox {  
    // Handler untuk mengelola data order
    private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    // Handler untuk mengelola data produk
    private ProductHandler productHandler = new ProductHandler();
    
    // Konstruktor halaman detail order untuk admin
    public AdminOrderDetailPage(AdminHomePage parent, Admin admin, OrderHeader oh) {
        
        setSpacing(20);
        setPadding(new Insets(25));

        // Title Order Detail
        Label title = new Label("Order Detail");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        getChildren().add(title);

        // Container untuk info order
        VBox infoBox = new VBox(8);
        infoBox.setStyle("-fx-background-color: #F3F4F6; -fx-padding: 15; -fx-background-radius: 8;");

        // Menampilkan informasi dasar order
        infoBox.getChildren().add(new Label("Order ID: " + oh.getIdOrder()));
        infoBox.getChildren().add(new Label("Status: " + oh.getStatus()));
        infoBox.getChildren().add(new Label("Date: " + oh.getOrderedAt()));

        getChildren().add(infoBox);

        // Header untuk kolom produk
        GridPane header = new GridPane();
        header.setHgap(20);
        header.setPadding(new Insets(10, 5, 10, 5));
        header.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 6;");

        applyColumnLayout(header);

        addHeaderLabel(header, "Product", 0, 200);
        addHeaderLabel(header, "Qty", 1, 80);
        addHeaderLabel(header, "Subtotal", 2, 120);

        getChildren().add(header);

        // Mengambil detail order dari database
        ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());

        // Container untuk row produk
        VBox productList = new VBox(10);

        // Loop untuk menampilkan setiap produk dalam order
        for (OrderDetail orderDetail : detail) {

            GridPane row = new GridPane();
            row.setHgap(20);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color: #F9FAFB; -fx-background-radius: 6;");

            // agar kolomnya sejajar dan konsisten
            applyColumnLayout(row); 

            // Mengambil data produk berdasarkan ID
            Product p = productHandler.getProduct(orderDetail.getIdProduct());
            
            // Menampilkan nama produk, jumlah, dan subtotal
            row.add(new Label(p.getName()), 0, 0);
            row.add(new Label(String.valueOf(orderDetail.getQty())), 1, 0);
            row.add(new Label(String.valueOf(p.getPrice() * orderDetail.getQty())), 2, 0);

            productList.getChildren().add(row);
        }

        // ScrollPane agar dapat menampilkan banyak produk
        ScrollPane scrollPane = new ScrollPane(productList);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        getChildren().add(scrollPane);
    }

    // Method untuk headerLabel agar konsisten
    private void addHeaderLabel(GridPane grid, String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setPrefWidth(width);
        grid.add(lbl, col, 0);
    }

    // Membuat kolom konsisten di header dan row
    private void applyColumnLayout(GridPane grid) {
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(200); // Product

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPrefWidth(80); // Quantity

        ColumnConstraints col3 = new ColumnConstraints();
        col3.setPrefWidth(120); // Subtotal

        grid.getColumnConstraints().addAll(col1, col2, col3);
    }
}
