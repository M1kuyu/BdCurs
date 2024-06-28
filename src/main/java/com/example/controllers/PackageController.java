package com.example.controllers;

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
