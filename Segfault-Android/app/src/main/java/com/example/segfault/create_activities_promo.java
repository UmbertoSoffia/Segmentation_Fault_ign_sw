package com.example.segfault;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Objects;

public class create_activities_promo extends AppCompatActivity {
    Button confirm ;
    Button reject;
    Spinner spin_n_people;
    Spinner spin_date;
    Spinner spin_hour;
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


        spin_n_people = findViewById(R.id.spinner_players_create);
        spin_date = findViewById(R.id.spinner_date_create);
        spin_struct = findViewById(R.id.spinner_struct_create);
        spin_hour = findViewById(R.id.fascia_ora_create);
        spin_min_age = findViewById(R.id.spinner_min_age_create);
        spin_max_age = findViewById(R.id.spinner_max_age_create);


        reject = findViewById(R.id.cancel_create);
        confirm = findViewById(R.id.confirm_create);
        ArrayList<String> date, n_pers, age_min, age_max, struct, hour;
        date = new ArrayList<>();
        n_pers = new ArrayList<>();
        struct = new ArrayList<>();
        hour = new ArrayList<>();
        age_max = new ArrayList<>();
        age_min = new ArrayList<>();




        /*
            ordine spinner
            1-sport
            2-struttura
            3-data
            4-npersone
            5-fascia eta
            6-fascia oraria
         */

        TextView textView = findViewById(R.id.sport_create_act_promo_value);
        //nome attiviita
        String Activity = textView.getText().toString();

        Context c = this;


        // inserisci tutte le strutture dell'utente in sto arraylist
        struct.add("st1");
        struct.add("st2");
        struct.add("st3");

        spin_struct.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, struct));
        spin_struct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                date.clear();
                n_pers.clear();
                hour.clear();
                age_max.clear();
                age_min.clear();

                //selected è il valore selezionato
                final String Selected_struct = struct.get(position);
                // in base a selected aggiungi elem a arr_list date con quelle disponibili
                date.add("15/10");
                date.add("21/12");

                spin_date.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, date));
                spin_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        n_pers.clear();
                        hour.clear();
                        age_max.clear();
                        age_min.clear();
                        //selected è il valore selezionato
                        String Selected = date.get(position);
                        // in base a selected aggiungi elem a arr_list date con quelle disponibili in quella data
                        hour.add("10:00-11:00");
                        hour.add("14:00-15:00");


                        spin_hour.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, hour));
                        spin_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                n_pers.clear();
                                age_max.clear();
                                age_min.clear();
                                //selected è il valore selezionato
                                String Selected = hour.get(position);
                                // in base a selected aggiungi elem a arr_list date con quelle disponibili
                                for (int i = 0; i < 100; i++) {
                                    age_min.add(((Integer) i).toString());
                                }

                                spin_min_age.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, age_min));
                                spin_min_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        n_pers.clear();
                                        age_max.clear();
                                        for (int i = position; i < 100; i++) {
                                            age_max.add(((Integer) i).toString());
                                        }

                                        spin_max_age.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, age_min));
                                        spin_max_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                n_pers.clear();
                                                //serve capire la capienza della stanza selezionata in base a
                                                int tot=10;
                                                //utilizza sta variabile per prendere tot
                                                //Selected_struct;
                                                for (int i = 0; i < 100; i++) {
                                                    n_pers.add(((Integer) i).toString());
                                                }

                                                spin_n_people.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, n_pers));
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

