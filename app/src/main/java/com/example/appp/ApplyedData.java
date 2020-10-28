package com.example.appp;

public class ApplyedData {
    private String im_profileimage;


    private String title;
    private String money;
    private String date;
    private String address;
    private String contents;
    private  String id;

    private int menu;

    public String getIm_profileimage() {
        return im_profileimage;
    }

    public void setIm_profileimage(String im_profileimage) {
        this.im_profileimage = im_profileimage;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public ApplyedData(String im_profileimage,String title, String money, String date, String address, String contents, String id, int menu) {
        this.im_profileimage = im_profileimage;
        this.title = title;
        this.money = money;
        this.date = date;
        this.address = address;
        this.contents = contents;

        this.id = id;
        this.menu = menu;
    }

    public ApplyedData(String im_profileimage,String title, String money, String date, String address, String contents) {
        this.im_profileimage = im_profileimage;
        this.title = title;
        this.money = money;
        this.date = date;
        this.address = address;
        this.contents = contents;

    }
}
