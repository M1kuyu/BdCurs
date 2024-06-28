package com.example.models;

/*public class Courier {
    private int courierId;
    private String name;
    private String phone;
    private int deliveryCenterId;
    private int userId;*/

public class Courier extends User {
    private int deliveryCenterId;




    public Courier(int courierId, String name, String phone, int deliveryCenterId, int userId) {
        this.courierId = courierId;
        this.name = name;
        this.phone = phone;
        this.deliveryCenterId = deliveryCenterId;
        this.userId = userId;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
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

    public int getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(int deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
