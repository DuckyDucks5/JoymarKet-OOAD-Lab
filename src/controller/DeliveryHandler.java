package controller;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.OrderHeader;
import repository.DeliveryDA;

public class DeliveryHandler {
	
	DeliveryDA deliveryDA = new DeliveryDA();

	public ArrayList<OrderHeader> getAllDeliveries(String idCourier){
        return deliveryDA.getAllDeliveries(idCourier);
    }
	
	public boolean assignCourierToOrder(String idOrder, String idCourier) {
		return deliveryDA.assignCourierToOrder(idOrder, idCourier);
	}
	
	public ArrayList<OrderHeader> getDeliveries(String idCourier){
		return deliveryDA.getDeliveries(idCourier);
	}
	
	  
}
