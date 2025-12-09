package view;

import java.util.ArrayList;

import controller.CartItemHandler;
import controller.CustomerHandler;
import controller.OrderHeaderHandler;
import controller.ProductHandler;
import controller.PromoHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import model.CartItem;
import model.Customer;
import model.Product;
import model.Promo;

public class CustCartPage extends GridPane {

	private CustomerHomePage parent;
	private Customer customer;
	private CartItemHandler cartItemHandler;
	private ProductHandler productHandler;
	private PromoHandler promoHandler;
	private OrderHeaderHandler orderHeaderHandler;
	private CustomerHandler customerHandler;
	
	private double grandTotalToPay; 
    private Promo appliedPromo = null;
    private Label afterDiscLbl;
    private Label grandTotalLbl;
    private Label descPromoLbl;
	
	public CustCartPage(CustomerHomePage parent, Customer customer) {
		this.parent = parent;
		this.customer = customer;
		cartItemHandler = new CartItemHandler();
        productHandler = new ProductHandler();
        promoHandler = new PromoHandler();
        orderHeaderHandler = new OrderHeaderHandler();
        customerHandler = new CustomerHandler();
		
		setPadding(new Insets(20));
		setVgap(10);
		setHgap(20);
		
		// Ambil semua item di cart customer
		ArrayList<CartItem> items = cartItemHandler.getCartItems(customer.getIdUser());
        
        // Jika cart kosong, tampilkan pesan
        if (items == null || items.isEmpty()) {
            Label emptyLbl = new Label("No Cart Item Available");
            add(emptyLbl,0,0);
            return;
        }
		
        // Tambahkan header tabel
		Label nameLbl = new Label("Name");
        Label priceLbl = new Label("Price");
        Label quantityLbl = new Label("Quantity");
        Label subtotalLbl = new Label("Subtotal");
        Label buttonLbl = new Label("Button");
        Label codePromoLbl = new Label("Enter Your Code Promo: ");
        TextField codePromoTF = new TextField();
        Button enterCodePromoBtn = new Button("Enter");
        Button checkOutBtn = new Button("Check Out");
        
        add(nameLbl, 0, 0);
        add(priceLbl, 1, 0);
        add(quantityLbl, 2, 0);
        add(subtotalLbl, 3, 0);
        add(buttonLbl, 4, 0);
        
        int row = 1;
        double totalOrder = 0;
        
        // Loop untuk menampilkan setiap item di cart
        for(CartItem item : items) {
        	Product p = productHandler.getProduct(item.getIdProduct());
        	Label name = new Label(p.getName());
        	Label price = new Label(String.valueOf(p.getPrice()));
        	Label quantity = new Label(String.valueOf(item.getCount()));
        	double subtotal = p.getPrice() * item.getCount();
        	Label subTotal = new Label(String.valueOf(subtotal));
        	Button editBtn = new Button("Edit");
        	Button removeBtn = new Button("Remove");
        	
        	totalOrder += subtotal;
        	
        	add(name,0,row);
        	add(price,1,row);
        	add(quantity,2,row);
        	add(subTotal,3,row);
        	add(editBtn,4,row);
        	add(removeBtn,5,row);
        	
        	// Action tombol Edit untuk update jumlah item
        	editBtn.setOnAction(e -> {
        		TextField countField = new TextField(quantity.getText());
            	Button updateBtn = new Button("Update");
            	Button cancelBtn = new Button("Cancel");
                Label currentStockLbl = new Label("Current Stock: " + p.getStock());
            	
            	// Replace label quantity dan tombol dengan field edit
            	this.getChildren().remove(quantity);
            	add(countField,2,getRowIndex(editBtn));
            	this.getChildren().remove(buttonLbl);
            	this.getChildren().remove(subtotalLbl);
            	this.getChildren().remove(subTotal);
            	this.getChildren().remove(editBtn);
            	this.getChildren().remove(removeBtn);
            	add(buttonLbl,3,0);
            	add(updateBtn, 3,getRowIndex(countField));
            	add(cancelBtn,4,getRowIndex(updateBtn));
            	add(currentStockLbl,5,getRowIndex(cancelBtn));
            	
            	// Action tombol Update untuk simpan perubahan jumlah
            	updateBtn.setOnAction(ev -> {
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
            		
            			boolean success = cartItemHandler.editCartItem(item.getIdCustomer(),item.getIdProduct(), count);
            			
        				if(success) {
        					showAlert("Success", "Cart has been successfully updated", AlertType.INFORMATION);
        				}
        				else {
        					showAlert("Error", "Failed to update the cart. Please try again.", AlertType.ERROR);
        				}
        				// Refresh halaman cart
        				parent.setCenter(new CustCartPage(parent, customer));
            		}catch(NumberFormatException e2) {
            			showAlert("Error", "Quantity must be numeric!", AlertType.ERROR);
						return;
            		}
            	});
            	
            	// Action tombol Cancel untuk membatalkan edit
            	cancelBtn.setOnAction(ev -> {
            		parent.setCenter(new CustCartPage(parent, customer));
            	});
        	});
        	
        	// Action tombol Remove untuk hapus item dari cart
        	removeBtn.setOnAction(e -> {
        		boolean success = cartItemHandler.deleteCartItem(item.getIdCustomer(),item.getIdProduct());
    			
				if(success) {
					showAlert("Success", "Cart has been successfully deleted", AlertType.INFORMATION);
				}
				else {
					showAlert("Error", "Failed to delete the cart. Please try again.", AlertType.ERROR);
				}
				parent.setCenter(new CustCartPage(parent, customer));
        	});
        	
        	row++;
        }
        
