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

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Objects;

public class info_utent_prom extends AppCompatActivity {
    private LinearLayout layoutList;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.info_utent_prom);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Utente promotore ");
        //inserimento dati personali utent prom


        TextView txt= findViewById(R.id.name_info_utent_prom);
        txt.setText(MainActivity.utente_supp.getName());
        txt=findViewById(R.id.mail_info_utent_prom);
        txt.setText(MainActivity.utente_supp.getMail());




        //inserimento delle sue strutture
        //fallisce in sto punto non riesce a beccare il layout di la per me ma bob
        layoutList = findViewById(R.id.layout_list_info_utentprom);
        //come aggiungere riga alla pagina con la stringa come testo della casella
        //il numero serve come id della struttura per poi dare valore al pulsante

        try{

            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/structure", "", "promoter=" + MainActivity.utente_supp.getCod_id() + "&token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if(res.equals("OK")){
                JSONArray response = req.array;
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj = (JSONObject) response.get(i);
                    addView(new Structure(((JSONObject) response.get(i)).get("name").toString(),
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

            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
                    builder.setMessage("Errore richiesta").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
    }
    private void addView( Structure s) {

        @SuppressLint("InflateParams") final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s.getName());
        Button myButton1 = cricketerView.findViewById(R.id.row_structure_button);
        myButton1.setOnClickListener(view -> {
            MainActivity.utente_supp=MainActivity.utente_log;
            MainActivity.struct=s;
            Intent i = new Intent(info_utent_prom.this, info_struct_promo.class);

            startActivity(i);

        });
        layoutList.addView(cricketerView);

    }

    public void onResume() {
        super.onResume();

        //cancello le strutture vecchie dalla lista

        layoutList.removeAllViews();

        //aggiorno informazioni strutture perchè potrebbero essere state modificate

        try{

            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/structure", "", "promoter=" + MainActivity.utente_supp.getCod_id() + "&token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if(res.equals("OK")){
                JSONArray response = req.array;
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj = (JSONObject) response.get(i);
                    addView(new Structure(((JSONObject) response.get(i)).get("name").toString(),
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

            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(info_utent_prom.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
    }


}
