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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class create_activities extends AppCompatActivity {
    Button confirm ;
    Button reject;
    Spinner spin_n_people;
    Spinner spin_date;
    Spinner spin_hour_start;
    Spinner spin_hour_stop;
    Spinner spin_struct;
    Spinner spin_min_age, spin_max_age;
    TextView match_name;
    static Structure structure;
    public static int convert(String str)
    {
        int val = 0;
        System.out.println("String = " + str);

        // Convert the String
        try {
            val = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {

            // This is thrown when the String
            // contains characters other than digits
            System.out.println("Invalid String");
        }
        return val;
    }

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

        setContentView(R.layout.create_activities);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Nuovo evento");


        spin_n_people = findViewById(R.id.spinner_players_create);
        spin_date = findViewById(R.id.spinner_date_create);
        spin_struct = findViewById(R.id.spinner_struct_create);
        spin_hour_start = findViewById(R.id.start_time_create_match);
        spin_hour_stop = findViewById(R.id.stop_time_create_match);
        spin_min_age = findViewById(R.id.spinner_min_age_create);
        spin_max_age = findViewById(R.id.spinner_max_age_create);
        match_name = findViewById(R.id.sport_create_act_promo_value);


        reject = findViewById(R.id.cancel_create);
        confirm = findViewById(R.id.confirm_create);
        ArrayList<String> date, n_pers, age_min,hour_stop, age_max,  hour_start;
        date = new ArrayList<>();
        n_pers = new ArrayList<>();
        ArrayList<Structure>struct = new ArrayList<>();
        hour_start = new ArrayList<>();
        hour_stop = new ArrayList<>();
        age_max = new ArrayList<>();
        age_min = new ArrayList<>();
        ArrayList<Match> incontri= new ArrayList<>();
        ArrayList<Match> incontri_supp= new ArrayList<>();





        /*
            ordine spinner
            1-struttura
            2-data
            3-fascia oraria
            4-fascia eta
            5-npersone

         */


        Context c = this;
        //becca tutti i match
        try {
            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/match", "", "token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK")) {
                JSONArray response = req.array;

                for (int i = 0; i < response.length(); i++) {

                    incontri.add(new Match(((JSONObject) response.get(i)).get("match_id").toString(),
                            ((JSONObject) response.get(i)).get("name").toString(),
                            ((JSONObject) response.get(i)).get("structure_id").toString(),
                            ((JSONObject) response.get(i)).get("date").toString(),
                            ((JSONObject) response.get(i)).get("start_time").toString(),
                            ((JSONObject) response.get(i)).get("stop_time").toString(),
                            ((JSONObject) response.get(i)).get("creator_id").toString(),
                            ((JSONObject) response.get(i)).get("age_range").toString(),
                            ((JSONObject) response.get(i)).get("description").toString(),
                            ((JSONObject) response.get(i)).get("number").toString())

                    );

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

        if (MainActivity.utente_log.isPromoter()) {
            //richiesta strutture del promotore
            try {

                FSRequest req = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/structure", "", "promoter=" + MainActivity.utente_log.getCod_id() + "&token=" + MainActivity.utente_log.getToken());
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
                                ((JSONObject) response.get(i)).get("working_days").toString(),
                                ((JSONObject) response.get(i)).get("address_id").toString()
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
            //richiesta di tutte le strutture
            try {

                FSRequest req = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/structure", "", "token=" + MainActivity.utente_log.getToken());
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
                                ((JSONObject) response.get(i)).get("working_days").toString(),
                                ((JSONObject) response.get(i)).get("address_id").toString()

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
                hour_stop.clear();
                hour_start.clear();
                age_max.clear();
                age_min.clear();

                 Structure selectedstruct=struct.get(position);
                 structure=selectedstruct;




                GregorianCalendar start = new GregorianCalendar();
                GregorianCalendar stop = new GregorianCalendar(start.get(Calendar.YEAR),start.get(Calendar.MONTH) + 1, start.get(Calendar.DAY_OF_MONTH));

                //ce proclema con settaggio primo giorno settimana e sfasa tutto sotto dei giorni settimana
                start.setFirstDayOfWeek(Calendar.MONDAY);

                for (Match m:incontri_supp) {
                        if(!(m.struttura.equals(selectedstruct.getId())) )
                            incontri_supp.remove(m);
                }
                String[] wd=selectedstruct.getWorking_days();
                while(start.get(Calendar.DAY_OF_MONTH) != stop.get(Calendar.DAY_OF_MONTH) || start.get(Calendar.MONTH) != stop.get(Calendar.MONTH) || start.get(Calendar.YEAR) != stop.get(Calendar.YEAR)){
                    //prendo un giorno in meno xk wd va da 0 a 6
                    int day=start.get(Calendar.DAY_OF_WEEK)-1;
                    //se uguale a " " vuol dire che giorno di riposo
                    if(!wd[day].equals(" "))
                        date.add(start.get(Calendar.DAY_OF_MONTH) + "-" + (start.get(Calendar.MONTH)+1) + "-" + start.get(Calendar.YEAR));
                    start.add(Calendar.DAY_OF_MONTH, 1);

                }

                spin_date.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, date));
                spin_date.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        n_pers.clear();
                        hour_start.clear();
                        hour_stop.clear();
                        age_max.clear();
                        age_min.clear();
                        String[] supp_date = date.get(position).split("-");
                        final GregorianCalendar selected_date = new GregorianCalendar(Integer.parseInt(supp_date[2]), Integer.parseInt(supp_date[1]) - 1, Integer.parseInt(supp_date[0]));


                        //forse sta parte non serve
                        for (Match m : incontri_supp) {

                            if (!(m.date.getTimeInMillis() == selected_date.getTimeInMillis())) {
                                incontri_supp.remove(m);
                            }

                        }
                        int ora_apertura= convert(selectedstruct.getStart_time().substring(0,2));
                        int ora_chiusura=convert(selectedstruct.getStop_time().substring(0,2));
                            for (int i = ora_apertura; i<ora_chiusura; i++) {
                                String s_ora;
                                s_ora=i+":"+selectedstruct.getStart_time().substring(3);
                                hour_start.add(s_ora);

                            }





                        spin_hour_start.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, hour_start));
                        spin_hour_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                n_pers.clear();
                                age_max.clear();
                                age_min.clear();
                                hour_stop.clear();

                                for (int i = convert(((String) spin_hour_start.getSelectedItem()).substring(0,2))+1; i<ora_chiusura; i++) {
                                    String ora=i+":"+selectedstruct.getStart_time().substring(3);
                                    hour_stop.add(ora);

                                }
                                //elimino i periodi gia occupati
                                //per ogni incontro tolgo tutte le robe che stanno in mezzo
                                for (Match m:incontri_supp) {
                                    int m_s=convert(m.start_time.substring(0,2));
                                    if(m_s>= convert(((String)spin_hour_start.getSelectedItem()).substring(0,2))) {
                                        for (int i =m_s ; i <convert(m.stop_time.substring(0,2)); i++) {
                                            hour_stop.remove(i+":"+selectedstruct.getStart_time().substring(3));

                                        }
                                    }
                                }

                                spin_hour_stop.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, hour_stop));
                                spin_hour_stop.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                        n_pers.clear();
                                        age_max.clear();
                                        age_min.clear();

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
                                            for (int i = spin_min_age.getSelectedItemPosition(); i < 100; i++) {
                                                age_max.add(((Integer) i).toString());
                                            }

                                            spin_max_age.setAdapter(new ArrayAdapter<String>(c, android.R.layout.simple_spinner_item, age_max));
                                            spin_max_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    n_pers.clear();
                                                   //aggiunge num possiblie persone  entro i limiti struttura
                                                    for (int i = 0; i < selectedstruct.getNumber()+1; i++) {
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            EditText desc=findViewById(R.id.description_new_match);

                AlertDialog.Builder builder=new AlertDialog.Builder(create_activities.this);
                builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", (dialog, which) ->{}). //saveInCAlendar()).
                        setNegativeButton("Salva senza inserire nel calendario", (dialog, which) -> {
                    //inserimento match
                    try {
                        JSONObject match = new JSONObject();

                        match.put("name", match_name.getText().toString());
                        match.put("start_time", spin_hour_start.getSelectedItem().toString());
                        match.put("stop_time", spin_hour_stop.getSelectedItem().toString());
                        match.put("description", desc.getText());
                        match.put("structure_id",structure.getId());
                        match.put("date", spin_date.getSelectedItem().toString());
                        match.put("age_range", spin_min_age.getSelectedItem().toString() + "-" + spin_max_age.getSelectedItem().toString());
                        match.put("number", spin_n_people.getSelectedItem().toString());
                        match.put("creator_id", MainActivity.utente_log.getId());
                        match.put("creator_type", (MainActivity.utente_log.isPromoter() ? "promoter" : "user" ));

                        FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/match/", match.toString(), "");
                        String res = req.execute().get();

                        if (res.equals("OK")) {
                            // match inserita correttamente: refresh della pagina
                            AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                            build.setMessage("Match inserito con successo").setPositiveButton("Ok", (dial, whi) -> {
                                finish();
                            });
                            AlertDialog alert = build.create();
                            alert.show();

                        } else {
                            // richiesta fallita
                            if (req.result != null) {
                                int err = req.result.getInt("error_code");
                                //errore nella richiesta
                                AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                                build.setMessage("Errore durante l'inserimento!").setPositiveButton("Ok", (dial, whi) -> {
                                    Intent i = new Intent(create_activities.this, create_activities.class);
                                    startActivity(i);
                                    finish();
                                });
                                AlertDialog alert = build.create();
                                alert.show();
                            }
                        }

                    } catch (Exception e) {
                        Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                        AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                        build.setMessage("Errore di connessione").setPositiveButton("Ok", (dial, whi) -> {
                            Intent i = new Intent(create_activities.this, create_activities.class);
                            startActivity(i);
                            finish();
                        });
                        AlertDialog alert = build.create();
                        alert.show();
                    }

                });
                AlertDialog alert=builder.create();
                alert.show();

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

