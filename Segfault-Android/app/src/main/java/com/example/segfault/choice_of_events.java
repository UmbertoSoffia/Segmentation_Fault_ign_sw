package com.example.segfault;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Objects;

public class choice_of_events extends AppCompatActivity {
    private LinearLayout layoutList;
    // capire come passare valori io credevo si passassero come qui sotto
    // private String date =  getIntent().getExtras().get("data").toString();
    // private String id_User=  getIntent().getExtras().get("id_user").toString();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("unisciti a noi");
        layoutList = findViewById(R.id.list_elem_serch);
        ArrayList<Match> all_match=new ArrayList<Match>();



        try{

            FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/match", "",  "token=" + MainActivity.utente.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if(res.equals("OK")){
                JSONArray response = req.array;

                for (int i = 0; i < response.length() ; i++) {
                    all_match.add((Match) response.get(i));
                }

            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(choice_of_events.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(choice_of_events.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(choice_of_events.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
        //serve route che dato ritorna tutte le attività (e che dentro ci siano i campi sport e id_struttura e i vari dati)

        Button find_with_constraint=findViewById(R.id.start_serch_activity2);
        find_with_constraint.setOnClickListener(v ->{
            layoutList.removeAllViews();
            // qua spari solo cose che ha ricercato lui
            SearchView activity=findViewById(R.id.searchViewsport);
            SearchView searchView= findViewById(R.id.searchView);
            String struct= (searchView.getQuery()).toString();
            String sport=(activity.getQuery()).toString();
            //fai controllo se ci sono eventi futuri con sti vincoli
            boolean one=false;
            for (Match m:all_match) {
                if(true){
                    addView(m);
                    one=true;
                }
            }
            if(!one) {
                //altrimenti
                AlertDialog.Builder builder = new AlertDialog.Builder(choice_of_events.this);
                builder.setMessage("Nessun evento disponibile").setPositiveButton("Ok", (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }



        } );
        Button find_without_constraint=findViewById(R.id.all_serch_activity);
        find_without_constraint.setOnClickListener(v ->{
            layoutList.removeAllViews();
            //serve route che dato ritorna tutte le attività (e che dentro ci siano i campi sport e id_struttura e i vari dati)
            // e li spari tutti
            for (Match m:all_match) {
                addView(m);
            }

        } );







    }
    private void addView(Match match) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);

        editText.setText(match.toString());
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText(R.string.partecipa);
        myButton1.setOnClickListener(view -> {


            // azione da fare per aggiungerre evento a utente
            Toast toast = Toast.makeText(getApplicationContext(), "evento confermato", Toast.LENGTH_SHORT);
            toast.show();
            //refresh qua (sottoa fatto già) per eliminare quello appena confermato
            Intent i = new Intent(choice_of_events.this, choice_of_events.class);

            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }
}
