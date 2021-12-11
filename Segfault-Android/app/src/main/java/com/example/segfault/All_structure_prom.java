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

            FSRequest req = new FSRequest("GET", MainActivity.utente.getToken(), "api/structure", "", "promoter=" + MainActivity.utente.getCod_id() + "&token=" + MainActivity.utente.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if(res.equals("OK")){
                JSONArray response = req.array;
                for (int i = 0; i < response.length() ; i++) {
                    JSONObject obj=(JSONObject)response.get(i);
                    addView(obj.getString("name"),obj.getString("id"));

                }

                /*
                struttura del json di risposta: array di oggetti JSON: da studiare la classe JSONArray
                per capire come utilizzare questa risposta

                [
                    {
                        "_id": "61b210cfc76ba462d85e5674",
                        "structure_id": "61b210cfbf43d9d862000001",
                        "name": "Struttura 1",
                        "description": "descrizione 1",
                        "start_time": "08:30",
                        "stop_time": "19:30",
                        "address_id": "61b210cfbf43d9d862000002",
                        "number": 40,
                        "promoter_id": "1",
                        "createdAt": "2021-12-09T14:21:03.705Z",
                        "updatedAt": "2021-12-09T14:21:03.705Z",
                        "__v": 0,
                        "address": {
                            "_id": "61b210cfc76ba462d85e5675",
                            "address_id": "61b210cfbf43d9d862000002",
                            "street": "via 1",
                            "number": "",
                            "city": "",
                            "createdAt": "2021-12-09T14:21:03.774Z",
                            "updatedAt": "2021-12-09T14:21:03.774Z",
                            "__v": 0
                        }
                    },   //prima struttura
                    {},  //seconda struttura
                    {}   //terza struttura
                ]
                per avere l'indirizzo devi fare struttura_corrente.address.street
                */
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
    private void addView( String s,String id_struct) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s);
        Button myButton1 = cricketerView.findViewById(R.id.row_structure_button);
        myButton1.setOnClickListener(view -> {
            Intent i = new Intent(All_structure_prom.this, info_struct.class);
            i.putExtra("id", getIntent().getExtras().getString("id"));
            i.putExtra("token", getIntent().getExtras().getString("token"));
            i.putExtra("name", getIntent().getExtras().getString("name"));
            i.putExtra("email", getIntent().getExtras().getString("email"));
            i.putExtra("id_struct",id_struct);
            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }



}
