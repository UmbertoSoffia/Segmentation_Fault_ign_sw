package com.example.segfault;

import androidx.annotation.NonNull;

public class User {
    private final String name;
    private final String id;
    private final String token;
    private final String mail;


    public User( @NonNull String id, String name, @NonNull String mail,String token ) {
        this.name = name;
        this.token=token;
        this.mail = mail;
        this.id=id;
    }

    public String getName() {
        return name;
    }

    public String getCod_id() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getMail() {
        return mail;
    }

}
