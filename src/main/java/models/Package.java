package models;

public class Package {
    private int packageId;
    private float weight;
    private String type;
    private int senderId;
    private int receiverId;
    private int deliveryCenterId;
    private int senderCenterId;
    private int courierId;

    public Package(int packageId, float weight, String type, int senderId, int receiverId, int deliveryCenterId, int senderCenterId, int courierId) {
        this.packageId = packageId;
        this.weight = weight;
        this.type = type;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.deliveryCenterId = deliveryCenterId;
        this.senderCenterId = senderCenterId;
        this.courierId = courierId;
    }

    // Геттеры и сеттеры
    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public int getDeliveryCenterId() {
        return deliveryCenterId;
    }

    public void setDeliveryCenterId(int deliveryCenterId) {
        this.deliveryCenterId = deliveryCenterId;
    }

    public int getSenderCenterId() {
        return senderCenterId;
    }

    public void setSenderCenterId(int senderCenterId) {
        this.senderCenterId = senderCenterId;
    }

    public int getCourierId() {
        return courierId;
    }

    public void setCourierId(int courierId) {
        this.courierId = courierId;
    }
}
