package com.example.segfault;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.GregorianCalendar;

public class Match {
    String id;
    String nome;
    String struttura;
    GregorianCalendar date;
    String start_time;
    String stop_time;
    String creatoreid;
    String age_range;
    String number;
    String desc;


    public Match(String id, String nome, String struttura, String date, String start_time, String stop_time, String creatoreid, String age_range,String desc,String number) {
        this.id = id;
        this.nome = nome;
        this.struttura = struttura;
        this.number=number;
        this.desc=desc;

        String[] str=  date.split("-",date.length());
        GregorianCalendar cal= new GregorianCalendar(Integer.parseInt(str[2]),Integer.parseInt(str[1]) - 1,Integer.parseInt(str[0]));
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
                "date= " + date.toString() + "\n" +
                "orario= " + start_time + "-" + stop_time +"\n" +
                "età= " + age_range + "\n" ;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if(!(obj instanceof Match)) return super.equals(obj);
        Match m=(Match) obj;

        return this.id.equals(m.id);

    }
}
