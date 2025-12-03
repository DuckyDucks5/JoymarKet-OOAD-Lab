package controller;

import java.util.ArrayList;

import model.CartItem;
import model.Product;
import repository.CartItemDA;

public class CartItemHandler {

	private CartItemDA cartItemDA = new CartItemDA();
	private ProductHandler productHandler = new ProductHandler();

    public boolean createCartItem(String idCustomer, String idProduct, int count) {

        // cek dulu apakah sudah ada di cart
        CartItem existing = cartItemDA.getCartItem(idCustomer, idProduct);
        Product p = productHandler.getProduct(idProduct);
        int maxStock = p.getStock();

        if (existing == null) {
            CartItem newItem = new CartItem(idCustomer, idProduct, count);
            return cartItemDA.createCartItem(newItem);
        } else {
        	//jika product sudah pernah dimasukkan ke cart maka countnya dijumlah
        	//kalau count melebihi sisa stock maka diset max stock
            int newCount = existing.getCount() + count;
            if(newCount > maxStock) {
            	newCount = maxStock;
            }
            
            return cartItemDA.editCartItem(idCustomer, idProduct, newCount);
        }
        
    }
    
    public ArrayList<CartItem> getCartItems(String idCustomer) {
		return cartItemDA.getCartItems(idCustomer);
	}
    
    public boolean editCartItem(String idCustomer, String idProduct, int count) {
		return cartItemDA.editCartItem(idCustomer, idProduct, count);
	}
    
    public boolean deleteCartItem(String idCustomer, String idProduct) {
		return cartItemDA.deleteCartItem(idCustomer, idProduct);
	}
    
    public boolean clearCart(String idCustomer) {
		return cartItemDA.clearCart(idCustomer);
	}
    
	
    
}
