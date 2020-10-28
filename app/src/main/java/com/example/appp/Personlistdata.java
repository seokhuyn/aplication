package com.example.appp;

public class Personlistdata {
    private String im_profileimage;

    private String name;
    private String instrument;
    private int menu;

    public String getIm_profileimage() {
        return im_profileimage;
    }

    public void setIm_profileimage(String im_profileimage) {
        this.im_profileimage = im_profileimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInstrument() {
        return instrument;
    }

    public void setInstrument(String instrument) {
        this.instrument = instrument;
    }

    public int getMenu() {
        return menu;
    }

    public void setMenu(int menu) {
        this.menu = menu;
    }

    public Personlistdata(String im_profileimage, String name, String instrument, int menu) {
        this.im_profileimage = im_profileimage;
        this.name = name;
        this.instrument = instrument;
        this.menu = menu;
    }
}
