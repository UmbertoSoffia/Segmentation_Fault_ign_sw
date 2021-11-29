package com.example.segfault;

import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.util.Date;

public class User {
    private final String name;
    private final String cod_fisc;
    private final String Surname;
    private final String mail;
    private final Date datexof_bird;


    public User(String name, @NonNull String cod_fisc, String surname,@NonNull String mail, Date date_of_bird) {
        this.name = name;
        Surname = surname;
        this.mail = mail;
        this.datexof_bird = date_of_bird;
        this.cod_fisc=cod_fisc;
    }

    public String getName() {
        return name;
    }

    public String getCod_fisc() {
        return cod_fisc;
    }

    public String getSurname() {
        return Surname;
    }

    public String getMail() {
        return mail;
    }

    public Date getDate_of_bird() {
        return datexof_bird;
    }
}
