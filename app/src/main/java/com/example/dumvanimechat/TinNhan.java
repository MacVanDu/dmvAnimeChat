package com.example.dumvanimechat;

public class TinNhan {
    private String text;
    private boolean isNguoi;

    public TinNhan(String text, boolean isNguoi) {
        this.text = text;
        this.isNguoi = isNguoi;
    }

    public String getText() {
        return text;
    }

    public boolean isNguoi() {
        return isNguoi;
    }
}
