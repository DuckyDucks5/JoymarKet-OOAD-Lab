package view;

import java.util.ArrayList;

import controller.ProductHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import model.Product;

public class ProductListPage extends GridPane{

    // Handler untuk mengelola data produk
	ProductHandler handler = new ProductHandler();
	
	public ProductListPage() {
        // Atur padding dan jarak antar elemen GridPane
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
        
        // Header tabel
        Label nameLbl = new Label("Name");
        Label priceLbl = new Label("Price");
        Label stockLbl = new Label("Stock");
        Label categoryLbl = new Label("Category");
        Label buttonLbl = new Label("Button");

        add(nameLbl, 0, 0);
        add(priceLbl, 1, 0);
        add(stockLbl, 2, 0);
        add(categoryLbl, 3, 0);
        add(buttonLbl, 4, 0);
        
        // Ambil semua produk dari database
        ArrayList<Product> products = handler.getAllProducts();

        int row = 1;
        // Loop untuk menampilkan setiap produk
        for (Product p : products) {
        	
        	Label name = new Label(p.getName());
        	Label price = new Label(String.valueOf(p.getPrice()));
        	Label stock = new Label(String.valueOf(p.getStock()));
        	Label category = new Label(p.getCategory());
        	Button updateBtn = new Button("Update");
        	
            // Event handler untuk tombol Update
        	updateBtn.setOnAction(e -> {
        		// Ubah label stock menjadi TextField agar bisa diedit
        		TextField stockField = new TextField(stock.getText());
        		this.getChildren().remove(stock);
        		add(stockField, 2, getRowIndex(updateBtn));
        		
        		// Tambahkan tombol Confirm untuk menyimpan perubahan
        		Button confirmBtn = new Button("Confirm");
        		this.getChildren().remove(updateBtn);
        		add(confirmBtn, 4, getRowIndex(stockField));
        		
        		confirmBtn.setOnAction(ev -> {
        			try {
        				int newStock = Integer.parseInt(stockField.getText());
        				
        				if (newStock < 0) {
        					showAlert("Error", "Stock tidak boleh negatif!", AlertType.ERROR);
        					return;
        				}						
        				boolean success = handler.editProductStock(p.getIdProduct(), newStock);
        				
        				if(success) {
        					showAlert("Success", "Stock berhasil diperbarui!", AlertType.INFORMATION);
        				}
        				else {
        					showAlert("Error", "Gagal memperbarui stock!", AlertType.ERROR);
        				}
                        // Refresh halaman setelah update
        				getScene().setRoot(new ProductListPage());
					} catch (NumberFormatException e2) {
						showAlert("Error", "Stock harus berupa angka valid!", AlertType.ERROR);
						return;
					}
        		});
        	});
        	
            // Tambahkan data produk ke GridPane
            add(name, 0, row);
            add(price, 1, row);
            add(stock, 2, row);
            add(category, 3, row);
            add(updateBtn, 4, row);
            row++;
        }
	}
	
    // Method untuk menampilkan alert
	private void showAlert(String title, String message, AlertType type) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(message);
		alert.showAndWait();
	}
}
