package com.example.controllers;

import com.example.models.Admin;
import com.example.models.Client;
import com.example.models.Courier;
import com.example.models.User;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserController {

    private User currentUser;
    private List<Client> clients;
    private List<Courier> couriers;
    private List<Admin> admins;

    public UserController() {

        clients = new ArrayList<>();
        couriers = new ArrayList<>();
        admins = new ArrayList<>();

    }


    public Client getClientById(int id) {
        for (Client client : clients) {
            if (client.getUserId() == id) {
                return client;
            }
        }
        return null; // or throw an exception
    }

    public Courier getCurrentCourier() {
        if (currentUser.getTypeId() == 2) {
            for (Courier courier : couriers) {
                if (courier.getUserId() == currentUser.getUserId()) {
                    return courier;
                }
            }
        }
        return null; // or throw an exception
    }

    public Admin getCurrentAdmin() {
        if (currentUser.getTypeId() == 3) {
            for (Admin admin : admins) {
                if (admin.getUserId() == currentUser.getUserId()) {
                    return admin;
                }
            }
        }
        return null; // or throw an exception
    }

    public boolean updateUser(User user) {
        if (user instanceof Client) {
            Client clientToUpdate = getClientById(user.getUserId());
            if (clientToUpdate != null) {
                clientToUpdate.setName(((Client) user).getName());
                clientToUpdate.setPhone(((Client) user).getPhone());
                clientToUpdate.setAddress(((Client) user).getAddress());
                return true;
            }
        } else if (user instanceof Courier) {
            Courier courierToUpdate = getCurrentCourier();
            if (courierToUpdate != null) {
                courierToUpdate.setName(((Courier) user).getName());
                courierToUpdate.setPhone(((Courier) user).getPhone());
                return true;
            }
        } else if (user instanceof Admin) {
            Admin adminToUpdate = getCurrentAdmin();
            if (adminToUpdate != null) {
                adminToUpdate.setName(((Admin) user).getName());
                adminToUpdate.setPhone(((Admin) user).getPhone());
                return true;
            }
        }
        return false; // if user not found or update failed
    }

    public User loginUser(String login, String password) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Users WHERE login = ? AND password = ?";
            ps = connection.prepareStatement(query);
            ps.setString(1, login);
            ps.setString(2, password);
            rs = ps.executeQuery();

            if (rs.next()) {
                currentUser = new User(
                        rs.getInt("user_id"),
                        rs.getString("login"),
                        rs.getString("password"),
                        rs.getInt("type_id")
                );
                return currentUser;
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

        return null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public List<Client> getAllClients() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Client> clients = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Clients";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getInt("nearest_delivery_center_id"),
                        rs.getInt("user_id")
                );
                clients.add(client);
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

        return clients;
    }

    public List<String> getAllDeliveryCenters() {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> centers = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT name FROM DeliveryCenters";
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()) {
                centers.add(rs.getString("name"));
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

        return centers;
    }
}
