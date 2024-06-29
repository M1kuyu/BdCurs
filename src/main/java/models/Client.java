package models;

public class Client extends User {
    private String name;
    private String phone;
    private String address;
    private int nearestDeliveryCenterId;

    public Client(int userId, String login, String password, String name, String phone, String address, int nearestDeliveryCenterId) {
        super(userId, login, password, UserType.CLIENT);
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.nearestDeliveryCenterId = nearestDeliveryCenterId;
    }

    // Геттеры и сеттеры
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
}
