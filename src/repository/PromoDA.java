package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Promo;
import utils.Connect;

public class PromoDA {

    // Mengambil koneksi database
	private Connection conn = Connect.getInstance().getConn();

    
    // Mengambil semua promo
	public ArrayList<Promo> getAllPromos() {
		ArrayList<Promo> promos = new ArrayList<>();
		String query = "SELECT * FROM promo";

		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();

            // Looping hasil query dan masukkan ke list
			while(rs.next()) {
				promos.add(new Promo(
                        rs.getString("idPromo"),
                        rs.getString("code"),
                        rs.getString("headline"),
                        rs.getDouble("discountPercentage")));
			}	
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return promos; // Kembalikan list promo
	}

    
    // Mengambil promo berdasarkan kode
	public Promo getPromo(String code) {
		String query = "SELECT * FROM promo WHERE code = ?";

		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();

            // Jika ditemukan, kembalikan sebagai objek Promo
			if(rs.next()) {
				return new Promo(
                        rs.getString("idPromo"),
                        rs.getString("code"),
                        rs.getString("headline"),
                        rs.getDouble("discountPercentage"));
			}
		} catch (SQLException e) {
			e.printStackTrace(); // Tangani exception SQL
		}
		return null; // Return null jika tidak ditemukan
	}
}
