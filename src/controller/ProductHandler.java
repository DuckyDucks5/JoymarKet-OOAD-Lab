package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Product;
import utils.Connect;

public class ProductHandler {
	private Connection conn = Connect.getInstance().getConn();
	
	public ArrayList<Product> getAllProduct() {
		ArrayList<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product";
		
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				String idProduct = rs.getString("idProduct");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int stock = rs.getInt("stock");
				String category = rs.getString("category");
				products.add(new Product(idProduct, name, price, stock, category));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return products;
		
	}
}
