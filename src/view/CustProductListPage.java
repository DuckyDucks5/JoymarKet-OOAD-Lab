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
import model.Customer;
import model.Product;

public class CustProductListPage extends GridPane{
	
	private CustomerHomePage parent;
	private Customer customer;
	ProductHandler handler = new ProductHandler();
	
	public CustProductListPage(CustomerHomePage parent, Customer customer) {
		this.parent = parent;
		this.customer = customer;
		setPadding(new Insets(20));
        setVgap(10);
        setHgap(20);
        
        // Tambahkan header tabel
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
        
        // Ambil semua produk yang tersedia
        ArrayList<Product> products = handler.getAvailableProducts();
        
        int row = 1;
        for(Product p : products) {
        	Label name = new Label(p.getName());
        	Label price = new Label(String.valueOf(p.getPrice()));
        	Label stock = new Label(String.valueOf(p.getStock()));
        	Label category = new Label(p.getCategory());
        	Button addToCartBtn = new Button("Add to Cart");
        	
        	add(name, 0, row);
            add(price, 1, row);
            add(stock, 2, row);
            add(category, 3, row);
            add(addToCartBtn, 4, row);
            
            // Action tombol Add to Cart
            addToCartBtn.setOnAction(e ->{
            	TextField countField = new TextField("1");
            	Button storeBtn = new Button("Store");
            	Button cancelBtn = new Button("Cancel");
            	Label quantityLbl = new Label("Quantity");
            	
            	// Tampilkan field input jumlah dan tombol Store/Cancel
            	this.getChildren().remove(buttonLbl);
            	add(quantityLbl,4,0);
            	add(buttonLbl,5,0);
            	add(storeBtn, 5, getRowIndex(addToCartBtn));
            	this.getChildren().remove(addToCartBtn);
            	add(countField, 4,getRowIndex(storeBtn));
            	add(cancelBtn, 6, getRowIndex(countField));
            	
            	// Action tombol Store untuk menyimpan ke cart
            	storeBtn.setOnAction(ev -> {
            		String input = countField.getText();

            	    if (input == null || input.trim().isEmpty()) {
            	        showAlert("Error", "Quantity must be filled!", AlertType.ERROR);
            	        return;
            	    }

            	    int count;
            		try {
            			count = Integer.parseInt(countField.getText());
            			
            			if(count <= 0 || count > p.getStock()) {
            				showAlert("Error", "Quantity must be between 1 and available stock", AlertType.ERROR);
        					return;
            			}
            			
            			// Tambahkan item ke cart
            			CartItemHandler handler = new CartItemHandler();
            			boolean success = handler.createCartItem(customer.getIdUser(), p.getIdProduct(), count);
            			
        				if(success) {
        					showAlert("Success", "Product has been successfully added to the cart", AlertType.INFORMATION);
        				}
        				else {
        					showAlert("Error", "Failed to add the product to the cart. Please try again.", AlertType.ERROR);
        				}
        				// Refresh halaman produk
        				parent.setCenter(new CustProductListPage(parent, customer));
            		}catch(NumberFormatException e2) {
            			showAlert("Error", "Quantity must be numeric!", AlertType.ERROR);
						return;
            		}
            	});
            	
            	// Action tombol Cancel untuk membatalkan input jumlah
            	cancelBtn.setOnAction(ev -> {
            		parent.setCenter(new CustProductListPage(parent, customer));
            	});
            	
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
