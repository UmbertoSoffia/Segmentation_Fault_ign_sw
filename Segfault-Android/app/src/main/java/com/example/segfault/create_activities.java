package com.example.segfault;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class create_activities extends AppCompatActivity {
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
            Toast.makeText(create_activities.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
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
        ArrayList<String> date, n_pers, age_min, age_max,  hour;
        date = new ArrayList<>();
        n_pers = new ArrayList<>();
        ArrayList<Structure>struct = new ArrayList<>();
        hour = new ArrayList<>();
        age_max = new ArrayList<>();
        age_min = new ArrayList<>();
        ArrayList<Match> incontri= new ArrayList<>();
        ArrayList<Match> incontri_supp= new ArrayList<>();




        /*
            ordine spinner
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
        //becca tutti i match
        try {
            FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/match", "", "token=" + MainActivity.utente.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK")) {
                JSONArray response = req.array;

                for (int i = 0; i < response.length(); i++) {

                    incontri.add(new Match(((JSONObject) response.get(i)).get("match_id").toString(),
                            ((JSONObject) response.get(i)).get("name").toString(),
                            ((JSONObject) response.get(i)).get("structure_id").toString(),
                            ((JSONObject) response.get(i)).get("sport").toString(),
                            ((JSONObject) response.get(i)).get("date").toString(),
                            ((JSONObject) response.get(i)).get("start_time").toString(),
                            ((JSONObject) response.get(i)).get("stop_time").toString(),
                            ((JSONObject) response.get(i)).get("creator_id").toString(),
                            ((JSONObject) response.get(i)).get("age_range").toString()));

                }
            }
        }
        catch (Exception e) {
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }

        if (MainActivity.utente.isPromoter()) {
            //beccatrutture promotore
            try {

                FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/structure", "", "promoter=" + MainActivity.utente.getCod_id() + "&token=" + MainActivity.utente.getToken());
                String res = req.execute().get();

                //richiesta andata a buon fine: disegno la lista delle strutture
                if (res.equals("OK")) {
                    JSONArray response = req.array;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        struct.add(new Structure(((JSONObject) response.get(i)).get("name").toString(),
                                ((JSONObject) response.get(i)).get("structure_id").toString(),
                                ((JSONObject) response.get(i)).get("description").toString(),
                                ((JSONObject) response.get(i)).getInt("number"),
                                ((JSONObject)(obj.get("address"))).get("street").toString(),
                                ((JSONObject) response.get(i)).get("start_time").toString(),
                                ((JSONObject) response.get(i)).get("stop_time").toString(),
                                ((JSONObject) response.get(i)).get("working_days").toString()
                                ));


                    }


                } else {
                    if (req.result.getInt("error_code") == 404) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                        builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog, which) -> {
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                        builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog, which) -> {
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

            }
            catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }
        else {
            //becca tutte le strutture
            try {

                FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/structure", "", "token=" + MainActivity.utente.getToken());
                String res = req.execute().get();

                //richiesta andata a buon fine: disegno la lista delle strutture
                if (res.equals("OK")) {
                    JSONArray response = req.array;
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject obj = (JSONObject) response.get(i);
                        struct.add(new Structure(((JSONObject) response.get(i)).get("name").toString(),
                                ((JSONObject) response.get(i)).get("structure_id").toString(),
                                ((JSONObject) response.get(i)).get("description").toString(),
                                ((JSONObject) response.get(i)).getInt("number"),
                                ((JSONObject)(obj.get("address"))).get("street").toString(),
                                ((JSONObject) response.get(i)).get("start_time").toString(),
                                ((JSONObject) response.get(i)).get("stop_time").toString(),
                                ((JSONObject) response.get(i)).get("working_days").toString()

                        ));


                    }


                } else {
                    if (req.result.getInt("error_code") == 404) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                        builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog, which) -> {
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                        builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog, which) -> {
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

            }
            catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(create_activities.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        }


        ArrayList<String> sstruct=new ArrayList<>();
        for (Structure s:struct ) {
            sstruct.add(s.getName());
        }

        spin_struct.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, sstruct));
        spin_struct.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                incontri_supp.clear();
                incontri_supp.addAll(incontri);
                date.clear();
                n_pers.clear();
                hour.clear();
                age_max.clear();
                age_min.clear();

                //selected è il valore selezionato
                final Structure Selected_struct = struct.get(position);
               // LocalDate now = LocalDate.now();
              //  LocalDate localDateA=LocalDate.of(now.getYear(), now.getMonthValue()+2,now.getDayOfMonth());


                Calendar start=Calendar.getInstance();
                start.set(Calendar.YEAR, Calendar.MONTH , Calendar.DAY_OF_MONTH-1);
                Calendar stop=Calendar.getInstance();
                stop.set(Calendar.YEAR, Calendar.MONTH +1, Calendar.DAY_OF_MONTH+1);

                ArrayList<Calendar> Sdate = new ArrayList<>();
                for (Match m:incontri_supp) {
                    if(!(m.date.after(start) && m.date.before(stop)) )
                        //elimina incontri che potrebbereo ostacolare scelta x velocizzare ricerca
                        incontri_supp.remove(m);
                }
                for (Match m:incontri_supp) {
                        if(!(m.struttura.equals(Selected_struct.getId())) )
                            //elimina incontri che potrebbereo ostacolare scelta x velocizzare ricerca
                            incontri_supp.remove(m);
                }
                int tot=0;
                while(start.compareTo(stop)!=0 ||  tot==100){

                    LocalDate localDate = LocalDateTime.ofInstant(start.toInstant(), start.getTimeZone().toZoneId()).toLocalDate();
                    start.add(Calendar.DATE,1);
                    tot++;

                }






                spin_date.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, date));
                spin_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        n_pers.clear();
                        hour.clear();
                        age_max.clear();
                        age_min.clear();
                        //valore che serve poi per inserire in db
                        Calendar select_date=Sdate.get(position);
                        // in base a selected aggiungi elem a arr_list date con quelle disponibili in quella data
                       /* for (Match m:incontri_supp) {
                             for(int i =0;i<24;i++){
                                 //inserisce fasce orarereie libere
                                 if(!m.date.equals(select_date) ) {
                                     incontri_supp.remove(m);
                                 }



                                 } !m.start_time.equals(i + ":00") &&!m.start_time.equals((i+1) + ":00") ){
                                     hour.add(i+":00 -"+(i+1)+":00");

                                 }
                                 else {
                                     //elimina incontri che potrebbereo ostacolare scelta x velocizzare ricerca
                                     incontri_supp.remove(m);
                                 }
                            }

                        }


                        spin_hour.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, hour));
                        spin_hour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                n_pers.clear();
                                age_max.clear();
                                age_min.clear();
                                String Selected = hour.get(position);
                                //aggiunge tutte le eta
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
                                               //aggiunge num possiblie persone  entro i limiti struttura
                                                for (int i = 0; i < Selected_struct.getNumber()+1; i++) {
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
                        });*/
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



                AlertDialog.Builder builder=new AlertDialog.Builder(create_activities.this);
                builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", (dialog, which) ->{}). //saveInCAlendar()).
                        setNegativeButton("Salva senza inserire nel calendario", (dialog, which) -> {
                    //salvare roba nel db

                    //capire come prendere variabili
                });
                AlertDialog alert=builder.create();
                alert.show();
                Intent i = new Intent(create_activities.this, create_activities.class);
                startActivity(i);
                finish();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(create_activities.this);
                builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(create_activities.this, create_activities.class);
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

