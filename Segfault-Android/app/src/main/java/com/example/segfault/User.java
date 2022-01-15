package com.example.segfault;

public class User {
    private final String name;
    private final String id;
    private final String token;
    private final String mail;
    private final String type;


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public User(String name, String id, String token, String mail, String type) {
        this.name = name;
        this.id = id;
        this.token = token;
        this.mail = mail;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    /**
     * @return true if the user is a promoter
     */
    public boolean isPromoter(){return type.equals("promoter");}

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
