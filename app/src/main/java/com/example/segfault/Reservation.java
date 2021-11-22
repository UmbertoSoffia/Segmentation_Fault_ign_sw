package com.example.segfault;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
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
import java.util.List;

public class Reservation extends AppCompatActivity {
    Button confirm;
    Button reject;
    Spinner n_people,sport,start,stop;
    Spinner ora;
    Spinner date;
    Spinner structure;

    private void saveInCAlendar(){
        Intent intent= new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, sport.getSelectedItem().toString()+"match");
        //qua bisognerebbe mettere indirizzo struttura
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "partita amatoriale di"+sport.getSelectedItem().toString());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,stop.getSelectedItem().toString());
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,start.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.ALL_DAY,false);

        //sta parte è se non ha app calendario
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(Reservation.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stampa il layout x prenotora
        setContentView(R.layout.reservation);
        n_people=(Spinner)findViewById(R.id.spinner_players);
        ora=(Spinner)findViewById(R.id.spinner_time);
        date=(Spinner)findViewById(R.id.spinner_date);
        structure=(Spinner)findViewById(R.id.spinner_struct);
        sport=(Spinner)findViewById(R.id.sport);
        start=(Spinner)findViewById(R.id.start);
        stop=(Spinner)findViewById(R.id.stop);
        confirm=(Button)findViewById(R.id.confirm);
        reject=(Button)findViewById(R.id.cancel);

        //butta tutti gli sport in sta lista
        ArrayList<String> List_sport=new ArrayList<>();
        ArrayList<String>List_hours=new ArrayList<>();

        // tutorial per fare spinner innestati
        ArrayAdapter adapter_sport=new ArrayAdapter(this, android.R.layout.simple_spinner_item, (List) sport);
        adapter_sport.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sport.setAdapter(adapter_sport);
        sport.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected=parent.getSelectedItem().toString();
                //qua fai la query per aggiungere
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });









        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bisogna controllare che ci sia disponibilità in quel giorno/ ora in quella struttura



                AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
                builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            saveInCAlendar();
                    }
                }).setNegativeButton("Salva senza inserire nel calendario", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //salvare roba nel db
                    }});
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
                builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setNegativeButton("Continua l'inserimento", null);
                AlertDialog alert=builder.create();
                alert.show();
            }
        });


    }

}
