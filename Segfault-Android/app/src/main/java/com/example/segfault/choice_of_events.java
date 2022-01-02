package com.example.segfault;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Objects;

public class choice_of_events extends AppCompatActivity {
    public static class PAir<F,S>{
        F first;
        S second;

        public PAir(F first, S second) {
            this.first = first;
            this.second = second;
        }
    }
    private LinearLayout layoutList;

    ArrayList<PAir<Match,Integer>>all_match=new ArrayList<>();
    ArrayList<Structure>all_str=new ArrayList<>();




    private void req() {
        try {

            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/match", "", "token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();
            FSRequest req2 = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/structure", "",  "&token=" + MainActivity.utente_log.getToken());
            String res2 = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK") && res2.equals("OK") ) {
                JSONArray response = req.array;
                JSONArray response2 = req2.array;


                for (int i = 0; i < response2.length(); i++) {
                    JSONObject obj = (JSONObject) response2.get(i);
                    all_str.add(new Structure(((JSONObject) response2.get(i)).get("name").toString(),
                            ((JSONObject) response2.get(i)).get("structure_id").toString(),
                            ((JSONObject) response2.get(i)).get("description").toString(),
                            ((JSONObject) response2.get(i)).getInt("number"),
                            ((JSONObject)(obj.get("address"))).get("street").toString(),
                            ((JSONObject) response2.get(i)).get("start_time").toString(),
                            ((JSONObject) response2.get(i)).get("stop_time").toString(),
                            ((JSONObject) response2.get(i)).get("working_days").toString(),
                            ((JSONObject) response2.get(i)).get("address_id").toString()

                    ));

                }

                for (int i = 0; i < response.length(); i++) {
                    Match m=new Match(((JSONObject) response.get(i)).get("match_id").toString(),
                            ((JSONObject) response.get(i)).get("name").toString(),
                            ((JSONObject) response.get(i)).get("structure_id").toString(),
                            ((JSONObject) response.get(i)).get("date").toString(),
                            ((JSONObject) response.get(i)).get("start_time").toString(),
                            ((JSONObject) response.get(i)).get("stop_time").toString(),
                            ((JSONObject) response.get(i)).get("creator_id").toString(),
                            ((JSONObject) response.get(i)).get("age_range").toString(),
                            ((JSONObject) response.get(i)).get("description").toString(),
                            ((JSONObject) response.get(i)).get("number").toString());
                    boolean insert=false;

                    for (PAir<Match,Integer> cop:all_match) {
                        if(cop.first.equals(m)){
                            cop.second=cop.second+1;
                            insert=true;
                            break;
                        }


                    }
                    if(!insert)all_match.add(new PAir<>(m,1));



                }

            } else {
                if (req.result.getInt("error_code") == 404) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog, which) -> {
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog, which) -> {
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }

        } catch (Exception e) {
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("unisciti a noi");
        layoutList = findViewById(R.id.list_elem_serch);
        req();




        Button find_without_constraint=findViewById(R.id.all_serch_activity);
        find_without_constraint.setOnClickListener(v ->{
            EditText activity=findViewById(R.id.searchViewsport);
            EditText searchView= findViewById(R.id.searchView);
            String struct= searchView.toString();
            String nome=activity.toString();
            layoutList.removeAllViews();
            if(struct.equals("") && nome.equals("")){
                boolean one = false;
                for (PAir<Match,Integer>  m : all_match) {
                    if(m.second<create_activities.convert(m.first.number)) {
                        addView(m);
                        one = true;
                    }
                }
                if (!one) {
                    //altrimenti
                    AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                    builder.setMessage("Nessun evento disponibile ").setPositiveButton("Ok", (dialog, which) -> {
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
            else{
                if(!struct.equals("") && nome.equals("")){
                    boolean one=false;
                    for (Structure s:all_str) {
                        if (s.getName().equals(struct)){
                            for (PAir<Match,Integer>  m:all_match) {
                                if (m.first.struttura.equals(s.getId()) && m.second<create_activities.convert(m.first.number)) {
                                    addView(m);
                                    one = true;
                                }
                            }

                        }


                    }



                    if(!one) {
                        //altrimenti
                        AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                        builder.setMessage("Nessun evento disponibile ").setPositiveButton("Ok", (dialog, which) -> {
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
                else {

                    if(struct.equals("") && !nome.equals("")) {
                        boolean one = false;
                        for (PAir<Match,Integer>  m : all_match) {
                            if (m.first.nome.equals(nome) && m.second<create_activities.convert(m.first.number)) {
                                addView(m);
                                one = true;
                            }
                        }
                        if (!one) {
                            //altrimenti
                            AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                            builder.setMessage("Nessun evento disponibile ").setPositiveButton("Ok", (dialog, which) -> {
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                    else{
                        boolean one=false;
                        for (Structure s:all_str) {
                            if (s.getName().equals(struct)){
                                for (PAir<Match,Integer>  m:all_match) {
                                    if (m.first.nome.equals(nome) && m.first.struttura.equals(s.getId()) && m.second<create_activities.convert(m.first.number)) {
                                        addView(m);
                                        one = true;
                                    }
                                }

                            }

                        }
                        if (!one) {
                            //altrimenti
                            AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                            builder.setMessage("Nessun evento disponibile ").setPositiveButton("Ok", (dialog, which) -> {
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }

                }
            }

        } );

    }
    @SuppressLint("SetTextI18n")
    private void addView(PAir<Match,Integer>  m) {
        Match match=m.first;
        GregorianCalendar calendar= new GregorianCalendar();
        calendar.set(GregorianCalendar.HOUR,0);
        calendar.set(GregorianCalendar.MINUTE,0);
        calendar.set(GregorianCalendar.SECOND,0);
        calendar.set(GregorianCalendar.MILLISECOND,0);

        if(match.date.before(calendar) ) return;

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        int giorno=match.date.get(Calendar.DAY_OF_MONTH);
        int mese=match.date.get(Calendar.MONTH) + 1;
        int anno=match.date.get(Calendar.YEAR);
        editText.setText("nome: "+match.nome+ "\ndata: " +giorno+"/"+mese+"/"+anno+"\nnumero partecipanti: "+m.second);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText("informazioni");
        myButton1.setOnClickListener(view -> {
            MainActivity.match=match;
            Intent i = new Intent(choice_of_events.this, info_match.class);
            startActivity(i);
            finish();
        });


        layoutList.addView(cricketerView);

    }
}
