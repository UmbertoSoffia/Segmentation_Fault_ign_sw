package com.example.segfault;

public class Structure {
    //questo rapprestenta un insieme di immagini

    private   String name;
    private   String id;
    private  String desc;
    private  int number;
    private String Address;
    private  String start_time;
    private  String stop_time;
    private String working_days;


    public Structure(String name, String id, String desc, int number, String address, String start_time, String stop_time, String working_days) {
        this.name = name;
        this.id = id;
        this.desc = desc;
        this.number = number;
        this.Address = address;
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.working_days = working_days;
    }

    /**
     * @return number of pieple
     */
    public int getNumber() {
        return number;
    }
    /**
     * set number of pieple
     */
    public void setNumber(int number) {
        this.number = number;
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



    public String getAddress() {
        return Address;
    }




}
