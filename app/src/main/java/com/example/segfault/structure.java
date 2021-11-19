package com.example.segfault;

import android.media.Image;

import java.util.ArrayList;

public class structure {
    //questo rapprestenta un insieme di immagini
    private ArrayList<Image> images;
    private   String name;
    private   Integer id;
    public  String desc;

    public structure(String name, Integer id, String desc) {
        this.name = name;
        this.id = id;
        this.desc = desc;
    }
    public ArrayList<Image> getImages(){
        return new ArrayList<Image>(images);
    }
    public void Addimage(Image i){
        this.images.add(i);
    }

    public Integer getId() {
        return id;
    }


}
