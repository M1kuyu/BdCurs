package com.example.controllers;

import com.example.models.User;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegistrationController {

    public boolean registerUser(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean registered = false;

        try {
            connection = DBUtil.getConnection();
            String query = "INSERT INTO Users (login, password, type_id) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getTypeId());

            registered = ps.executeUpdate() > 0;
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

        return registered;
    }
}

