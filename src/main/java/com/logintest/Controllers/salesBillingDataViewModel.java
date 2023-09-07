package com.logintest.Controllers;

public class salesBillingDataViewModel {
    private String name;
    private int price;
    private int qnty;
    private int total;

    public salesBillingDataViewModel(String name, int price, int qnty) {
        this.name = name;
        this.price = price;
        this.qnty = qnty;
        this.total = price*qnty;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getQnty() {
        return qnty;
    }
    public int getTotal() {
        return total;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setQnty(int qnty) {
        this.qnty = qnty;
    }
    public void setTotal(int total) {
        this.total = total;
    }
}