package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.OrderHeader;
import repository.DeliveryDA;

public class DeliveryHandler {
	
	// Data Access Object Untuk Operasi Delivery
	DeliveryDA deliveryDA = new DeliveryDA();

	// Mengambil Semua Delivery Yang Dimiliki Courier
	public ArrayList<OrderHeader> getAllDeliveries(String idCourier){
        return deliveryDA.getAllDeliveries(idCourier);
    }
	
	// Meng-Assign Courier Ke Order
	public boolean assignCourierToOrder(String idOrder, String idCourier) {
		return deliveryDA.assignCourierToOrder(idOrder, idCourier);
	}
	
	// Mengambil Delivery Berdasarkan Courier Yang Sedang Aktif
	public ArrayList<OrderHeader> getDeliveries(String idCourier){
		return deliveryDA.getDeliveries(idCourier);
	}
}
