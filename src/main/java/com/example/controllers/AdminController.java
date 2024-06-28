package com.example.controllers;

import com.example.models.Admin;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminController {

    public Admin getAdminByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Admin admin = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Admins WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                admin = new Admin(
                        rs.getInt("admin_id"),
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

        return admin;
    }

    public boolean updateAdmin(Admin admin) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            connection = DBUtil.getConnection();
            String query = "UPDATE Admins SET name = ?, phone = ?, delivery_center_id = ? WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, admin.getName());
            ps.setString(2, admin.getPhone());
            ps.setInt(3, admin.getDeliveryCenterId());
            ps.setInt(4, admin.getUserId());

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
