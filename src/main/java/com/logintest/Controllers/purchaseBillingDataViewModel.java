package com.logintest.Controllers;

public class purchaseBillingDataViewModel {
    private String name;
    private int price;
    private int qnty;
    private int total;
    private int sprice;

    public purchaseBillingDataViewModel(String name, int price, int qnty, int sprice) {
        this.name = name;
        this.price = price;
        this.qnty = qnty;
        this.sprice = sprice;
        this.total = price*qnty;
    }

    public String getName() {
        return name;
    }
    public int getPrice() {
        return price;
    }
    public int getSprice() {
        return sprice;
    }
    public void setSprice(int sprice) {
        this.sprice = sprice;
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