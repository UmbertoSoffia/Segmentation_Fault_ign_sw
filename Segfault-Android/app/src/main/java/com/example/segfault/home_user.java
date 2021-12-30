package com.example.segfault;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

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


        String host="mail.javatpoint.com";
        final String user="segfaultunive@gmail.com";//change accordingly
        final String password="Unive2022";//change accordingly

        String to="umbertosoffia00@gmail.com";//change accordingly

        //Get the session object
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //Compose the message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject("javatpoint");
            message.setText("This is simple program of sending email using JavaMail API");

            //send the message
            Transport.send(message);

            System.out.println("message sent successfully...");

        } catch (MessagingException e) {e.printStackTrace();}
        Toast.makeText(getApplicationContext(),"possibil positività comunicata",Toast.LENGTH_LONG).show();



    }
    private void fillCalendar(){

        events.clear();
        try{
            FSRequest req = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/reservation", "", "user=" + MainActivity.utente_log.getCod_id() + "&token=" + MainActivity.utente_log.getToken());
            String res = req.execute().get();
            ArrayList<Match> incontri=new ArrayList<Match>();

            //richiesta andata a buon fine: disegno la lista delle reservation
            if (res.equals("OK")) {
                JSONArray response = req.array;
            // scorri l'array, ogni oggetto nell'array ha un campo match che è un oggetto json
                for (int i = 0; i < response.length(); i++) {
                    JSONObject ogg=(JSONObject) ((JSONObject) response.get(i)).get("match");
                    incontri.add(new Match(ogg.get("match_id").toString(),
                            ogg.get("name").toString(),
                            ogg.get("structure_id").toString(),
                            ogg.get("date").toString(),
                            ogg.get("start_time").toString(),
                            ogg.get("stop_time").toString(),
                            ogg.get("creator_id").toString(),
                            ogg.get("age_range").toString(),
                            ogg.get("description").toString(),
                            ogg.get("number").toString())

                    );

                }
                for (Match m:incontri)
                    events.add(getEventDay(m.date.get(Calendar.DAY_OF_MONTH),m.date.get(Calendar.MONTH)+1,m.date.get(Calendar.YEAR)));





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
            try {
                JSONObject user = new JSONObject();
                user.put("user_id", MainActivity.utente_log.getId());
                FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/reservation/notify", user.toString(), "");
                String res = req.execute().get();
                if(res.equals("OK")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(home_user.this);
                    builder.setMessage("Mail inviate").setPositiveButton("Ok", (dialog, which) -> {});
                    AlertDialog alert = builder.create();
                    alert.show();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(home_user.this);
                    builder.setMessage("Errore invio mail").setPositiveButton("Ok", (dialog, which) -> {
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }

            }catch(Exception e){
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(home_user.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
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
