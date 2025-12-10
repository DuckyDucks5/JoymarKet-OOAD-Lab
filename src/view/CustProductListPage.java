package view;

import java.util.ArrayList;
import controller.CartItemHandler;
import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Customer;
import model.Product;

public class CustProductListPage extends GridPane {

    private CustomerHomePage parent; // Referensi ke halaman parent untuk navigasi
    private Customer customer; // menyimpan data customer yang sedang login
    ProductHandler handler = new ProductHandler(); //handler untuk mengambil data produk

    // Lebar kolom
    private final int COL1 = 150;
    private final int COL2 = 100;
    private final int COL3 = 80;
    private final int COL4 = 120;
    private final int COL5 = 150;

    public CustProductListPage(CustomerHomePage parent, Customer customer) {
        this.parent = parent;
        this.customer = customer;

        // Layout utama
        setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);

        // Header tabel dengan font bold
        Label nameLbl = new Label("Name");
        nameLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        nameLbl.setPrefWidth(COL1);

        Label priceLbl = new Label("Price");
        priceLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        priceLbl.setPrefWidth(COL2);

        Label stockLbl = new Label("Stock");
        stockLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        stockLbl.setPrefWidth(COL3);

        Label categoryLbl = new Label("Category");
        categoryLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        categoryLbl.setPrefWidth(COL4);

        Label buttonLbl = new Label("Button");
        buttonLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        buttonLbl.setPrefWidth(COL5);

        add(nameLbl, 0, 0);
        add(priceLbl, 1, 0);
        add(stockLbl, 2, 0);
        add(categoryLbl, 3, 0);
        add(buttonLbl, 4, 0);

        // Ambil semua produk yang tersedia
        ArrayList<Product> products = handler.getAvailableProducts();

        int row = 1;
        for (Product p : products) {

            // Label tiap produk
            Label name = new Label(p.getName());
            name.setPrefWidth(COL1);
            Label price = new Label(String.valueOf(p.getPrice()));
            price.setPrefWidth(COL2);
            Label stock = new Label(String.valueOf(p.getStock()));
            stock.setPrefWidth(COL3);
            Label category = new Label(p.getCategory());
            category.setPrefWidth(COL4);

            Button addToCartBtn = new Button("Add to Cart");
            addToCartBtn.setPrefWidth(COL5);
            addToCartBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");

            // Tambahkan ke tabel
            add(name, 0, row);
            add(price, 1, row);
            add(stock, 2, row);
            add(category, 3, row);
            add(addToCartBtn, 4, row);

            // Action tombol Add to Cart
            addToCartBtn.setOnAction(e -> {

                TextField countField = new TextField("1");
                countField.setPrefWidth(60);

                Button storeBtn = new Button("Store");
                storeBtn.setStyle("-fx-background-color:#2563EB; -fx-text-fill:white; -fx-background-radius:5;");

                Button cancelBtn = new Button("Cancel");
                cancelBtn.setStyle("-fx-background-color:#9CA3AF; -fx-text-fill:white; -fx-background-radius:5;");

                Label quantityLbl = new Label("Quantity");
                quantityLbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
                quantityLbl.setPrefWidth(80);

                // Tampilkan field input jumlah
                this.getChildren().remove(buttonLbl);
                add(quantityLbl, 4, 0);
                add(buttonLbl, 5, 0);

                add(storeBtn, 5, getRowIndex(addToCartBtn));
                this.getChildren().remove(addToCartBtn);
                add(countField, 4, getRowIndex(storeBtn));
                add(cancelBtn, 6, getRowIndex(countField));

                // Action tombol Store untuk menyimpan ke cart
                storeBtn.setOnAction(ev -> {

                    String input = countField.getText();

                    if (input == null || input.trim().isEmpty()) {
                        showAlert("Error", "Quantity must be filled!", AlertType.ERROR);
                        return;
                    }

                    try {
                        int count = Integer.parseInt(countField.getText());

                        if (count <= 0 || count > p.getStock()) {
                            showAlert("Error", "Quantity must be between 1 and available stock", AlertType.ERROR);
                            return;
                        }

                        // Tambahkan item ke cart
                        CartItemHandler handler = new CartItemHandler();
                        boolean success = handler.createCartItem(customer.getIdUser(), p.getIdProduct(), count);

                        if (success) {
                            showAlert("Success", "Product has been successfully added to the cart", AlertType.INFORMATION);
                        } else {
                            showAlert("Error", "Failed to add the product to the cart. Please try again.", AlertType.ERROR);
                        }

                        // Refresh halaman produk
                        parent.setCenter(new CustProductListPage(parent, customer));

                    } catch (NumberFormatException e2) {
                        showAlert("Error", "Quantity must be numeric!", AlertType.ERROR);
                    }
                });

                // Action tombol Cancel
                cancelBtn.setOnAction(ev -> parent.setCenter(new CustProductListPage(parent, customer)));
            });

            row++;
        }
    }

    // Method untuk menampilkan Alert
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
