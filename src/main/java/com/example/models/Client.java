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

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getNearestDeliveryCenterId() {
        return nearestDeliveryCenterId;
    }

    public void setNearestDeliveryCenterId(int nearestDeliveryCenterId) {
        this.nearestDeliveryCenterId = nearestDeliveryCenterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
