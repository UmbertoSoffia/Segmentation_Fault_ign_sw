package com.example.segfault;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Match {
    String id;
    String nome;
    String struttura;
    String sport;
    GregorianCalendar date;
    String start_time;
    String stop_time;
    String creatoreid;
    String age_range;


    public Match(String id, String nome, String struttura, String sport, String date, String start_time, String stop_time, String creatoreid, String age_range) {
        this.id = id;
        this.nome = nome;
        this.struttura = struttura;
        this.sport = sport;

        String[] str=  date.split("-",date.length());
        GregorianCalendar cal= new GregorianCalendar(Integer.parseInt(str[2]),Integer.parseInt(str[1]),Integer.parseInt(str[0]));
        this.date =cal;

        this.start_time = start_time;
        this.stop_time = stop_time;
        this.creatoreid = creatoreid;
        this.age_range = age_range;
    }


    @NonNull
    @Override
    public String toString() {
        return  "nome= " + nome + "\n" +
                "sport= " + sport + "\n" +
                "date= " + date.toString() + "\n" +
                "orario= " + start_time + "-" + stop_time +"\n" +
                "età= " + age_range + "\n" ;
    }
}
