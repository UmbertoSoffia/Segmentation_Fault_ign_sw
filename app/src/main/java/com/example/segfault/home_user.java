package com.example.segfault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class home_user extends Activity {
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
                Intent i = new Intent(home_user.this, homepage.class);
                startActivity(i);
            }
        });

        //bisogna mostrare il calendario


    }


}
