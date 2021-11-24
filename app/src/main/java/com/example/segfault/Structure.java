package com.example.segfault;

import android.media.Image;

import java.util.ArrayList;

public class Structure {
    //questo rapprestenta un insieme di immagini

    private   String name;
    private   Integer id;
    private  String desc;
    private String mail;
    private  String number;
    private String Address;
    private String  phone;

    public Structure(String name, Integer id, String desc, String mail, String number, String address, String phone) {
        this.name = name;
        this.id = id;
        this.desc = desc;
        this.mail = mail;
        this.number = number;
        Address = address;
        this.phone = phone;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getMail() {
        return mail;
    }

    public String getNumber() {
        return number;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return phone;
    }

    public Integer getId() {
        return id;
    }


}
