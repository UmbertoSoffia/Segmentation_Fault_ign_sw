package com.example.segfault;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;


public class Reservation extends AppCompatActivity {
    Button confirm;
    Button reject;
    Spinner spin_n_people, spin_sport;
    Spinner spin_date;
    Spinner spin_struct;


    private void saveInCAlendar(){
        //bisogna capire come passsargli la data
        Intent intent= new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, spin_sport.getSelectedItem().toString()+"match");
        //qua bisognerebbe mettere indirizzo struttura
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, spin_struct.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "partita amatoriale di"+ spin_sport.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, spin_struct.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.ALL_DAY,false);

        //sta parte è se non ha app calendario
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(Reservation.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }


    @SuppressLint("SetTextI18n")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Nuova prenotazione");

        spin_date =findViewById(R.id.spinner_date);
        spin_struct =findViewById(R.id.spinner_struct);
        spin_sport = findViewById(R.id.attività);
        spin_n_people = findViewById(R.id.spinner_num_people);
        ArrayList<String> date,n_pers,sport,struct;
        date=new ArrayList<>();
        n_pers=new ArrayList<>();
        sport=new ArrayList<>();
        struct=new ArrayList<>();
        reject= findViewById(R.id.cancel_button_reservation);
        confirm=findViewById(R.id.confirm_reservation);

        
        /*
            ordine spinner
            1-sport
            2-struttura
            3-npersone
            4-data/ora concatenate in sta maniera in sql c'è modo per concatenarle abbiamo visto in sicurezza mi pare

         */
        //qua butta tutti tipi di sport nell' arraylist sport

        sport.add("calcio");sport.add("nuoto");
        Context c=this;
        spin_sport.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,sport));
        spin_sport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                date.clear();
                n_pers.clear();
                struct.clear();
                //butta elem in Array strutturain base a quello selezionato
                //parte da 0
                String Selected=sport.get(position);
                if (position==0)
                    struct.add("campetto");
                else
                    struct.add("piscina");

                spin_struct.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,struct));
                spin_struct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        n_pers.clear();
                        date.clear();
                        //butta elem in Array n_pers in base a quello selezionato
                        //parte da 0
                        String Selected=struct.get(position);
                        n_pers.add("10");
                        n_pers.add("11");
                        spin_n_people.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,n_pers));
                        spin_n_people.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                date.clear();
                                //butta elem in Array n_pers in base a quello selezionato
                                //parte da 0
                                String Selected=n_pers.get(position);
                                if(position==0){
                                date.add("15/10");
                                date.add("18/10");
                                }
                                spin_date.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item,date));
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });











        confirm.setOnClickListener(v -> {
            // bisogna controllare che ci sia disponibilità in quel giorno/ ora in quella struttura



            AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
            builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", (dialog, which) -> {
                    saveInCAlendar();
                //salvare roba nel db
            }).setNegativeButton("Salva senza inserire nel calendario", (dialog, which) -> {
                //salvare roba nel db
                Intent i = new Intent(Reservation.this, Reservation.class);
                startActivity(i);

            });
            AlertDialog alert=builder.create();
            alert.show();
        });
        reject.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
            builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", (dialog, which) -> {
                Intent i = new Intent(Reservation.this, Reservation.class);
                startActivity(i);

            }).setNegativeButton("Continua l'inserimento", null);
            AlertDialog alert=builder.create();
            alert.show();
        });



    }

}
