package com.example.models;

public class DeliveryCenter {
    private int centerId;
    private String name;
    private String address;

    public DeliveryCenter(int centerId, String name, String address) {
        this.centerId = centerId;
        this.name = name;
        this.address = address;
    }

    public int getCenterId() {
        return centerId;
    }

    public void setCenterId(int centerId) {
        this.centerId = centerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
