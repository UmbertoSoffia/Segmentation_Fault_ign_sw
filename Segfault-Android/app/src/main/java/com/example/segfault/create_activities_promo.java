package com.example.segfault;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class create_activities_promo extends AppCompatActivity {
    Button confirm ;
    Button reject;
    Spinner spin_n_people, spin_sport;
    Spinner spin_date;
    Spinner spin_ora;
    Spinner spin_struct;
    Spinner spin_min_age, spin_max_age;

    /*private void saveInCAlendar(){
        //bisogna capire come passsargli la data
        Intent intent= new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, sport.getSelectedItem().toString()+"match");
        //qua bisognerebbe mettere indirizzo struttura
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "partita amatoriale di"+sport.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.ALL_DAY,false);

        //sta parte è se non ha app calendario
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(create_activities_promo.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }*/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stampa il layout x prenotora
        setContentView(R.layout.create_activities);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Nuovo evento");


        spin_n_people=findViewById(R.id.spinner_players_create);
        spin_date=findViewById(R.id.spinner_date_create);
        spin_struct=findViewById(R.id.spinner_struct_create);
        spin_ora=findViewById(R.id.fascia_ora_create);
        spin_min_age=findViewById(R.id.spinner_min_age_create);
        spin_max_age=findViewById(R.id.spinner_max_age_create);


        reject= findViewById(R.id.cancel_create);
        confirm=findViewById(R.id.confirm_create);
        ArrayList<String> date,n_pers,sport,struct;
        date=new ArrayList<>();
        n_pers=new ArrayList<>();
        struct=new ArrayList<>();




        /*
            ordine spinner
            1-sport
            2-struttura
            3-npersone
            4-data
            5-fascia oraria

         */

        final ArrayList<String> query=new ArrayList<>();
        query.add(0," query sport");
        query.add(1,"Structure");
        query.add(2,"n_people");
        query.add(3,"date");
        query.add(4,"fascia_oraria");
        //nested spinner
        //nestedSpinner(list_spin,query,0,null);




























        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bisogna controllare che ci sia disponibilità in quel giorno/ ora in quella struttura
                //salcvare prenotazione nel db
                //salvare nuovo incontro nel db
                String id_user;



                AlertDialog.Builder builder=new AlertDialog.Builder(create_activities_promo.this);
                builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", (dialog, which) ->{}). //saveInCAlendar()).
                        setNegativeButton("Salva senza inserire nel calendario", (dialog, which) -> {
                    //salvare roba nel db
                });
                AlertDialog alert=builder.create();
                alert.show();
                Intent i = new Intent(create_activities_promo.this, create_activities_promo.class);

                startActivity(i);
                finish();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(create_activities_promo.this);
                builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(create_activities_promo.this, create_activities_promo.class);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("Continua l'inserimento", null);
                AlertDialog alert=builder.create();
                alert.show();
            }
        });


    }
}
