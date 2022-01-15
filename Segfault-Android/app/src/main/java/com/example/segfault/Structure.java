package com.example.segfault;

public class Structure {

    private final String name;
    private final String id;
    private  String desc;
    private  int number;
    private final String Address;
    private final String start_time;
    private final String stop_time;
    private final String working_days;
    private final String address_id;


    public Structure(String name, String id, String desc, int number, String address, String start_time, String stop_time, String wd, String addr_id) {
        this.name = name;
        this.id = id;
        this.desc = desc;
        this.number = number;
        this.Address = address;
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.working_days = wd;
        this.address_id = addr_id;
    }

    public String getStart_time() {
        return start_time;
    }

    public String getStop_time() {
        return stop_time;
    }

    public String[] getWorking_days() {
        return   working_days.split("-",working_days.length());



    }

    /**
     * @return number of people
     */
    public int getNumber() {
        return number;
    }
    /**
     * set number of people
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
    public String getId(){
        return this.id;
    }
    public String getDesc() {
        return desc;
    }



    public String getAddress() {
        return Address;
    }

    public String getAddress_id() {
        return address_id;
    }




}
