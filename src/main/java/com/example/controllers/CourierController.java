package com.example.controllers;

import com.example.models.Courier;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourierController {

    public Courier getCourierByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Courier courier = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Couriers WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                courier = new Courier(
                        rs.getInt("courier_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getInt("delivery_center_id"),
                        rs.getInt("user_id")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courier;
    }

    public boolean updateCourier(Courier courier) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            connection = DBUtil.getConnection();
            String query = "UPDATE Couriers SET name = ?, phone = ?, delivery_center_id = ? WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, courier.getName());
            ps.setString(2, courier.getPhone());
            ps.setInt(3, courier.getDeliveryCenterId());
            ps.setInt(4, courier.getUserId());

            updated = ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                DBUtil.closePreparedStatement(ps);
                DBUtil.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return updated;
    }
}
