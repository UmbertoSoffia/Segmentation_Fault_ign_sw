package com.example.segfault;

public class Match {
    String id;
    String nome;
    String struttura;
    String sport;
    String date;
    String start_time;
    String stop_time;
    String creatoreid;
    String age_range;


    public Match(String id, String nome, String struttura, String sport, String date, String start_time, String stop_time, String creatoreid, String age_range) {
        this.id = id;
        this.nome = nome;
        this.struttura = struttura;
        this.sport = sport;
        this.date = date;
        this.start_time = start_time;
        this.stop_time = stop_time;
        this.creatoreid = creatoreid;
        this.age_range = age_range;
    }

    @Override
    public String toString() {
        return  "nome= " + nome + "\n" +
                //"struttura= " + struttura +"\n"  + // ci vorrebbe nome ma questo è id
                "sport= " + sport + "\n" +
                "date= " + date.substring(0,10) + "\n" +
                "orario= " + start_time + "-" + stop_time +"\n" +
                "età= " + age_range + "\n" ;
    }
}
