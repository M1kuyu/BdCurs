
// src/main/java/com/example/models/Client.java
package com.example.models;

public class Client {
    private int clientId;
    private String name;
    private String phone;
    private String address;
    private int nearestDeliveryCenterId;

    public Client(int clientId, String name, String phone, String address, int nearestDeliveryCenterId) {
        this.clientId = clientId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nearestDeliveryCenterId = nearestDeliveryCenterId;
    }

    // Getters and setters
}
