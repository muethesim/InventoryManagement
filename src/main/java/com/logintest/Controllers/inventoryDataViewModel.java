package com.logintest.Controllers;

public class inventoryDataViewModel {
    private String sname;
    private int sstocks;
    private int sprice;
    private int svalue;

    public inventoryDataViewModel(String sname, int sstocks, int sprice) {
        this.sname = sname;
        this.sstocks = sstocks;
        this.sprice = sprice;
        this.svalue = sstocks * sprice;
    }
    public String getSname() {
        return sname;
    }
    public void setSname(String sname) {
        this.sname = sname;
    }
    public int getSstocks() {
        return sstocks;
    }
    public void setSstocks(int sstocks) {
        this.sstocks = sstocks;
    }
    public int getSprice() {
        return sprice;
    }
    public void setSprice(int sprice) {
        this.sprice = sprice;
    }
    public int getSvalue() {
        return svalue;
    }
    public void setSvalue(int svalue) {
        this.svalue = svalue;
    }
}
