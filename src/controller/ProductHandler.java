package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Product;
import repository.ProductDA;
import utils.Connect;

public class ProductHandler {
	private ProductDA productDA = new ProductDA();
	
	public ArrayList<Product> getAllProducts() {
		return productDA.getAllProducts();
	}
	
	public Product getProduct(String idProduct) {
		return productDA.getProduct(idProduct);
	}
	
	public boolean editProductStock(String idProduct, int newStock) {
		return productDA.editProductStock(idProduct, newStock);
	}
	
	public ArrayList<Product> getAvailableProducts(){
		return productDA.getAvailableProducts();
	}
}
