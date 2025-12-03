package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.OrderHeader;
import utils.Connect;

public class OrderHeaderDA {
	
	private Connection conn = Connect.getInstance().getConn();
	
	//membuat order header
	public boolean createOrderHeader(OrderHeader order) {
        try {
            String query = "INSERT INTO order_header (idOrder, idCustomer, idPromo, status, ordered_at, total_amount) VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, order.getIdOrder());
            ps.setString(2, order.getIdCustomer());
            ps.setString(3, order.getIdPromo());
            ps.setString(4, order.getStatus());
            ps.setTimestamp(5, order.getOrderedAt());
            ps.setDouble(6, order.getTotalAmount());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	//Generate orderId 
	public String generateOrderID() {
        try {
            int num = 1;
            while (true) {
                String id = "ODR" + String.format("%03d", num);
                String query = "SELECT idOrder FROM order_header WHERE idOrder = ?";
                PreparedStatement ps = conn.prepareStatement(query);
                ps.setString(1, id);

                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return id;
                }
                num++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public OrderHeader getOrderHeader(String orderId) {
        try {
            String query = "SELECT * FROM order_header WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderId);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new OrderHeader(
                        rs.getString("idOrder"),
                        rs.getString("idCustomer"),
                        rs.getString("idPromo"),
                        rs.getString("status"),
                        rs.getTimestamp("orderedAt"),
                        rs.getDouble("totalAmount"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public ArrayList<OrderHeader> getCustomerOrders(String idCustomer) {
        ArrayList<OrderHeader> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM order_header WHERE idCustomer = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idCustomer);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new OrderHeader(
                        rs.getString("idOrder"),
                        rs.getString("idCustomer"),
                        rs.getString("idPromo"),
                        rs.getString("status"),
                        rs.getTimestamp("orderedAt"),
                        rs.getDouble("totalAmount")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean updateStatus(String idOrder, String status) {
        try {
            String query = "UPDATE order_header SET status = ? WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);

            ps.setString(1, status);
            ps.setString(2, idOrder);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
	

