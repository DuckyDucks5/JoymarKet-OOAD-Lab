package view;

import java.util.ArrayList;

import controller.CourierHandler;
import controller.OrderHeaderHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Courier;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;

public class CourierViewDetailAllOrderPage extends GridPane {

    private OrderHeader orderHeader; // menyimpan data order yang sedang ditampilkan
    private CourierHomePage parent; // halaman parent untuk navigasi
    private CourierHandler courierHandler; // handler untuk mengambil atau mengubah data courier
    private Courier courier; // menyimpan data courier yang sedang login

    private OrderHeaderHandler orderHandler = new OrderHeaderHandler(); // handler untuk order
    private ProductHandler productHandler = new ProductHandler(); // handler untuk produk

    public CourierViewDetailAllOrderPage(CourierHomePage parent, OrderHeader oh, Courier courier) {
        this.parent = parent;
        this.orderHeader = oh;
        this.courier = courier;

        // Styling utama GridPane
        setPadding(new Insets(30));
        setVgap(18);
        setHgap(25);
        setStyle("-fx-background-color: #F5F7FA;");

        // Judul halaman
        Label title = new Label("Detail Pesanan");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setStyle("-fx-text-fill: #333;");
        add(title, 0, 0);

        // Separator
        Region sep1 = new Region();
        sep1.setStyle("-fx-border-color: #DDD; -fx-border-width: 0 0 1 0;");
        sep1.setPrefHeight(1);
        add(sep1, 0, 1, 3, 1);

        // Bagian informasi order
        Label orderInfoTitle = new Label("Informasi Pesanan");
        orderInfoTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        orderInfoTitle.setStyle("-fx-text-fill: #444;");
        add(orderInfoTitle, 0, 2);

        // Grid internal untuk info order
        GridPane orderInfoGrid = new GridPane();
        orderInfoGrid.setVgap(10);
        orderInfoGrid.setHgap(20);
        orderInfoGrid.setPadding(new Insets(10, 0, 15, 0));

        // Menambahkan label info order
        orderInfoGrid.add(new Label("ID Pesanan:"), 0, 0);
        orderInfoGrid.add(new Label(oh.getIdOrder()), 1, 0);
        orderInfoGrid.add(new Label("ID Customer:"), 0, 1);
        orderInfoGrid.add(new Label(oh.getIdCustomer()), 1, 1);
        orderInfoGrid.add(new Label("Tanggal Pesan:"), 0, 2);
        orderInfoGrid.add(new Label(String.valueOf(oh.getOrderedAt())), 1, 2);
        orderInfoGrid.add(new Label("Status:"), 0, 3);
        orderInfoGrid.add(new Label(oh.getStatus()), 1, 3);

        // Tombol update status ditempatkan di bawah label Status
        Button updateStatus = new Button("Update Status");
        updateStatus.setStyle(
            "-fx-background-color: #4C84FF; -fx-text-fill: white; -fx-font-weight: bold; "
          + "-fx-padding: 7 16; -fx-background-radius: 8;"
        );
        orderInfoGrid.add(updateStatus, 1, 4); // 1,4: tepat di bawah Status

        add(orderInfoGrid, 0, 3);

        // Separator informasi kurir
        Region sep2 = new Region();
        sep2.setStyle("-fx-border-color: #DDD; -fx-border-width: 0 0 1 0;");
        sep2.setPrefHeight(1);
        add(sep2, 0, 4, 3, 1);

        // Bagian informasi kurir
        Label courierInfoTitle = new Label("Informasi Kurir");
        courierInfoTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        courierInfoTitle.setStyle("-fx-text-fill: #444;");
        add(courierInfoTitle, 0, 5);

        GridPane courierGrid = new GridPane();
        courierGrid.setVgap(10);
        courierGrid.setHgap(20);
        courierGrid.setPadding(new Insets(10, 0, 15, 0));

        // Menampilkan data kurir
        courierGrid.add(new Label("Nama Kurir: "), 0, 0);
        courierGrid.add(new Label(courier.getFullname()), 1, 0);
        courierGrid.add(new Label("Email Kurir: "), 0, 1);
        courierGrid.add(new Label(courier.getEmail()), 1, 1);
        courierGrid.add(new Label("Telepon Kurir: "), 0, 2);
        courierGrid.add(new Label(courier.getPhone()), 1, 2);
        courierGrid.add(new Label("Tipe Kendaraan: "), 0, 3);
        courierGrid.add(new Label(courier.getVehicleType()), 1, 3);
        courierGrid.add(new Label("Plat Kendaraan: "), 0, 4);
        courierGrid.add(new Label(courier.getVehiclePlate()), 1, 4);

        add(courierGrid, 0, 6);

        // Separator produk
        Region sep3 = new Region();
        sep3.setStyle("-fx-border-color: #DDD; -fx-border-width: 0 0 1 0;");
        sep3.setPrefHeight(1);
        add(sep3, 0, 7, 3, 1);

        // Bagian produk yang dipesan
        Label detailTitle = new Label("Produk yang Dipesan");
        detailTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 13));
        detailTitle.setStyle("-fx-text-fill: #444;");
        add(detailTitle, 0, 8);

        // Header tabel produk
        Label p1 = new Label("Produk");
        Label p2 = new Label("Jumlah");
        Label p3 = new Label("Subtotal");
        p1.setStyle("-fx-font-weight: bold;");
        p2.setStyle("-fx-font-weight: bold;");
        p3.setStyle("-fx-font-weight: bold;");
        add(p1, 0, 9);
        add(p2, 1, 9);
        add(p3, 2, 9);

        // Menampilkan semua produk dalam order
        ArrayList<OrderDetail> detail = orderHandler.getOrderDetails(oh.getIdOrder());
        int row = 10;
        for (OrderDetail od : detail) {
            Product p = productHandler.getProduct(od.getIdProduct());

            Label prodName = new Label(p.getName());
            Label qty = new Label(String.valueOf(od.getQty()));
            Label subtotal = new Label(String.valueOf(p.getPrice() * od.getQty()));

            prodName.setStyle("-fx-padding: 1 0;");
            qty.setStyle("-fx-padding: 1 0;");
            subtotal.setStyle("-fx-padding: 1 0;");

            add(prodName, 0, row);
            add(qty, 1, row);
            add(subtotal, 2, row);
            row++;
        }

        // Event tombol update status
        updateStatus.setOnAction(e -> {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Update Status");
            dialog.setHeaderText("Update Status untuk Pesanan: " + oh.getIdOrder());

            ButtonType saveBtnType = new ButtonType("Simpan", ButtonType.OK.getButtonData());
            dialog.getDialogPane().getButtonTypes().addAll(saveBtnType, ButtonType.CANCEL);

            ComboBox<String> statusBox = new ComboBox<>();
            statusBox.getItems().addAll("Pending", "In Progress", "Delivered");
            statusBox.setValue(oh.getStatus());

            VBox box = new VBox(10);
            box.setPadding(new Insets(10));
            box.getChildren().addAll(new Label("Pilih status baru:"), statusBox);
            dialog.getDialogPane().setContent(box);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == saveBtnType) {
                    return statusBox.getValue();
                }
                return null;
            });

            var result = dialog.showAndWait();

            // Jika ada status dipilih, update di database dan refresh halaman
            result.ifPresent(newStatus -> {
                String idOrder = oh.getIdOrder();
                orderHandler.updateStatus(idOrder, newStatus);
                showAlert("Sukses", "Pesanan " + idOrder + " telah diupdate ke status " + newStatus);
                parent.setCenter(new CourierViewAllOrderPage(parent, courier));
            });
        });
    }

    // Method untuk menampilkan alert
    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setHeaderText(title);
        a.setContentText(message);
        a.showAndWait();
    }
}
