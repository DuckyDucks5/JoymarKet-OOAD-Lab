package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import model.OrderDetail;
import utils.Connect;

public class OrderDetailDA {

	private Connection conn = Connect.getInstance().getConn();

    public boolean createOrderDetail(OrderDetail detail) {

        try {
            String query = "INSERT INTO order_detail (idOrder, idProduct, qty) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, detail.getIdOrder());
            ps.setString(2, detail.getIdProduct());
            ps.setInt(3, detail.getQty());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<OrderDetail> getOrderDetails(String idOrder) {
        ArrayList<OrderDetail> list = new ArrayList<>();

        try {
            String query = "SELECT * FROM order_detail WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, idOrder);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new OrderDetail(
                        rs.getString("idOrder"),
                        rs.getString("idProduct"),
                        rs.getInt("qty")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean deleteDetails(String orderId) {

        try {
            String query = "DELETE FROM order_detail WHERE idOrder = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, orderId);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
