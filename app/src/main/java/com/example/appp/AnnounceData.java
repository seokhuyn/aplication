package com.example.appp;

import android.media.Image;
import android.net.Uri;

public class AnnounceData {
    private String im_profileimage;

    private String title;
    private String money;
    private String date;
    private String address;
    private String need;
    private String arbtime;
    private  String id;

    private int menu;

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
//
//    public int getIm_profileimage() {
//        return im_profileimage;
//    }
//
//    public void setIm_profileimage(int im_profileimage) {
//        this.im_profileimage = im_profileimage;
//    }


    public String getIm_profileimage() {
        return im_profileimage;
    }

    public void setIm_profileimage(String im_profileimage) {
        this.im_profileimage = im_profileimage;
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
        return need;
    }

    public void setContents(String contents) {
        this.need = contents;
    }

    public String getArbtime() {
        return arbtime;
    }

    public void setArbtime(String arbtime) {
        this.arbtime = arbtime;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }


    public AnnounceData(String im_profileimage, String title, String money, String date, String address, String need, String arbtime, String id, int menu) {
        this.im_profileimage = im_profileimage;
        this.title = title;
        this.money = money;
        this.date = date;
        this.address = address;
        this.need = need;
        this.arbtime = arbtime;
        this.id = id;
        this.menu = menu;
    }

    public AnnounceData(String im_profileimage, String title, String money, String date, String address, String need, String arbtime) {
        this.im_profileimage = im_profileimage;
        this.title = title;
        this.money = money;
        this.date = date;
        this.address = address;
        this.need = need;
        this.arbtime = arbtime;
    }


}
