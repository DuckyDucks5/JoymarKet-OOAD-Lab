package model;

public class Courier extends User{
	
	private String vehicleType;
	private String vehiclePlate;
	
	public Courier(String idUser, String fullName, String email, String phone, String address,
			 String vehicleType, String vehiclePlate) {
		super(idUser, fullName, email, null, phone, address, "courier");
		this.vehicleType = vehicleType;
		this.vehiclePlate = vehiclePlate;
	}
	

	
	

}
