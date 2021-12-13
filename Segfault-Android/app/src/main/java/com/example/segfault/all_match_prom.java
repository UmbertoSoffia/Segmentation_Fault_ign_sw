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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class all_match_prom extends AppCompatActivity {
    private LinearLayout layoutList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_structure);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutti gli incontri ");
        layoutList = findViewById(R.id.layout_list_allstruct);


        //richiesta lista di strutture

        try{

            FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/match", "", "id=" + MainActivity.utente.getCod_id() + "&token=" + MainActivity.utente.getToken()+ "&type=promoter");
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if(res.equals("OK")){
                JSONArray response = req.array;
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj=(JSONObject)response.get(i);
                    addView(new Match(((JSONObject) response.get(i)).get("match_id").toString(),
                            ((JSONObject) response.get(i)).get("name").toString(),
                            ((JSONObject) response.get(i)).get("structure_id").toString(),
                            ((JSONObject) response.get(i)).get("sport").toString(),
                            ((JSONObject) response.get(i)).get("date").toString(),
                            ((JSONObject) response.get(i)).get("start_time").toString(),
                            ((JSONObject) response.get(i)).get("stop_time").toString(),
                            ((JSONObject) response.get(i)).get("creator_id").toString(),
                            ((JSONObject) response.get(i)).get("age_range").toString()));

                }


            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
    }
    private void addView(Match match) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);

        editText.setText(match.toString());
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText(R.string.elimina);
        myButton1.setOnClickListener(view -> {


            try {
                FSRequest req = new FSRequest("DELETE", MainActivity.utente.getToken(), "api/match"+match.id, "", "");
                String res = req.execute().get();

                if(!(res.equals("OK"))){
                    AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                    builder.setMessage("").setPositiveButton("Errore durante eliminazione", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else {
                    // azione da fare per aggiungerre evento a utente
                    Toast toast = Toast.makeText(getApplicationContext(), "Evento eliminato", Toast.LENGTH_SHORT);
                    toast.show();
                }
            } catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder=new AlertDialog.Builder(all_match_prom.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
                AlertDialog alert=builder.create();
                alert.show();
            }

            //refresh qua (sottoa fatto già) per eliminare quello appena confermato
            Intent i = new Intent(all_match_prom.this, all_match_prom.class);

            startActivity(i);
            finish();
        });


        layoutList.addView(cricketerView);

    }

}
