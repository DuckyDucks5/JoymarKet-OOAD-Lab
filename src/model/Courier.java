package model;

public class Courier extends User{
	
	private String vehicleType;
	private String vehiclePlate;
	
	public Courier(String idUser, String fullName, String email, String password, String phone, String address,
			String role, String vehicleType, String vehiclePlate) {
		super(idUser, fullName, email, password, phone, address, role);
		this.vehicleType = vehicleType;
		this.vehiclePlate = vehiclePlate;
	}
	
	

}
