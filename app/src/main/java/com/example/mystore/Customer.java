package com.example.mystore;

public class Customer {
    private String strFirstName;
    private String strLastName;
    private String strAddress;
    private int avg;
    private int customerId;

    public Customer(int customerId, String strFirstName, String strLastName, String strAddress, int avg) {
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        this.strAddress = strAddress;
        this.avg = avg;
        this.customerId = customerId;
    }

    public Customer(String strFirstName, String strLastName, String strAddress, int avg) {
        this.strFirstName = strFirstName;
        this.strLastName = strLastName;
        this.strAddress = strAddress;
        this.avg = avg;
        this.customerId = 0;
    }

    public String getName() {
        return strFirstName;
    }

    public void setName(String strFirstName) {
        this.strFirstName = strFirstName;
    }

    public String getLastName() {
        return this.strLastName;
    }

    public void setLastName(String surname) {
        this.strLastName = surname;
    }

    public String getCity() {
        return this.strAddress;
    }

    public void setCity(String strAddress) {
        this.strAddress = strAddress;
    }

    public int getAvg() {
        return this.avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public long getcustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "First Name:'" + strFirstName + '\'' +
                ", Last Name:'" + strLastName + '\'' +
                ", Address:" + strAddress +
                ", avg:" + avg +
                ", customerID:" + customerId +
                '}';
    }
}

