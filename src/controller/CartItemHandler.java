package controller;

import java.util.ArrayList;

import model.CartItem;
import model.Product;
import repository.CartItemDA;

public class CartItemHandler {

	private CartItemDA cartItemDA = new CartItemDA();
	private ProductHandler productHandler = new ProductHandler();

    public boolean createCartItem(String idCustomer, String idProduct, int count) {

        // Ambil Cart Item Lama Jika Sudah Ada Di Keranjang
        CartItem existing = cartItemDA.getCartItem(idCustomer, idProduct);

        // Ambil Data Produk Untuk Mengecek Stok Maksimal
        Product p = productHandler.getProduct(idProduct);
        int maxStock = p.getStock();

        // Jika Belum Ada Item Ini Di Cart
        if (existing == null) {
            CartItem newItem = new CartItem(idCustomer, idProduct, count);
            return cartItemDA.createCartItem(newItem);
        } else {
        	// Jika Produk Sudah Ada Di Cart Maka Jumlahnya Ditambah
        	// Jika Jumlah Baru Melebihi Stok Maka Di-set Ke Stok Maksimal
            int newCount = existing.getCount() + count;
            if(newCount > maxStock) {
            	newCount = maxStock;
            }
            
            return cartItemDA.editCartItem(idCustomer, idProduct, newCount);
        }
        
    }
    
    // Mengambil Semua Cart Item Milik Customer
    public ArrayList<CartItem> getCartItems(String idCustomer) {
		return cartItemDA.getCartItems(idCustomer);
	}
    
    // Mengubah Jumlah Item Pada Cart
    public boolean editCartItem(String idCustomer, String idProduct, int count) {
		return cartItemDA.editCartItem(idCustomer, idProduct, count);
	}
    
    // Menghapus Satu Item Dari Cart
    public boolean deleteCartItem(String idCustomer, String idProduct) {
		return cartItemDA.deleteCartItem(idCustomer, idProduct);
	}
    
    // Menghapus Semua Item Dalam Cart Customer
    public boolean clearCart(String idCustomer) {
		return cartItemDA.clearCart(idCustomer);
	}

}
