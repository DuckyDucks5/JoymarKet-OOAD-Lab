package controller;

import java.sql.Timestamp;
import java.util.ArrayList;

import model.CartItem;
import model.OrderDetail;
import model.OrderHeader;
import model.Product;
import repository.OrderDetailDA;
import repository.OrderHeaderDA;

public class OrderHeaderHandler {
	
	private OrderHeaderDA orderHeaderDA = new OrderHeaderDA();
	private CartItemHandler cartItemHandler = new CartItemHandler();
	private ProductHandler productHandler = new ProductHandler();
	private OrderDetailDA orderDetailDA = new OrderDetailDA();
	
	public boolean checkout(String idCustomer, String idPromo) {

        String orderId = orderHeaderDA.generateOrderID();
        Timestamp orderedTime = new Timestamp(System.currentTimeMillis());

        ArrayList<CartItem> cartItems = cartItemHandler.getCartItems(idCustomer);

        double total = 0;

        for (CartItem item : cartItems) {
            Product p = productHandler.getProduct(item.getIdProduct());
            total += p.getPrice() * item.getCount();
        }

        OrderHeader header = new OrderHeader(
                orderId,
                idCustomer,
                idPromo,
                "Waiting for Delivery",
                orderedTime,
                total
        );

        if (!orderHeaderDA.createOrderHeader(header))
            return false;

        for (CartItem item : cartItems) {
            Product p = productHandler.getProduct(item.getIdProduct());

            OrderDetail detail = new OrderDetail(
                    orderId,
                    item.getIdProduct(),
                    item.getCount()
            );

            orderDetailDA.createOrderDetail(detail);
            productHandler.editProductStock(item.getIdProduct(), p.getStock()-item.getCount());
        }

        cartItemHandler.clearCart(idCustomer);

        return true;
    }

	
	public ArrayList<OrderHeader> getCustomerOrders(String idCustomer){
        return orderHeaderDA.getCustomerOrders(idCustomer);
    }

    public boolean updateStatus(String idOrder, String newStatus){
        return orderHeaderDA.updateStatus(idOrder, newStatus);
    }

    public ArrayList<OrderDetail> getOrderDetails(String idOrder){
        return orderDetailDA.getOrderDetails(idOrder);
    }
	
	
	
}
