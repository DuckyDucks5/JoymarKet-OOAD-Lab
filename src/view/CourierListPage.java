package view;

import java.util.ArrayList;

import controller.CourierHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import model.Courier;

public class CourierListPage extends GridPane{

	CourierHandler handler =  new CourierHandler();
	
	public CourierListPage() {
		setPadding(new Insets(20));
		setVgap(10);
		setHgap(20);
		
		Label nameLbl = new Label("Name");
		Label emailLbl = new Label("Email");
		Label phoneLbl = new Label("Phone");
		Label addressLbl = new Label("Address");
		Label vehicleTypeLbl = new Label("Vehicle Type");
		Label vehiclePlateLbl = new Label("Vehicle Plate");
		
		add(nameLbl, 0, 0);
        add(emailLbl, 1, 0);
        add(phoneLbl, 2, 0);
        add(addressLbl, 3, 0);
        add(vehicleTypeLbl, 4, 0);
        add(vehiclePlateLbl, 5, 0);
		
		ArrayList<Courier> couriers = handler.getAllCouriers();
		
		int row = 1;
		for (Courier c : couriers) {
			Label name = new Label(c.getFullname());
			Label email = new Label(c.getEmail());
			Label phone = new Label(c.getPhone());
			Label address = new Label(c.getAddress());
			Label vehicleType = new Label(c.getVehicleType());
			Label vehiclePlate = new Label(c.getVehiclePlate());
			
			add(name, 0, row);
			add(email, 1, row);
			add(phone, 2, row);
			add(address, 3, row);
			add(vehicleType, 4, row);
			add(vehiclePlate, 5, row);
			row++;
			
		}
	}
	
}
