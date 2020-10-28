package com.example.appp;

public class Memorydata {

    String id;
    String textdata;
    String datedata;
    int menudata ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDatedata() {
        return datedata;
    }

    public void setDatedata(String datedata) {
        this.datedata = datedata;
    }

    public String getTextdata() {
        return textdata;
    }

    public void setTextdata(String textdata) {
        this.textdata = textdata;
    }

    public int getMenudata() {
        return menudata;
    }

    public void setMenudata(int menudata) {
        this.menudata = menudata;
    }

    public Memorydata(String id, String textdata, String datedata, int menudata) {
        this.id = id;
        this.textdata = textdata;
        this.datedata = datedata;
        this.menudata = menudata;
    }

    public Memorydata(String textdata, String datedata, int menudata) {
        this.textdata = textdata;
        this.datedata = datedata;
        this.menudata = menudata;
    }
}
