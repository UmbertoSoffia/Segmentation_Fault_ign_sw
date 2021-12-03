package com.example.segfault;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.List;
import java.util.Objects;

public class home_user extends AppCompatActivity {


    private EventDay getEventDay(int day,int month,int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return new EventDay(calendar, R.drawable.sample_icon);
    }
    //https://www.tutorialspoint.com/android/android_sending_email.htm
    protected void sendEmail(String email, String msg) {
        //da provare non sono sicuro al 100% msg vada qui
        Log.i("Send email", msg);
        String[] TO = {email};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Possibile positività Covid19");
        //emailIntent.putExtra(Intent.EXTRA_TEXT, msg);

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
        Button new_chall=findViewById(R.id.new_match);

        new_chall.setOnClickListener(v -> {
            Intent i = new Intent(home_user.this, Reservation.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
            finish();

        });

        Button n_positivity=findViewById(R.id.com_positivity);
        n_positivity.setOnClickListener(v -> {

            ArrayList<Pair<User,Integer>> infected=new ArrayList<Pair<User,Integer>>();
            //buttare dentro infected quelli possibili integer serve per i toto giorni di distanza

            for (Pair<User,Integer> i:infected) {
                sendEmail(i.first.getMail(),"hai avuto un contatto con una persona positiva al COVID19 esattamente "+i.second+" giorni");
            }
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

                    Attach();
                    //Toast toast = Toast.makeText(getApplicationContext(), "devi mostrre evento", Toast.LENGTH_SHORT);
                   // toast.show();
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
            case R.id.nav_book:
                //manca la pagina per tuttte le prenotazioni
               // Intent i = new Intent(home_user.this, MainActivity.class);
               // startActivity(i);
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

    private void Attach() {
            AlertDialog.Builder dialogbuilder = new AlertDialog.Builder(this);
            final View contractPopupwiew=getLayoutInflater().inflate(R.layout.base_of_paste,null);
//da qua in poi
         View cricketerView = getLayoutInflater().inflate(R.layout.popup_activity,null,false);
         LinearLayout layoutList=findViewById(R.id.layout_list);




        layoutList.addView(cricketerView);

//sopra di questo
        dialogbuilder.setView(contractPopupwiew);
        AlertDialog dialog = dialogbuilder.create();
        dialog.show();

    }
}
