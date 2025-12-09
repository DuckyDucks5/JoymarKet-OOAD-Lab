package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.CartItem;
import utils.Connect;

public class CartItemDA {

    // Mengambil koneksi database
    private Connection conn = Connect.getInstance().getConn();

    
    // Menambahkan produk ke cart
    public boolean createCartItem(CartItem item) {
        String query = "INSERT INTO cart_item (idCustomer, idProduct, count) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Set parameter insert item baru
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

    
    // Mengupdate jumlah item jika produk sudah ada di cart
    public boolean editCartItem(String idCustomer, String idProduct, int count) {
        String query = "UPDATE cart_item SET count = ? WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Set jumlah baru item di cart
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


    // Mengecek apakah produk sudah ada di cart
    public CartItem getCartItem(String idCustomer, String idProduct) {
        String query = "SELECT * FROM cart_item WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Query untuk mendapatkan satu item tertentu
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);

            ResultSet rs = ps.executeQuery();

            // Jika item ditemukan, kembalikan sebagai objek CartItem
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


    // Mengambil seluruh item cart milik customer
    public ArrayList<CartItem> getCartItems(String idCustomer) {
        ArrayList<CartItem> list = new ArrayList<>();
        String query = "SELECT * FROM cart_item WHERE idCustomer = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Query untuk mendapatkan semua item milik satu customer
            ps.setString(1, idCustomer);
            ResultSet rs = ps.executeQuery();

            // Looping semua item dan masukkan ke list
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


    // Menghapus 1 produk dari cart
    public boolean deleteCartItem(String idCustomer, String idProduct) {
        String query = "DELETE FROM cart_item WHERE idCustomer = ? AND idProduct = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Hapus item berdasarkan customer & product
            ps.setString(1, idCustomer);
            ps.setString(2, idProduct);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    // Menghapus seluruh cart milik 1 customer (saat checkout)
    public boolean clearCart(String idCustomer) {
        String query = "DELETE FROM cart_item WHERE idCustomer = ?";

        try (PreparedStatement ps = conn.prepareStatement(query)) {

            // Hapus semua item dalam cart customer
            ps.setString(1, idCustomer);

            ps.executeUpdate();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