        // Jika tidak ada promo, grand total sama dengan total order
        this.grandTotalToPay = totalOrder;
        
        final double finalTotalOrder = totalOrder; 
        final int finalRow = row;
        
        Label totalOrderLbl = new Label("Total Order: " + totalOrder);
        
        add(totalOrderLbl,5,finalRow+1);
        add(codePromoLbl,5,finalRow+5);
        add(codePromoTF,6,finalRow+5);
        add(enterCodePromoBtn,7,finalRow+5);
        add(checkOutBtn,6,finalRow+6);
        
        // Action tombol Enter untuk redeem promo
        enterCodePromoBtn.setOnAction(e -> {
        	String codePromoStr = codePromoTF.getText();
        	Promo promo = promoHandler.getPromo(codePromoStr);
        	
        	// Hapus label promo sebelumnya jika ada
            if (afterDiscLbl != null) getChildren().remove(afterDiscLbl);
            if (grandTotalLbl != null) getChildren().remove(grandTotalLbl);
            if (descPromoLbl != null) getChildren().remove(descPromoLbl);
            
        	if(promo==null) {
        		this.appliedPromo = null;
        		showAlert("Error", "Invalid Code Promo", AlertType.ERROR);
        	}else {
        		double discountRate = promo.getDiscountPercentage(); 
        	    double discountAmount = finalTotalOrder * discountRate;
        	    
        	    // Hitung grand total setelah diskon
        	    this.grandTotalToPay = finalTotalOrder - discountAmount;
        	    this.appliedPromo = promo; 
        	    
        	    // Tampilkan label promo dan grand total baru
                afterDiscLbl = new Label("Discount: " + discountAmount);
                grandTotalLbl = new Label("Grand Total: " + this.grandTotalToPay);
                descPromoLbl = new Label(promo.getHeadline());
        		
        		add(afterDiscLbl,5,finalRow+3);
        		add(grandTotalLbl,5,finalRow+4);
        		add(descPromoLbl,5,finalRow+2);
        		
        		showAlert("Success", "Code Promo has been successfully redeemed", AlertType.INFORMATION);
        	}
        });
        
        // Action tombol Check Out
        checkOutBtn.setOnAction(e -> {
        	double currentBalance = customer.getBalance();
            
            // Validasi saldo customer
            if (currentBalance < this.grandTotalToPay) {
                showAlert("Insufficient Balance", 
                          "Your balance is " + currentBalance + ", but total is " + this.grandTotalToPay, 
                          AlertType.ERROR);
                return; 
            }
            
            String idPromoToUse = null;
            if (this.appliedPromo != null) {
                idPromoToUse = this.appliedPromo.getIdPromo();
            }
            
            // Checkout order
            boolean success = orderHeaderHandler.checkout(customer.getIdUser(),idPromoToUse);
			
			if(success) {
				customerHandler.reduceBalance(customer, this.grandTotalToPay);
				showAlert("Success", "Subscription Successfull", AlertType.INFORMATION);
			}
			else {
				showAlert("Error", "Failed to Order. Please try again.", AlertType.ERROR);
			}

            // Refresh halaman cart
            parent.setCenter(new CustCartPage(parent, customer));
        });
        
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
