package com.example.controllers;

import com.example.models.*;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private User currentUser;
    private Client currentClient;
    private Admin currentAdmin;
    private Courier currentCourier;

    public User loginUser(String login, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        User user = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Users WHERE login = ? AND password = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getInt("user_id"), rs.getString("login"), rs.getString("password"), rs.getInt("type_id"));
                setCurrentUser(user);
                loadUserDetails();
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

        return user;
    }

    private void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Client getCurrentClient() {
        return currentClient;
    }

    public Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public Courier getCurrentCourier() {
        return currentCourier;
    }

    private void loadUserDetails() {
        if (currentUser == null) {
            return;
        }

        switch (currentUser.getTypeId()) {
            case 1:
                loadClientDetails();
                break;
            case 2:
                loadCourierDetails();
                break;
            case 3:
                loadAdminDetails();
                break;
        }
    }

    private void loadClientDetails() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Clients WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, currentUser.getUserId());
            rs = ps.executeQuery();

            if (rs.next()) {
                currentClient = new Client(
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
    }

    private void loadCourierDetails() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Couriers WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, currentUser.getUserId());
            rs = ps.executeQuery();

            if (rs.next()) {
                currentCourier = new Courier(
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
    }

    private void loadAdminDetails() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Admins WHERE user_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, currentUser.getUserId());
            rs = ps.executeQuery();

            if (rs.next()) {
                currentAdmin = new Admin(
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
    }

    public boolean updateUser(Object user) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean updated = false;

        try {
            connection = DBUtil.getConnection();
            String query = "";

            if (user instanceof Client) {
                query = "UPDATE Clients SET name = ?, phone = ?, address = ?, nearest_delivery_center_id = ? WHERE user_id = ?";
                Client client = (Client) user;
                ps = connection.prepareStatement(query);
                ps.setString(1, client.getName());
                ps.setString(2, client.getPhone());
                ps.setString(3, client.getAddress());
                ps.setInt(4, client.getNearestDeliveryCenterId());
                ps.setInt(5, client.getUserId());
            } else if (user instanceof Courier) {
                query = "UPDATE Couriers SET name = ?, phone = ?, delivery_center_id = ? WHERE user_id = ?";
                Courier courier = (Courier) user;
                ps = connection.prepareStatement(query);
                ps.setString(1, courier.getName());
                ps.setString(2, courier.getPhone());
                ps.setInt(3, courier.getDeliveryCenterId());
                ps.setInt(4, courier.getUserId());
            } else if (user instanceof Admin) {
                query = "UPDATE Admins SET name = ?, phone = ?, delivery_center_id = ? WHERE user_id = ?";
                Admin admin = (Admin) user;
                ps = connection.prepareStatement(query);
                ps.setString(1, admin.getName());
                ps.setString(2, admin.getPhone());
                ps.setInt(3, admin.getDeliveryCenterId());
                ps.setInt(4, admin.getUserId());
            }

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
