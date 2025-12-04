package model;

import java.sql.Timestamp;

public class OrderHeader {
	
	private String idOrder;
    private String idCustomer;
    private String idPromo;
    private String status;
    private String courierId;
    private Timestamp orderedAt;
    private double totalAmount;
	public OrderHeader(String idOrder, String idCustomer, String idPromo, String status, String courierId,
			Timestamp orderedAt, double totalAmount) {
		super();
		this.idOrder = idOrder;
		this.idCustomer = idCustomer;
		this.idPromo = idPromo;
		this.status = status;
		this.courierId = courierId;
		this.orderedAt = orderedAt;
		this.totalAmount = totalAmount;
	}
	public String getIdOrder() {
		return idOrder;
	}
	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}
	public String getIdCustomer() {
		return idCustomer;
	}
	public void setIdCustomer(String idCustomer) {
		this.idCustomer = idCustomer;
	}
	public String getIdPromo() {
		return idPromo;
	}
	public void setIdPromo(String idPromo) {
		this.idPromo = idPromo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCourierId() {
		return courierId;
	}
	public void setCourierId(String courierId) {
		this.courierId = courierId;
	}
	public Timestamp getOrderedAt() {
		return orderedAt;
	}
	public void setOrderedAt(Timestamp orderedAt) {
		this.orderedAt = orderedAt;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	
    
    
}
