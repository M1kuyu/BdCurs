package models;

public class Courier extends User {
    private String name;
    private String phone;
    private int deliveryCenterId;

    public Courier(int userId, String login, String password, String name, String phone, int deliveryCenterId) {
        super(userId, login, password, UserType.COURIER);
        this.name = name;
        this.phone = phone;
        this.deliveryCenterId = deliveryCenterId;
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

    public int getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(int deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }
}
