package model;

public class Customer extends User {
	//Atribut yang dibutuhkan
    private double balance;
    private String gender;

    public Customer(String idUser, String fullName, String email, String password,
                    String phone, String address, double balance, String gender) {
        super(idUser, fullName, email, password, phone, address, "Customer");
        this.balance = balance;
        this.gender = gender;
    }

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getBalance() {
    	return balance;
    }
    
    public void setBalance(double balance) {
    	this.balance = balance;
    }
}