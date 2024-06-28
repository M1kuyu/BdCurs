package com.example.controllers;

import com.example.models.Package;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SendPackageController {

    public Package sendPackage(Package aPackage) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean sent = false;

        try {
            connection = DBUtil.getConnection();
            String query = "INSERT INTO Packages (weight, type, sender_id, receiver_id, delivery_center_id, sender_center_id, courier_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setDouble(1, aPackage.getWeight());
            ps.setString(2, aPackage.getType());
            ps.setInt(3, aPackage.getSenderId());
            ps.setInt(4, aPackage.getReceiverId());
            ps.setInt(5, aPackage.getDeliveryCenterId());
            ps.setInt(6, aPackage.getSenderCenterId());
            ps.setInt(7, aPackage.getCourierId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    aPackage.setPackageId(rs.getInt(1));
                    sent = true;
                }
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

        return sent ? aPackage : null;
    }
}
