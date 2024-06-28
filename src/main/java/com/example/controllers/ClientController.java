/*package com.example.controllers;

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
*/
package com.example.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import com.example.models.Client;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientController extends BaseController {

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private Button saveButton;
    @FXML
    private Button updateButton;
    @FXML
    private Button deleteButton;

    @FXML
    public void initialize() {
        saveButton.setOnAction(event -> saveClient());
        updateButton.setOnAction(event -> updateClient());
        deleteButton.setOnAction(event -> deleteClient());
    }

    private void saveClient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO Clients (name, phone, address, nearest_delivery_center_id) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            // Replace with actual nearest_delivery_center_id
            stmt.setInt(4, 1);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateClient() {
        String name = nameField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();

        // Assume client_id is known
        int clientId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "UPDATE Clients SET name = ?, phone = ?, address = ? WHERE client_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setString(2, phone);
            stmt.setString(3, address);
            stmt.setInt(4, clientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteClient() {
        // Assume client_id is known
        int clientId = 1;

        try (Connection conn = DBUtil.getConnection()) {
            String query = "DELETE FROM Clients WHERE client_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, clientId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
