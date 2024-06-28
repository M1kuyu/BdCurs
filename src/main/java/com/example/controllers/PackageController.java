/*package com.example.controllers;

import com.example.models.Package;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



public class PackageController {

    public Package getPackageById(int packageId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        Package aPackage = null;

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Packages WHERE package_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, packageId);
            rs = ps.executeQuery();

            if (rs.next()) {
                aPackage = new Package(
                        rs.getInt("package_id"),
                        rs.getDouble("weight"),
                        rs.getString("type"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getInt("delivery_center_id"),
                        rs.getInt("sender_center_id"),
                        rs.getInt("courier_id")
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

        return aPackage;
    }

    public List<Package> getPackagesByUserId(int userId) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Package> packages = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Packages WHERE sender_id = ? OR receiver_id = ?";
            ps = connection.prepareStatement(query);
            ps.setInt(1, userId);
            ps.setInt(2, userId);
            rs = ps.executeQuery();

            while (rs.next()) {
                Package aPackage = new Package(
                        rs.getInt("package_id"),
                        rs.getDouble("weight"),
                        rs.getString("type"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getInt("sender_center_id"),
                        rs.getInt("receiver_center_id"),
                        rs.getInt("status")
                );
                packages.add(aPackage);
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

        return packages;
    }


    public boolean sendPackage(Package aPackage) {
        Connection connection = null;
        PreparedStatement ps = null;
        boolean sent = false;

        try {
            connection = DBUtil.getConnection();
            String query = "INSERT INTO Packages (weight, type, sender_id, receiver_id, delivery_center_id, sender_center_id, courier_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
            ps = connection.prepareStatement(query);
            ps.setDouble(1, aPackage.getWeight());
            ps.setString(2, aPackage.getType());
            ps.setInt(3, aPackage.getSenderId());
            ps.setInt(4, aPackage.getReceiverId());
            ps.setInt(5, aPackage.getDeliveryCenterId());
            ps.setInt(6, aPackage.getSenderCenterId());
            ps.setInt(7, aPackage.getCourierId());

            sent = ps.executeUpdate() > 0;
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

        return sent;
    }

    public List<Package> getPackagesByCenter(String center) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Package> packages = new ArrayList<>();

        try {
            connection = DBUtil.getConnection();
            String query = "SELECT * FROM Packages WHERE delivery_center_id = (SELECT center_id FROM DeliveryCenters WHERE name = ?)";
            ps = connection.prepareStatement(query);
            ps.setString(1, center);
            rs = ps.executeQuery();

            while (rs.next()) {
                Package aPackage = new Package(
                        rs.getInt("package_id"),
                        rs.getDouble("weight"),
                        rs.getString("type"),
                        rs.getInt("sender_id"),
                        rs.getInt("receiver_id"),
                        rs.getInt("delivery_center_id"),
                        rs.getInt("sender_center_id"),
                        rs.getInt("courier_id")
                );
                packages.add(aPackage);
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

        return packages;
    }
}
*/
package com.example.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import com.example.models.Client;
import com.example.models.Package;
import com.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PackageController extends BaseController {

    @FXML
    private TextField weightField;
    @FXML
    private ComboBox<String> typeComboBox;
    @FXML
    private ComboBox<Client> receiverComboBox;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<String> centerComboBox;
    @FXML
    private Button sendPackageButton;

    @FXML
    public void initialize() {
        loadReceivers();
        loadCenters();
        sendPackageButton.setOnAction(event -> sendPackage());
    }

    private void loadReceivers() {
        ObservableList<Client> receivers = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT client_id, name, phone, address FROM Clients";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Client client = new Client();
                client.setClientId(rs.getInt("client_id"));
                client.setName(rs.getString("name"));
                client.setPhone(rs.getString("phone"));
                client.setAddress(rs.getString("address"));
                receivers.add(client);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        receiverComboBox.setItems(receivers);
    }

    private void loadCenters() {
        ObservableList<String> centers = FXCollections.observableArrayList();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT name FROM DeliveryCenters";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                centers.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        centerComboBox.setItems(centers);
    }

    private void sendPackage() {
        float weight = Float.parseFloat(weightField.getText());
        String type = typeComboBox.getValue();
        Client receiver = receiverComboBox.getValue();
        String address = addressField.getText();
        String center = centerComboBox.getValue();

        try (Connection conn = DBUtil.getConnection()) {
            // Вставка информации о посылке
            String query = "INSERT INTO Packages (weight, type, receiver_id, delivery_center_id, sender_center_id) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setFloat(1, weight);
            stmt.setString(2, type);
            stmt.setInt(3, receiver.getClientId());
            stmt.setInt(4, getCenterIdByName(center));
            stmt.setInt(5, getNearestCenterIdByAddress(address)); // Метод для получения ID ближайшего центра по адресу
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int packageId = generatedKeys.getInt(1);
                addPackageHistory(packageId, getCenterIdByName(center), 1); // Начальный статус
                int courierId = assignRandomCourier(getCenterIdByName(center));
                updatePackageWithCourier(packageId, courierId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private int getCenterIdByName(String centerName) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT center_id FROM DeliveryCenters WHERE name = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, centerName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("center_id");
            } else {
                throw new SQLException("Center not found");
            }
        }
    }

    private int getNearestCenterIdByAddress(String address) throws SQLException {
        // Реализация логики получения ближайшего центра по адресу
        return 1; // Возвращаем ID ближайшего центра (временная заглушка)
    }

    private void addPackageHistory(int packageId, int centerId, int statusId) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "INSERT INTO PackageDeliveryHistory (package_id, center_id, status_id) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, packageId);
            stmt.setInt(2, centerId);
            stmt.setInt(3, statusId);
            stmt.executeUpdate();
        }
    }

    private int assignRandomCourier(int centerId) throws SQLException {
        List<Integer> couriers = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT courier_id FROM Couriers WHERE delivery_center_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, centerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                couriers.add(rs.getInt("courier_id"));
            }
        }

        if (couriers.isEmpty()) {
            throw new SQLException("No couriers available for the selected center");
        }

        Random random = new Random();
        return couriers.get(random.nextInt(couriers.size()));
    }

    private void updatePackageWithCourier(int packageId, int courierId) throws SQLException {
        try (Connection conn = DBUtil.getConnection()) {
            String query = "UPDATE Packages SET courier_id = ? WHERE package_id = ?";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, courierId);
            stmt.setInt(2, packageId);
            stmt.executeUpdate();
        }
    }
}
