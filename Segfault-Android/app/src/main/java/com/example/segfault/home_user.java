package com.example.segfault;

import static javax.mail.Transport.send;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class home_user extends AppCompatActivity {


    private EventDay getEventDay(int day,int month,int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new EventDay(calendar, R.drawable.sample_icon);
    }


    //tutorial su come mandare mail senza aprire l'app mail
    //!! non va
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









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_user);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mettiti alla prova");



        Button new_chall=findViewById(R.id.new_match);

        new_chall.setOnClickListener(v -> {
            Intent i = new Intent(home_user.this, Reservation.class);
            i.putExtra("id", getIntent().getExtras().getString("id"));
            i.putExtra("token", getIntent().getExtras().getString("token"));
            i.putExtra("name", getIntent().getExtras().getString("name"));
            i.putExtra("email", getIntent().getExtras().getString("email"));

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



        Button partecipa=findViewById(R.id.home_user_prenota_attività);
        partecipa.setOnClickListener(v->{
            //manda alla pagina di tutte le prenotazioni del giorno selezionato
            Intent i = new Intent(home_user.this, choice_of_events.class);
            i.putExtra("id", getIntent().getExtras().getString("id"));
            i.putExtra("token", getIntent().getExtras().getString("token"));
            i.putExtra("name", getIntent().getExtras().getString("name"));
            i.putExtra("email", getIntent().getExtras().getString("email"));
            startActivity(i);

        });
        List<EventDay> events = new ArrayList<>();



        //come aggiungere eventi al calendario
        events.add(getEventDay(15,12,2021));
        events.add(getEventDay(12,1,2022));




        CalendarView calendarView =  findViewById(R.id.calendarView);
        calendarView.setEvents(events);

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {
                Calendar clickedDayCalendar = eventDay.getCalendar();
                if(events.contains(eventDay)){

                    //manda alla pagina di tutte le prenotazioni del giorno selezionato
                    Intent i = new Intent(home_user.this, list_act_user.class);
                    i.putExtra("data",clickedDayCalendar.toString());
                    i.putExtra("id", getIntent().getExtras().getString("id"));
                    i.putExtra("token", getIntent().getExtras().getString("token"));
                    i.putExtra("name", getIntent().getExtras().getString("name"));
                    i.putExtra("email", getIntent().getExtras().getString("email"));
                    startActivity(i);

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
                i.putExtra("id", getIntent().getExtras().getString("id"));
                i.putExtra("token", getIntent().getExtras().getString("token"));
                i.putExtra("name", getIntent().getExtras().getString("name"));
                i.putExtra("email", getIntent().getExtras().getString("email"));
                startActivity(i);
                return true;
            case R.id.info_utent_user:
                Intent j = new Intent(home_user.this, info_utent_user.class);
                j.putExtra("id", getIntent().getExtras().getString("id"));
                j.putExtra("token", getIntent().getExtras().getString("token"));
                j.putExtra("name", getIntent().getExtras().getString("name"));
                j.putExtra("email", getIntent().getExtras().getString("email"));
                startActivity(j);
                return true;
            case R.id.nav_structure:
                Intent k = new Intent(home_user.this, all_structure_user.class);
                k.putExtra("id", getIntent().getExtras().getString("id"));
                k.putExtra("token", getIntent().getExtras().getString("token"));
                k.putExtra("name", getIntent().getExtras().getString("name"));
                k.putExtra("email", getIntent().getExtras().getString("email"));
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
