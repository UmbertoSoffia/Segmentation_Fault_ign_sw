package com.example.segfault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;

import java.util.Calendar;
import java.util.HashMap;

public class home_user extends Activity {
    CustomCalendar customCalendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stampa il layout all_struct
        setContentView(R.layout.home_user);
        Button new_chall=findViewById(R.id.new_match);
        new_chall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, Reservation.class);
                finish();
                startActivity(i);
            }
        });
        Button backhome = findViewById(R.id.back_home_user);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(home_user.this, MainActivity.class);
                startActivity(i);
            }
        });


        //queste robe qua sotto sono per i layout in base alla scritta lui colora calendario(current, present ecc)
        //bisogna mostrare il calendario
        customCalendar =findViewById(R.id.calendar);
        HashMap<Object, Property> descHashMap=new HashMap<>();
        Property defaultPropriety=new Property();
        defaultPropriety.layoutResource=R.layout.default_view_calendar;
        defaultPropriety.dateTextViewResource=R.id.text;
        descHashMap.put("default",defaultPropriety);

        //per la data corrente
        Property currentProperty=new Property();
        currentProperty.layoutResource=R.layout.current_view_calendar;
        currentProperty.dateTextViewResource=R.id.text;
        descHashMap.put("current",currentProperty);

        //per la data presente
        Property presentProperty=new Property();
        currentProperty.layoutResource=R.layout.present_view_calendar;
        currentProperty.dateTextViewResource=R.id.text;
        descHashMap.put("present",presentProperty);

        //for abset
        Property absentProprety=new Property();
        currentProperty.layoutResource=R.layout.absent_view_calendar;
        currentProperty.dateTextViewResource=R.id.text;
        descHashMap.put("absent",absentProprety);


        customCalendar.setMapDescToProp(descHashMap);

        //inizializza hashmap
        HashMap<Integer,Object> dateHashMap=new HashMap<>();
        //inizializza calendario
        Calendar calendar= Calendar.getInstance();
        //giorno in cui si è
        dateHashMap.put(calendar.get(Calendar.DAY_OF_MONTH),"current");
        // qua bisogna inserire dati nel calendario
        //si può riempire calendario solo un mese alla volta eogni volta hashmap diuversa
        //bisogna capire come fare a cambiare mese



        //esempi inserimento dati
        //giorno 1 evento confermato (colore verde) dl mese correntr
        dateHashMap.put(1,"present");
        //giorno 2 evento non confermato colorao di rosso
        dateHashMap.put(2,"absent");
        dateHashMap.put(3,"present");
        dateHashMap.put(4,"absent");
        //giono 20 presente
        dateHashMap.put(20,"present");
        dateHashMap.put(30,"absent");
        customCalendar.setDate(calendar,dateHashMap);


        customCalendar.setOnDateSelectedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(View view, Calendar selectedDate, Object desc) {
                //qua ti da la data
                String sdate=selectedDate.get(Calendar.DAY_OF_MONTH)+"/"+selectedDate.get(Calendar.MONTH)+1+"/"+selectedDate.get(Calendar.YEAR);

                //qua quando seleziona devi farevedere la descrizione evento
                Toast.makeText(getApplicationContext(),sdate,Toast.LENGTH_SHORT).show();
            }
        });





    }


}
