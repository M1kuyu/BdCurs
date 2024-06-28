package com.example.models;

public class Client {
    private int clientId;
    private String name;
    private String phone;
    private String address;
    private int nearestDeliveryCenterId;
    private int userId;

    public Client(int clientId, String name, String phone, String address, int nearestDeliveryCenterId, int userId) {
        this.clientId = clientId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nearestDeliveryCenterId = nearestDeliveryCenterId;
        this.userId = userId;
    }

    public int getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getNearestDeliveryCenterId() {
        return nearestDeliveryCenterId;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}
