package com.example.segfault;

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

public class All_structure_prom extends AppCompatActivity {
    private LinearLayout layoutList;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_structure);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutte le strutture ");
        layoutList = findViewById(R.id.layout_list_allstruct);


        //richiesta lista di strutture

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
                            ((JSONObject) response.get(i)).get("working_days").toString()

                    ));
                }

            }else{
                if( req.result.getInt("error_code") == 404){
                    AlertDialog.Builder builder=new AlertDialog.Builder(All_structure_prom.this);
                    builder.setMessage("Nessuna struttura presente").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else{
                    AlertDialog.Builder builder=new AlertDialog.Builder(All_structure_prom.this);
                    builder.setMessage("Errore richiesta 4000").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }

        } catch(Exception e){
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder=new AlertDialog.Builder(All_structure_prom.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
            AlertDialog alert=builder.create();
            alert.show();
        }
    }
    private void addView(Structure s) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s.getName());
        Button myButton1 = cricketerView.findViewById(R.id.row_structure_button);
        myButton1.setOnClickListener(view -> {
            Intent i = new Intent(All_structure_prom.this, info_struct_promo.class);
            MainActivity.struct=s;
            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }



}
