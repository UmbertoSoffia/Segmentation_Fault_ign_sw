package com.example.segfault;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
    EditText desc;
    static Structure structure;
    public static int convert(String str){
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
    private void saveInDB(boolean incalendar){
        Match m = null;
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
            m=new Match(req.result.get("id").toString(),
                    match_name.getText().toString(),
                    structure.getId(),
                    spin_date.getSelectedItem().toString(),
                    spin_hour_start.getSelectedItem().toString(),
                    spin_hour_stop.getSelectedItem().toString(),
                    MainActivity.utente_log.getId(),
                    spin_min_age.getSelectedItem().toString() + "-" + spin_max_age.getSelectedItem().toString(),
                    desc.getText().toString(),
                    spin_n_people.getSelectedItem().toString());



            if (res.equals("OK")) {
                JSONObject reservation = new JSONObject();
                reservation.put("match_id", req.result.get("id"));
                reservation.put("user_id", MainActivity.utente_log.getId());
                FSRequest req2 = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/reservation", reservation.toString(), "");
                String res2 = req2.execute().get();
                if (res2.equals("OK")){
                    Toast toast = Toast.makeText(getApplicationContext(), "ti sei iscritto all'incontro", Toast.LENGTH_SHORT);
                    toast.show();

                    //aspetta due secondi e poi esce
                    Thread.sleep(2000);

                    finish();
                }
                else{
                    AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                    build.setMessage("Errore dureante l'iscrizione").setPositiveButton("Ok", (dial, whi) -> {
                    });
                    AlertDialog aler = build.create();
                    aler.show();
                }

                AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                build.setMessage("Match inserito con successo").setPositiveButton("Ok", (dial, whi) ->
                    finish());
                AlertDialog alert = build.create();
                alert.show();

            } else {
                // richiesta fallita
                if (req.result != null) {
                    int err = req.result.getInt("error_code");
                    //errore nella richiesta
                    AlertDialog.Builder build = new AlertDialog.Builder(create_activities.this);
                    build.setMessage("Errore durante l'inserimento!"+err).setPositiveButton("Ok", (dial, whi) -> {
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
        
        if(incalendar){
            Intent intent = new Intent(Intent.ACTION_EDIT);
            intent.setType("vnd.android.cursor.item/event");
            assert m != null;
            intent.putExtra(CalendarContract.Events.TITLE,m.nome);
            GregorianCalendar cal=new GregorianCalendar();

            cal.set(m.date.get(GregorianCalendar.YEAR),m.date.get(GregorianCalendar.MONTH)+1
                    ,m.date.get(GregorianCalendar.DAY_OF_MONTH),convert( spin_hour_start.getSelectedItem().toString().substring(0,2))
                    ,convert(spin_hour_start.getSelectedItem().toString().split(":",spin_hour_start.getSelectedItem().toString().length())[1]));

            GregorianCalendar cal2=new GregorianCalendar();
            cal2.set(m.date.get(GregorianCalendar.YEAR),m.date.get(GregorianCalendar.MONTH)+1
                    ,m.date.get(GregorianCalendar.DAY_OF_MONTH),convert( spin_hour_stop.getSelectedItem().toString().substring(0,2))
                    ,convert(spin_hour_stop.getSelectedItem().toString().split(":",spin_hour_stop.getSelectedItem().toString().length())[1]));

            intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, cal.getTimeInMillis());
            intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,cal2.getTimeInMillis());
            intent.putExtra(CalendarContract.Events.ALL_DAY, false);
            intent.putExtra(CalendarContract.Events.DESCRIPTION,m.desc);
            startActivity(intent);
        }
    }
    
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
        desc=findViewById(R.id.description_new_match);



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












        //fine parte richieste node
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
                GregorianCalendar stop = new GregorianCalendar(start.get(GregorianCalendar.YEAR),start.get(GregorianCalendar.MONTH) + 1, start.get(GregorianCalendar.DAY_OF_MONTH));


                String[] wd=selectedstruct.getWorking_days();
                while(start.get(GregorianCalendar.DAY_OF_MONTH) != stop.get(GregorianCalendar.DAY_OF_MONTH) || start.get(GregorianCalendar.MONTH) != stop.get(GregorianCalendar.MONTH) || start.get(GregorianCalendar.YEAR) != stop.get(GregorianCalendar.YEAR)){
                    //prendo un giorno in meno xk wd va da 0 a 6
                    int day=start.get(GregorianCalendar.DAY_OF_WEEK);

                    //se uguale a " " vuol dire che giorno di riposo
                   switch (day) {
                       case GregorianCalendar.MONDAY: if (!wd[0].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.TUESDAY: if (!wd[1].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.WEDNESDAY: if (!wd[2].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.THURSDAY: if (!wd[3].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.FRIDAY: if (!wd[4].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.SATURDAY: if (!wd[5].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;
                       case GregorianCalendar.SUNDAY: if (!wd[6].equals(" ")) date.add(start.get(GregorianCalendar.DAY_OF_MONTH) + "-" + (start.get(GregorianCalendar.MONTH)+1) + "-" + start.get(GregorianCalendar.YEAR)); break;

                   }


                    start.add(GregorianCalendar.DAY_OF_MONTH, 1);

                }

                spin_date.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, date));
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
                        //filtro i match in base al giorno cosi ne confronto meno
                        ArrayList<Match> supp=new ArrayList<>(incontri_supp);
                        for (Match m : incontri_supp) {
                            //forse troppo stringente qua bisogna testare
                            if (!(m.date.getTimeInMillis() == selected_date.getTimeInMillis())) {
                                supp.remove(m);
                            }

                        }
                        incontri_supp.clear();
                        incontri_supp.addAll(supp);
                        int ora_apertura= convert(selectedstruct.getStart_time().substring(0,2));
                        int ora_chiusura=convert(selectedstruct.getStop_time().substring(0,2));
                            for (int i = ora_apertura; i<ora_chiusura; i++) {
                                String s_ora;
                                s_ora=i+":"+selectedstruct.getStart_time().substring(3);
                                hour_start.add(s_ora);

                            }





                        spin_hour_start.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, hour_start));
                        spin_hour_start.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                n_pers.clear();
                                age_max.clear();
                                age_min.clear();
                                hour_stop.clear();
                                ArrayList<Integer>hour_supp=new ArrayList<>();


                                //elimino i periodi gia occupati
                                //per ogni incontro tolgo tutte le robe che stanno in mezzo
                                for (Match m:incontri_supp) {
                                    int m_s=convert(m.start_time.substring(0,2));
                                    hour_supp.add(m_s);
                                }
                                Collections.sort(hour_supp);
                                int stop=convert(selectedstruct.getStop_time().substring(0,2));
                                for (int i:hour_supp) {
                                    int a=convert(((String) spin_hour_start.getSelectedItem()).substring(0,2));
                                    if(a<=i)
                                        stop=i;

                                }
                                for (int i = convert(((String) spin_hour_start.getSelectedItem()).substring(0,2))+1; i<stop; i++) {
                                    String ora=i+":"+selectedstruct.getStart_time().substring(3);
                                    hour_stop.add(ora);

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
                                        spin_min_age.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, age_min));
                                        spin_min_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                            n_pers.clear();
                                            age_max.clear();
                                            for (int i = spin_min_age.getSelectedItemPosition(); i < 100; i++) {
                                                age_max.add(((Integer) i).toString());
                                            }

                                            spin_max_age.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, age_max));
                                            spin_max_age.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                                @Override
                                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                                    n_pers.clear();
                                                   //aggiunge num possiblie persone  entro i limiti struttura
                                                    for (int i = 1; i < selectedstruct.getNumber()+1; i++) {
                                                        n_pers.add(((Integer) i).toString());
                                                    }

                                                    spin_n_people.setAdapter(new ArrayAdapter<>(c, android.R.layout.simple_spinner_item, n_pers));
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
        confirm.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(create_activities.this);
            builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", (dialog, which) ->saveInDB(true)).
                    setNegativeButton("Salva senza inserire nel calendario", (dialog, which) -> saveInDB(false));
            AlertDialog alert=builder.create();
            alert.show();
        });
        reject.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(create_activities.this);
            builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", (dialog, which) -> {
                Intent i = new Intent(create_activities.this, create_activities.class);
                startActivity(i);
                finish();
            }).setNegativeButton("Continua l'inserimento", null);
            AlertDialog alert=builder.create();
            alert.show();
        });


    }
}

