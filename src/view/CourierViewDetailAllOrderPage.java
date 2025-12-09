package view;

import java.util.ArrayList;

import controller.CourierHandler;
import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Courier;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class CourierViewDetailAllOrderPage extends GridPane {

    private OrderHeader orderHeader;
    private CourierHomePage parent;
    private CourierHandler courierHandler;

    private OrderHeaderHandler orderHandler = new OrderHeaderHandler();
    private ProductHandler productHandler = new ProductHandler();

    public CourierViewDetailAllOrderPage(CourierHomePage parent, OrderHeader oh) {
        this.parent = parent;
        this.orderHeader = oh;

        // Atur padding dan jarak antar elemen GridPane
        setPadding(new Insets(20));
        setVgap(12);
        setHgap(25);

        // Judul halaman
        Label title = new Label("Order Detail");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        add(title, 0, 0);

        // Section informasi order
        Label orderInfoTitle = new Label("Order Information");
        orderInfoTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        add(orderInfoTitle, 0, 1);

        add(new Label("Order ID:"), 0, 2);
        add(new Label(oh.getIdOrder()), 1, 2);
        add(new Label("Customer ID:"), 0, 3);
        add(new Label(oh.getIdCustomer()), 1, 3);
        add(new Label("Order Date:"), 0, 4);
        add(new Label(String.valueOf(oh.getOrderedAt())), 1, 4);
        add(new Label("Status:"), 0, 5);
        add(new Label(oh.getStatus()), 1, 5);

        // Tombol untuk update status order
        add(new Label("Update Status:"), 0, 6);
        Button updateStatus = new Button("Update Status");
        add(updateStatus, 1, 6);

        // Section detail produk
        Label detailTitle = new Label("Ordered Products");
        detailTitle.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        add(detailTitle, 0, 14);

        // Header tabel produk
        Label p1 = new Label("Product");
        Label p2 = new Label("Quantity");
        Label p3 = new Label("Subtotal");
        p1.setStyle("-fx-font-weight: bold;");
        p2.setStyle("-fx-font-weight: bold;");
        p3.setStyle("-fx-font-weight: bold;");
        add(p1, 0, 15);
        add(p2, 1, 15);
        add(p3, 2, 15);

        // Ambil semua detail order dari DB
        ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());
        int row = 16;

        // Loop untuk menampilkan setiap produk dalam order
        for (OrderDetail od : detail) {
            Product p = productHandler.getProduct(od.getIdProduct());
            add(new Label(p.getName()), 0, row);
            add(new Label(String.valueOf(od.getQty())), 1, row);
            add(new Label(String.valueOf(p.getPrice() * od.getQty())), 2, row);
            row++;
        }

        // Handle tombol Update Status
        updateStatus.setOnAction(e -> {
            // Buat dialog popup untuk pilih status baru
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Status");
            dialog.setHeaderText("Update Status for Order: " + oh.getIdOrder());

            // Tombol Save & Cancel
            ButtonType saveBtnType = new ButtonType("Save", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

            // ComboBox untuk pilih status
            ComboBox<String> statusBox = new ComboBox<>();
            statusBox.getItems().addAll("Pending", "In Progress", "Delivered");
            statusBox.setValue(oh.getStatus());

            VBox box = new VBox(10);
            box.getChildren().addAll(new Label("Choose new status:"), statusBox);
            box.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(box);

            // Ambil hasil dari dialog
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveBtnType) {
                    return statusBox.getValue();
                }
                return null;
            });

            var result = dialog.showAndWait();

            // Jika status dipilih, update ke DB dan refresh page
            result.ifPresent(newStatus -> {
                String idOrder = oh.getIdOrder();
                orderHandler.updateStatus(idOrder, newStatus);
                showAlert("Success", "Order " + idOrder + " updated to " + newStatus);
                parent.setCenter(new CourierViewDetailAllOrderPage(parent, oh));
            });
        });
    }

    // Method untuk menampilkan alert informasi
    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
