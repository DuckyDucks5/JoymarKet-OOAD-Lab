package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Product;
import utils.Connect;

public class ProductDA {

	private Connection conn = Connect.getInstance().getConn();
	
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
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
	
	public Product getProduct(String idProduct) {
		String query = "SELECT * FROM product WHERE idProduct = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, idProduct);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new Product(rs.getString("idProduct"), rs.getString("name"), rs.getDouble("price"), rs.getInt("stock"), rs.getString("category"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean editProductStock(String idProduct, int newStock) {
		String query = "UPDATE product SET stock = ? WHERE idProduct = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newStock);
			ps.setString(2, idProduct);
			
			int change = ps.executeUpdate();
			return change > 0;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public ArrayList<Product> getAvailableProducts(){
		ArrayList<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product WHERE stock > 0";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
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
