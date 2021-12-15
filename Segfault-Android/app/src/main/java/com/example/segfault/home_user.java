package com.example.segfault;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class home_user extends AppCompatActivity {
    CalendarView calendarView ;
    List<EventDay> events;
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
        Toast.makeText(getApplicationContext(),"possibil positività comunicata",Toast.LENGTH_LONG).show();
        /*
        final String Username="segfaultunive@gmail.com";
        final String pwd="segfaultunive2021";
        Properties props=new Properties();
        props.put("mail.smtp.auth","true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");

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
        }*/

    }
    private void fillCalendar(){

         events.clear();
        try{
            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/match", "", "user="+MainActivity.utente_supp.getCod_id() +"&token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK")) {
                JSONArray response = req.array;

                for (int i = 0; i < response.length(); i++) {
                    String date=((JSONObject) response.get(i)).get("date").toString();
                    String[] str=  date.split("-",date.length());
                    events.add(getEventDay(Integer.parseInt(str[2]),Integer.parseInt(str[1]),Integer.parseInt(str[0])));
                }


            }
        } catch (Exception e) {
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder = new AlertDialog.Builder(home_user.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        calendarView.setEvents(events);

    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_user);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Mettiti alla prova");
        calendarView =  findViewById(R.id.calendarView);
        events=new ArrayList<>();




        Button new_chall=findViewById(R.id.new_match);

        new_chall.setOnClickListener(v -> {
            Intent i = new Intent(home_user.this, create_activities.class);
            startActivity(i);

        });

        Button n_positivity=findViewById(R.id.com_positivity);
        n_positivity.setOnClickListener(v -> {
                //user= utennte, integer=gioni di differenza
            ArrayList<Pair<User,Integer>> infected=new ArrayList<Pair<User,Integer>>();
            //buttare dentro infected quelli possibili integer serve per i toto giorni di distanza
            infected.add(new Pair<User, Integer>(new User("id","nome","umbertosoffia00@gmail.com", null,"normal"),5));

            for (Pair<User,Integer> i:infected) {
                mail(i.first.getMail(),"hai avuto un contatto con una persona positiva al COVID19 esattamente "+i.second+" giorni");
            }
            StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        });



        Button partecipa=findViewById(R.id.home_user_prenota_attività);
        partecipa.setOnClickListener(v->{
            MainActivity.utente_supp=MainActivity.utente_log;
            //manda alla pagina di tutte le prenotazioni del giorno selezionato
            Intent i = new Intent(home_user.this, choice_of_events.class);
            startActivity(i);

        });

        fillCalendar();

        calendarView.setOnDayClickListener(new OnDayClickListener() {
            @Override
            public void onDayClick(EventDay eventDay) {

                if(events.contains(eventDay)){
                    MainActivity.utente_supp=MainActivity.utente_log;
                    MainActivity.eventDay=eventDay;

                    //manda alla pagina di tutte le prenotazioni del giorno selezionato

                    Intent i = new Intent(home_user.this, list_act_user.class);
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
        MainActivity.utente_supp=MainActivity.utente_log;
        switch (item.getItemId()) {
            case R.id.nav_logout:
                finish();
                return true;
            case R.id.nav_structure:
                Intent k = new Intent(home_user.this, all_structure_user.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        fillCalendar();
    }


}
