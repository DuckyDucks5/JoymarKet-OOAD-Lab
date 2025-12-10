package view;

import java.util.ArrayList;

import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.Product;

public class ProductListPage extends VBox {

    private ProductHandler handler = new ProductHandler(); // handler untuk mengambil data produk

    // Lebar kolom
    private final int COL1 = 180;
    private final int COL2 = 120;
    private final int COL3 = 100;
    private final int COL4 = 150;
    private final int COL5 = 150;

    public ProductListPage() {

        setSpacing(20);
        setPadding(new Insets(25));

        // Title product list
        Label title = new Label("Product List");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        getChildren().add(title);

        // Header tabel
        GridPane header = new GridPane();
        header.setHgap(10);
        header.setPadding(new Insets(10));
        header.setStyle("-fx-background-color: #E5E7EB; -fx-background-radius: 6;");

        addHeaderLabel(header, "Name", 0, COL1);
        addHeaderLabel(header, "Price", 1, COL2);
        addHeaderLabel(header, "Stock", 2, COL3);
        addHeaderLabel(header, "Category", 3, COL4);
        addHeaderLabel(header, "Action", 4, COL5);

        getChildren().add(header);

        // Ambil semua produk
        ArrayList<Product> products = handler.getAllProducts();

        // Loop setiap product & render row
        for (Product p : products) {

            GridPane row = new GridPane();
            row.setHgap(10);
            row.setPadding(new Insets(8));
            row.setStyle("-fx-background-color: #F9FAFB; -fx-background-radius: 6;");

            Label name = new Label(p.getName());
            name.setPrefWidth(COL1);

            Label price = new Label(String.valueOf(p.getPrice()));
            price.setPrefWidth(COL2);

            Label stock = new Label(String.valueOf(p.getStock()));
            stock.setPrefWidth(COL3);

            Label category = new Label(p.getCategory());
            category.setPrefWidth(COL4);

            Button updateBtn = new Button("Update");
            updateBtn.setPrefWidth(COL5);
            updateBtn.setStyle(
                "-fx-background-color: #2563EB; -fx-text-fill: white; -fx-background-radius: 5;"
            );

            // Event Update
            updateBtn.setOnAction(e -> {

                TextField stockField = new TextField(stock.getText());
                stockField.setPrefWidth(COL3);
                stockField.setStyle(
                    "-fx-background-color: white; -fx-border-color: #888; -fx-background-radius: 4;"
                );

                row.getChildren().remove(stock);
                row.add(stockField, 2, 0);

                Button confirmBtn = new Button("Save");
                confirmBtn.setPrefWidth(COL5);
                confirmBtn.setStyle(
                    "-fx-background-color: #16A34A; -fx-text-fill: white; -fx-background-radius: 5;"
                );

                row.getChildren().remove(updateBtn);
                row.add(confirmBtn, 4, 0);

                confirmBtn.setOnAction(ev -> {
                    try {
                        int newStock = Integer.parseInt(stockField.getText());

                        if (newStock < 0) {
                            showAlert("Error", "Stock tidak boleh negatif!", AlertType.ERROR);
                            return;
                        }

                        boolean success = handler.editProductStock(p.getIdProduct(), newStock);

                        if (success) {
                            showAlert("Success", "Stock berhasil diperbarui!", AlertType.INFORMATION);
                        } else {
                            showAlert("Error", "Gagal memperbarui stock!", AlertType.ERROR);
                        }

                        // Refresh page
                        ((BorderPane) getScene().getRoot()).setCenter(new ProductListPage());

                    } catch (NumberFormatException ex) {
                        showAlert("Error", "Stock harus berupa angka valid!", AlertType.ERROR);
                    }
                });

            });

            // Masukkan row ke GridPane
            row.add(name, 0, 0);
            row.add(price, 1, 0);
            row.add(stock, 2, 0);
            row.add(category, 3, 0);
            row.add(updateBtn, 4, 0);

            getChildren().add(row);

        }
    }

    // Method untuk membuat header label dengan format konsisten
    private void addHeaderLabel(GridPane grid, String text, int col, int width) {
        Label lbl = new Label(text);
        lbl.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        lbl.setPrefWidth(width);
        grid.add(lbl, col, 0);
    }

    // Method untuk menampilkan Alert
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setHeaderText(null);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
