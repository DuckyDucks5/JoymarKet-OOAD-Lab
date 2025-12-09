package controller;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.Courier;
import repository.CourierDA;

public class CourierHandler {
	private CourierDA courierDA = new CourierDA();
	
	public ArrayList<Courier> getAllCouriers(){
		return courierDA.getAllCouriers();
	}
    public boolean assignCourier(String orderId) {
        return courierDA.assignCourier(orderId);
    }
    
    public String getCourier(String idOrder) {
    	return courierDA.getCourier(idOrder);
    }

}
