package com.example.segfault;

import static javax.mail.Transport.send;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.mail.Authenticator;
import  javax.mail.Message;
import  javax.mail.MessagingException;
import  javax.mail.PasswordAuthentication;
import  javax.mail.Session;
import  javax.mail.Transport;
import javax.mail.internet.AddressException;
import  javax.mail.internet.InternetAddress;
import  javax.mail.internet.MimeMessage;
import java.util.Properties;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

import io.reactivex.annotations.NonNull;

public class home_user extends AppCompatActivity {


    private EventDay getEventDay(int day,int month,int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new EventDay(calendar, R.drawable.sample_icon);
    }
    //https://www.youtube.com/watch?v=roruU4hVwXA
    private void mail(String email, String msg){
        final String Username="segfaultunive@gmail.com";
        final String pwd="segfaultunive2021";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        Toast.makeText(getApplicationContext(),"bona",Toast.LENGTH_LONG).show();
        Session session= Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return  new PasswordAuthentication(Username,pwd);
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setFrom( new InternetAddress(Username));

            //message.setRecipient(Message.RecipientType.TO,InternetAddress.parse(email) );
            message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
            message.setSubject(" Possibile positivita covid19");
            //message.setText(msg);
            send(message);
            Toast.makeText(getApplicationContext(),"mail inviata",Toast.LENGTH_LONG).show();

        } catch (MessagingException e) {
            throw new RuntimeException();
        }



    }






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
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mettiti alla prova");

        JSONObject jsonObject= new JSONObject();
        try {
            String cf=(String)jsonObject.get("cf");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // esempio di richiesta al server (route families share)
        /*try {
            FSRequest req = new FSRequest("GET","FS", "api/users/id:1", "","");
            String res = req.execute().get();
            Log.d("res", res);
            if (req.result != null)
                Log.d("json", req.result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // esempio di richiesta al server (POST TEXT)
        /*try {
            FSRequest req = new FSRequest("POST","TEXT", "", "","user=1&pwd=abc");
            String res = req.execute().get();
            Log.d("res", res);
            if (req.result != null)
                Log.d("json", req.result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        // esempio di richiesta al server (POST JSON)
        /*try {
            JSONObject j = new JSONObject();
            j.put("user", 1);
            j.put("pwd", "abc");
            FSRequest req = new FSRequest("POST","JSON", "", j.toString(),"");
            String res = req.execute().get();
            Log.d("res", res);
            if (req.result != null)
                Log.d("json", req.result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/
/*
        // esempio di richiesta al server (GET TEXT)
        try {
            FSRequest req = new FSRequest("GET","TEXT", "", "","user=1&pwd=abc");
            String res = req.execute().get();
            Log.d("res", res);
            if (req.result != null)
                Log.d("json", req.result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }*/

        Button new_chall=findViewById(R.id.new_match);

        new_chall.setOnClickListener(v -> {
            Intent i = new Intent(home_user.this, Reservation.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
            finish();

        });

        Button n_positivity=findViewById(R.id.com_positivity);
        n_positivity.setOnClickListener(v -> {
                //user= utennte, integer=gioni di differenza
            ArrayList<Pair<User,Integer>> infected=new ArrayList<Pair<User,Integer>>();
            //buttare dentro infected quelli possibili integer serve per i toto giorni di distanza
            infected.add(new Pair<User, Integer>(new User("umberto","cf",null, "umbertosoffia00@gmail.com",null),5));

            for (Pair<User,Integer> i:infected) {
                mail(i.first.getMail(),"hai avuto un contatto con una persona positiva al COVID19 esattamente "+i.second+" giorni");
            }
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        });
        //questa e la libreria https://github.com/Applandeo/Material-Calendar-View
        //insieme di eventi
        List<EventDay> events = new ArrayList<>();



        //aggiungere eventi al calendario
        events.add(getEventDay(15,12,2021));
        events.add(getEventDay(12,1,2022));




        CalendarView calendarView =  findViewById(R.id.calendarView);
        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                if(events.contains(eventDay)){

                    Attach(clickedDayCalendar.getTime());

                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Nessun evento per questa gionata", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


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
            case R.id.info_utent_user:
                Intent j = new Intent(home_user.this, info_utent_user.class);
                startActivity(j);
                return true;
            case R.id.nav_structure:
                Intent k = new Intent(home_user.this, all_structure_user.class);
                k.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
//manda alla pagina di tutte le prenotazioni
    private void Attach(Date data) {

        Intent i = new Intent(home_user.this, list_act_user.class);
        i.putExtra("data",data.getTime());
        i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
        startActivity(i);


    }
}
