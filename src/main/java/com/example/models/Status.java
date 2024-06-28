package com.example.models;

public class Status {
    private int statusId;
    private String description;

    public Status(int statusId, String description) {
        this.statusId = statusId;
        this.description = description;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
