package com.example.segfault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.drawerlayout.widget.DrawerLayout;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;

import java.util.ArrayList;

public class home_user extends Activity {
    CustomCalendar customCalendar;
    //https://www.tutorialspoint.com/android/android_sending_email.htm

    protected void sendEmail(String email, String msg) {
        Log.i("Send email", "");
        String[] TO = {email};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Possibile positività Covid19");
        emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send notification..."));
            finish();
            Log.i("Finished sending notification...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(home_user.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_user);
        Button new_chall=findViewById(R.id.new_match);

        new_chall.setOnClickListener(v -> {
            Intent i = new Intent(home_user.this, Reservation.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
            finish();

        });
        Button backhome = findViewById(R.id.menu_promo);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               DrawerLayout menu_laterale = (DrawerLayout)findViewById(R.id.home_user);
               menu_laterale.openDrawer(Gravity.LEFT);
            }
        });
        Button n_positivity=findViewById(R.id.com_positivity);
        n_positivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<Pair<User,Integer>> infected=new ArrayList<Pair<User,Integer>>();
                //buttare dentro infected quelli possibili integer serve per i toto giorni di distanza

                for (Pair<User,Integer> i:infected) {
                    sendEmail(i.first.getMail(),"hai avuto un contatto con una persona positiva al COVID19 esattamente "+i.second+" giorni");
                }
            }
        });



        //queste robe qua sotto sono per i layout in base alla scritta lui colora calendario(current, present ecc)
        //bisogna mostrare il calendario
        /*customCalendar =findViewById(R.id.calendar);
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
        });*/





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_user,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout:
                Intent i = new Intent(home_user.this, MainActivity.class);
                startActivity(i);
                return true;

            case R.id.nav_structure:
                Intent k = new Intent(home_user.this, All_structure_prom.class);
                k.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
