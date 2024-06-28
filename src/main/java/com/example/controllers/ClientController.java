package com.example.controllers;

import com.example.models.Client;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientController {

    public Client getClientByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Client client = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Clients WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            rs = ps.executeQuery();

            if (rs.next()) {
                client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("nearest_delivery_center_id"),
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

        return client;
    }

    public boolean updateClient(Client client) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            connection = DBUtil.getConnection();
            String query = "UPDATE Clients SET name = ?, phone = ?, address = ?, nearest_delivery_center_id = ? WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, client.getName());
            ps.setString(2, client.getPhone());
            ps.setString(3, client.getAddress());
            ps.setInt(4, client.getNearestDeliveryCenterId());
            ps.setInt(5, client.getUserId());

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
