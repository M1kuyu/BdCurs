package com.example.models;

import java.util.Date;

public class PackageStatus {
    private int packageStatusId;
    private int statusId;
    private int packageId;
    private Date date;

    public PackageStatus(int packageStatusId, int statusId, int packageId, Date date) {
        this.packageStatusId = packageStatusId;
        this.statusId = statusId;
        this.packageId = packageId;
        this.date = date;
    }

    public int getPackageStatusId() {
        return packageStatusId;
    }

    public void setPackageStatusId(int packageStatusId) {
        this.packageStatusId = packageStatusId;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
