package controller;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;

import model.CartItem;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;
import repository.OrderDetailDA;
import repository.OrderHeaderDA;

public class OrderHeaderHandler {
	
	// Data Access Object Untuk Operasi Order Header
	private OrderHeaderDA orderHeaderDA = new OrderHeaderDA();

	// Handler Untuk Mengambil Dan Mengatur Data Cart
	private CartItemHandler cartItemHandler = new CartItemHandler();

	// Handler Untuk Mendapatkan Dan Mengupdate Stok Produk
	private ProductHandler productHandler = new ProductHandler();

	// Data Access Object Untuk Detail Order
	private OrderDetailDA orderDetailDA = new OrderDetailDA();
	
	// Proses Checkout Customer
	public boolean checkout(String idCustomer, String idPromo) {

		// Generate ID Order Baru
        String orderId = orderHeaderDA.generateOrderID();

        // Timestamp Saat Order Dibuat
        Timestamp orderedTime = new Timestamp(System.currentTimeMillis());

        // Courier Diatur Ketika Sudah Di-Assign Admin
        String courierId = null;

        // Mengambil Semua Cart Item Customer
        ArrayList<CartItem> cartItems = cartItemHandler.getCartItems(idCustomer);

        // Menghitung Total Harga Sebelum Promo
        double total = 0;
        for (CartItem item : cartItems) {
            Product p = productHandler.getProduct(item.getIdProduct());
            total += p.getPrice() * item.getCount();
        }

        // Membuat Order Header
        OrderHeader header = new OrderHeader(
                orderId,
                idCustomer,
                idPromo,
                "Pending",       // Status Awal Order
                orderedTime,
                total
        );

        // Insert Order Header Ke Database
        if (!orderHeaderDA.createOrderHeader(header))
            return false;

        // Menambahkan Setiap Item Ke Order Detail
        for (CartItem item : cartItems) {
            Product p = productHandler.getProduct(item.getIdProduct());

            // Membuat Detail Order
            OrderDetail detail = new OrderDetail(
                    orderId,
                    item.getIdProduct(),
                    item.getCount()
            );

            // Insert Ke Database
            orderDetailDA.createOrderDetail(detail);

            // Kurangi Stock Produk Setelah Di-Checkout
            productHandler.editProductStock(item.getIdProduct(), p.getStock() - item.getCount());
        }

        // Menghapus Cart Customer Setelah Berhasil Checkout
        cartItemHandler.clearCart(idCustomer);

        return true;
    }

	// Mengambil Semua Order Dari Customer Tertentu
	public ArrayList<OrderHeader> getCustomerOrders(String idCustomer){
        return orderHeaderDA.getCustomerOrders(idCustomer);
    }

	// Mengambil Semua Order Dari Semua Customer (Khusus Admin)
	public ArrayList<OrderHeader> getAllCustomerOrders(){
        return orderHeaderDA.getAllCustomerOrders();
    }

	// Update Status Order (Pending → On Delivery → Delivered)
    public boolean updateStatus(String idOrder, String newStatus){
        return orderHeaderDA.updateStatus(idOrder, newStatus);
    }

	// Mengambil Semua Detail Dari Suatu Order
    public ArrayList<OrderDetail> getOrderDetails(String idOrder){
        return orderDetailDA.getOrderDetails(idOrder);
    }
}
