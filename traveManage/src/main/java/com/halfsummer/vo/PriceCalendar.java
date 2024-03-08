package com.halfsummer.vo;


import java.util.Date;

/**
 * 图片 列表类
 */
public class PriceCalendar {
    private Date date;
    private double price;
    private double data;
    private String psid;
    private String houseTypeName;

    public String getHouseTypeName() {
        return houseTypeName;
    }

    public void setHouseTypeName(String houseTypeName) {
        this.houseTypeName = houseTypeName;
    }

    public PriceCalendar() {
    }

    public PriceCalendar(Date date, double price) {
        this.date = date;
        this.price = price;
    }

    public PriceCalendar(Date date, double price, String psid) {
        this.date = date;
        this.price = price;
        this.psid = psid;
    }
    public PriceCalendar(Date date, double price, String psid,String houseTypeName) {
        this.date = date;
        this.price = price;
        this.psid = psid;
        this.houseTypeName=houseTypeName;
    }

    public void setPsid(String psid) {
        this.psid = psid;
    }

    public String getPsid() {

        return psid;
    }

    public Date getDate() {
        return date;
    }

    public double getPrice() {
        return price;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getData() {
        return data;
    }

    public void setData(double data) {
        this.data = data;
    }
}
