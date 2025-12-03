package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CartItem;
import utils.Connect;

public class CartItemDA {

	private Connection conn = Connect.getInstance().getConn();
	
	//memasukkan product ke cart
	public boolean createCartItem(CartItem item) {
        String query = "INSERT INTO cart_item (idCustomer, idProduct, count) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, item.getIdCustomer());
            ps.setString(2, item.getIdProduct());
            ps.setInt(3, item.getCount());

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	// UPDATE count jika produk sudah ada di cart
    public boolean editCartItem(String idCustomer, String idProduct, int count) {
        String query = "UPDATE cart_item SET count = ? WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setInt(1, count);
            ps.setString(2, idCustomer);
            ps.setString(3, idProduct);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

	
	// CEK apakah produk sudah ada/ pernah masuk ke cart
    public CartItem getCartItem(String idCustomer, String idProduct) {
        String query = "SELECT * FROM cart_item WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new CartItem(
                        rs.getString("idCustomer"),
                        rs.getString("idProduct"),
                        rs.getInt("count")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    
 // GET semua cart milik customer
    public ArrayList<CartItem> getCartItems(String idCustomer) {
        ArrayList<CartItem> list = new ArrayList<>();
        String query = "SELECT * FROM cart_item WHERE idCustomer = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new CartItem(
                        rs.getString("idCustomer"),
                        rs.getString("idProduct"),
                        rs.getInt("count")
                ));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    
    // DELETE satu produk dari cart
    public boolean deleteCartItem(String idCustomer, String idProduct) {
        String query = "DELETE FROM cart_item WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    //DELETE cart 1 customer (dipakai apabila sudah checkout)
    public boolean clearCart(String idCustomer) {
        String query = "DELETE FROM cart_item WHERE idCustomer = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, idCustomer);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
