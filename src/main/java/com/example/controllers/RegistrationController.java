package com.example.controllers;

import com.example.models.User;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RegistrationController {

    public boolean registerUser(User user) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean registered = false;

        try {
            connection = DBUtil.getConnection();
            String query = "INSERT INTO Users (login, password, type_id) VALUES (?, ?, ?)";
            ps = connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getLogin());
            ps.setString(2, user.getPassword());
            ps.setInt(3, user.getTypeId());

            int affectedRows = ps.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        user.setUserId(rs.getInt(1));
                    }
                }
                registered = true;
            }
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
