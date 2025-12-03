package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Promo;
import utils.Connect;

public class PromoDA {

	private Connection conn = Connect.getInstance().getConn();
	
	public ArrayList<Promo> getAllPromos() {
		ArrayList<Promo> promos = new ArrayList<>();
		String query = "SELECT * FROM promo";
		
		try {
			PreparedStatement st = conn.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				String idPromo = rs.getString("idPromo");
				String code = rs.getString("code");
				String headline = rs.getString("headline");
				double discountPercentage = rs.getDouble("discountPercentage");
				promos.add(new Promo(idPromo, code, headline, discountPercentage));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return promos;
	}
	
	public Promo getPromo(String code) {
		String query = "SELECT * FROM promo WHERE code = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, code);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				return new Promo(rs.getString("idPromo"), rs.getString("code"),rs.getString("headline"), rs.getDouble("discountPercentage"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
