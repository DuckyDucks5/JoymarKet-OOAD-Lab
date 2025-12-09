package controller;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import model.Courier;
import repository.CourierDA;

public class CourierHandler {
	
	// Data Access Object Untuk Mengakses Data Courier
	private CourierDA courierDA = new CourierDA();
	
	// Mengambil Semua Data Courier Dari Database
	public ArrayList<Courier> getAllCouriers(){
		return courierDA.getAllCouriers();
	}

	// Mengassign Courier Secara Otomatis Untuk Order Tertentu
    public boolean assignCourier(String orderId) {
        return courierDA.assignCourier(orderId);
    }
    
    // Mengambil Id Courier Yang Menghandle Suatu Order
    public String getCourier(String idOrder) {
    	return courierDA.getCourier(idOrder);
    }

}
