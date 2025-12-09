package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import model.Product;
import utils.Connect;

public class ProductDA {

    // Mengambil koneksi database
	private Connection conn = Connect.getInstance().getConn();

    
    // Mengambil semua produk
	public ArrayList<Product> getAllProducts() {
		ArrayList<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product";

		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

            // Looping hasil query dan masukkan ke list
			while(rs.next()) {
				products.add(new Product(
                        rs.getString("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category")));
			}	
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return products; // Kembalikan list produk
	}

    
    // Mengambil produk berdasarkan ID
	public Product getProduct(String idProduct) {
		String query = "SELECT * FROM product WHERE idProduct = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, idProduct);
			ResultSet rs = ps.executeQuery();

            // Jika ditemukan, kembalikan sebagai objek Product
			if(rs.next()) {
				return new Product(
                        rs.getString("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return null; // Return null jika tidak ditemukan
	}

    
    // Mengupdate stok produk
	public boolean editProductStock(String idProduct, int newStock) {
		String query = "UPDATE product SET stock = ? WHERE idProduct = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, newStock);
			ps.setString(2, idProduct);

            // Eksekusi update dan kembalikan hasil
			int change = ps.executeUpdate();
			return change > 0;
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return false;
	}

    
    // Mengambil semua produk yang tersedia (stok > 0)
	public ArrayList<Product> getAvailableProducts(){
		ArrayList<Product> products = new ArrayList<>();
		String query = "SELECT * FROM product WHERE stock > 0";

		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

            // Looping hasil query dan masukkan ke list
			while(rs.next()) {
				products.add(new Product(
                        rs.getString("idProduct"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("stock"),
                        rs.getString("category")));
			}	
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return products; // Kembalikan list produk
	}
}
