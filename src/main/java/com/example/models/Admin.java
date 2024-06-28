package com.example.models;

public class Admin extends User {
    private int deliveryCenterId;

/*public class Admin {
    private int adminId;
    private String name;
    private String phone;
    private int deliveryCenterId;
    private int userId;*/

    public Admin(int adminId, String name, String phone, int deliveryCenterId, int userId) {
        this.adminId = adminId;
        this.name = name;
        this.phone = phone;
        this.deliveryCenterId = deliveryCenterId;
        this.userId = userId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
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
