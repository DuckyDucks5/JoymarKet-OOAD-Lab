package controller;

import java.util.ArrayList;

import model.Product;
import repository.ProductDA;

public class ProductHandler {
	
	// Data Access Object Untuk Mengakses Tabel Product
	private ProductDA productDA = new ProductDA();
	
	// Mengambil Semua Produk Dari Database
	public ArrayList<Product> getAllProducts() {
		return productDA.getAllProducts();
	}
	
	// Mengambil Satu Produk Berdasarkan ID
	public Product getProduct(String idProduct) {
		return productDA.getProduct(idProduct);
	}
	
	// Mengupdate Stok Produk Setelah Checkout / Admin Edit
	public boolean editProductStock(String idProduct, int newStock) {
		return productDA.editProductStock(idProduct, newStock);
	}
	
	// Mengambil Produk Yang Stoknya Masih Tersedia (>0)
	public ArrayList<Product> getAvailableProducts() {
		return productDA.getAvailableProducts();
	}
}
