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

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

/*import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import org.naishadhparmar.zcustomcalendar.CustomCalendar;
import org.naishadhparmar.zcustomcalendar.OnDateSelectedListener;
import org.naishadhparmar.zcustomcalendar.Property;*/

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class home_user extends AppCompatActivity {
    
    //https://www.tutorialspoint.com/android/android_sending_email.htm

    @SuppressLint("IntentReset")
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
///        https://developer.android.com/reference/java/util/Calendar



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
                Intent k = new Intent(home_user.this, All_structure_prom.class);
                k.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
