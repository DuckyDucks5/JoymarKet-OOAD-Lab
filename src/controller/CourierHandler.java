package controller;

import java.util.ArrayList;

import model.Courier;
import repository.CourierDA;

public class CourierHandler {
	private CourierDA courierDA = new CourierDA();
	
	public ArrayList<Courier> getAllCouriers(){
		return courierDA.getAllCouriers();
	}

}
