package view;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import controller.CourierHandler;
import controller.DeliveryHandler;
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
import model.Admin;
import model.Courier;
import model.Customer;
import model.OrderHeader;

public class AdminOrderListPage extends VBox {

    private AdminHomePage parent; 
    private Admin admin; // Menyimpan data admin yang sedang login
    private OrderHeaderHandler orderHandler = new OrderHeaderHandler(); // Handler untuk mengambil dan mengubah data order
    private ProductHandler productHandler = new ProductHandler(); // Handler untuk mengambil dan mengubah data produk
    private DeliveryHandler deliveryHandler = new DeliveryHandler(); //Handler untuk mengambil dan mengubah data delivery
    private CourierHandler courierHanlder = new CourierHandler(); // Handler untuk mengambil dan mengubah data courier

    // Lebar kolom agar konsisten 
    private final int COL1 = 100;
    private final int COL2 = 100;
    private final int COL3 = 150;
    private final int COL4 = 100;
    private final int COL5 = 120;
    private final int COL6 = 110;
    private final int COL7 = 100;
    private final int COL8 = 120;

    public AdminOrderListPage(AdminHomePage parent, Admin admin) {
        super();
        this.parent = parent;
        this.admin = admin;

        // Layout utama
        setSpacing(20);
        setPadding(new Insets(25));

        // Labl admin order list
        Label title = new Label("Admin Order List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        getChildren().add(title);

        // Ambil semua order dari customer
        ArrayList<OrderHeader> orders = orderHandler.getAllCustomerOrders();

        // Jika tidak ada order, tampilkan pesan
        if (orders.isEmpty()) {
            Label emptyLbl = new Label("No Order Available");
            emptyLbl.setFont(Font.font("Arial", FontWeight.BOLD, 16));
            getChildren().add(emptyLbl);
            return;
        }

        // Header tabel
        GridPane header = new GridPane();
        header.setHgap(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color:#E5E7EB; -fx-background-radius:6;");

        addHeader(header, "Order ID", 0, COL1);
        addHeader(header, "Customer ID", 1, COL2);
        addHeader(header, "Date", 2, COL3);
        addHeader(header, "Amount", 3, COL4);
        addHeader(header, "Status", 4, COL5);
        addHeader(header, "Action", 5, COL6);
        addHeader(header, "Courier", 6, COL7);
        addHeader(header, "Action", 7, COL8);

        getChildren().add(header);

        // Looping untuk menampilkan setiap order
        for (OrderHeader orderHeader : orders) {

            GridPane row = new GridPane();
            row.setHgap(10);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color:#F9FAFB; -fx-background-radius:6;");

            Label idLbl = createCell(orderHeader.getIdOrder(), COL1);
            Label custLbl = createCell(orderHeader.getIdCustomer(), COL2);
            Label dateLbl = createCell(orderHeader.getOrderedAt().toString(), COL3);
            Label amountLbl = createCell(String.valueOf(orderHeader.getTotalAmount()), COL4);

            Label statusLbl = new Label(orderHeader.getStatus());
            statusLbl.setPrefWidth(COL5);

            // Warnai status sesuai dengan kondisinya
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

            // Ambil courier yang ditugaskan ke order ini
            String idCourier = courierHanlder.getCourier(idLbl.getText());
            Label courierLbl = createCell(idCourier, COL7);

            // Tombol Detail untuk mengetahui detail order
            Button detailBtn = new Button("Details");
            detailBtn.setPrefWidth(COL6);
            detailBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");

            // Tombol assign courier ke order
            Button assignCourierBtn = new Button("Assign Courier");
            assignCourierBtn.setPrefWidth(COL8);
            assignCourierBtn.setStyle("-fx-background-color:#059669; -fx-text-fill:white; -fx-background-radius:5;");

            // Tambahkan elemen ke row
            row.add(idLbl, 0, 0);
            row.add(custLbl, 1, 0);
            row.add(dateLbl, 2, 0);
            row.add(amountLbl, 3, 0);
            row.add(statusLbl, 4, 0);
            row.add(detailBtn, 5, 0);
            row.add(courierLbl, 6, 0);

            // Tampilkan tombol assign courier hanya jika status Pending
            if (statusLbl.getText().equals("Pending")) {
                row.add(assignCourierBtn, 7, 0);
            }

            // Action untuk tombol Detail
            detailBtn.setOnAction(e -> {
                parent.setCenter(new AdminOrderDetailPage(parent, admin, orderHeader));
            });

            // Action untuk tombol Assign Courier
            assignCourierBtn.setOnAction(e -> {

                // Handler mengambil semua courier
                CourierHandler courierHandler = new CourierHandler();
                ArrayList<Courier> couriers = courierHandler.getAllCouriers();

                // Jika tidak ada courier, tampilkan alert
                if (couriers.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "No couriers available.");
                    alert.show();
                    return;
                }

                // Buat dialog pemilihan courier
                Dialog<Courier> dialog = new Dialog<>();
                dialog.setTitle("Assign Courier");
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                ComboBox<Courier> courierBox = new ComboBox<>();
                courierBox.getItems().addAll(couriers);
                courierBox.setPromptText("Select Courier");

                VBox vbox = new VBox(10, new Label("Choose Courier: "), courierBox);
                vbox.setPadding(new Insets(10));

                dialog.getDialogPane().setContent(vbox);
                dialog.setResultConverter(button -> button == ButtonType.OK ? courierBox.getValue() : null);

                // Jika courier dipilih, lakukan assignment
                dialog.showAndWait().ifPresent(selectedCourier -> {
                    if (selectedCourier != null) {
                        deliveryHandler.assignCourierToOrder(orderHeader.getIdOrder(), selectedCourier.getIdUser());
                        courierHandler.assignCourier(orderHeader.getIdOrder());

                        Alert done = new Alert(Alert.AlertType.INFORMATION,
                                "Courier " + selectedCourier.getFullname() + " assigned!");
                        done.show();

                        // Refresh halaman order
                        parent.setCenter(new AdminOrderListPage(parent, admin));
                    }
                });
            });

            getChildren().add(row); // Tambah row ke VBox
        }
    }

    // Method membuat header label
    private void addHeader(GridPane grid, String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setPrefWidth(width);
        grid.add(lbl, col, 0);
    }

    // Method membuat cell
    private Label createCell(String text, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", 13));
        lbl.setPrefWidth(width);
        return lbl;
    }
}
