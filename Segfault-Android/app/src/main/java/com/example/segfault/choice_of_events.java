package com.example.segfault;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class choice_of_events extends AppCompatActivity {
    private LinearLayout layoutList;
    private ArrayList<Match> all_match = new ArrayList<Match>();

    private void req() {
        try {

            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/match", "", "token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK")) {
                JSONArray response = req.array;

                for (int i = 0; i < response.length(); i++) {
                    all_match.add(new Match(((JSONObject) response.get(i)).get("match_id").toString(),
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
            SearchView activity=findViewById(R.id.searchViewsport);
            SearchView searchView= findViewById(R.id.searchView);
            String struct= (searchView.getQuery()).toString();
            String nome=(activity.getQuery()).toString();
            layoutList.removeAllViews();
            if(struct.equals("") && nome.equals("")){
                boolean one = false;
                for (Match m : all_match) {

                    addView(m);
                    one = true;
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
                    for (Match m:all_match) {
                        //ci vuole name senno non va con id
                        if (m.struttura.equals(struct)) {
                            addView(m);
                            one = true;
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
                        for (Match m : all_match) {
                            //ci vuole name senno non va con id
                            if (m.nome.equals(nome)) {
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
                        boolean one = false;
                        for (Match m : all_match) {
                            //ci vuole name senno non va con id
                            if (m.nome.equals(nome) && m.struttura.equals(struct)) {
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

                }
            }

        } );

    }
    @SuppressLint("SetTextI18n")
    private void addView(Match match) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        int giorno=match.date.get(Calendar.DAY_OF_MONTH);
        int mese=match.date.get(Calendar.MONTH) + 1;
        int anno=match.date.get(Calendar.YEAR);
        editText.setText("nome: "+match.nome+ "\n" +giorno+"/"+mese+"/"+anno);
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
