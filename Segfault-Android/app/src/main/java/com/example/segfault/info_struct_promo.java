package com.example.segfault;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class info_struct_promo extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("informazioni /modifica struttura: "+MainActivity.struct.getName());
        setContentView(R.layout.info_struct_promo);

        //passa info queri a sta struttura utilizzando id_strutturaumberto
        String name=MainActivity.struct.getName();
        String desc=MainActivity.struct.getDesc();
        String working_days=MainActivity.struct.getWorking_days();
        String address=MainActivity.struct.getAddress();
        String number=((Integer)MainActivity.struct.getNumber()).toString();
        String closing_time=MainActivity.struct.getStop_time();
        String opening_time=MainActivity.struct.getStart_time();
        //setta i campi

        Button confirm= findViewById(R.id.confirm_create_info_struct_promo);
        TextView name_struct=findViewById(R.id.name_info_struct_promo);
        name_struct.setText(name);
        TextView addre_struct=findViewById(R.id.street_addr_info_struct_promo);
        addre_struct.setText(address);
        TextView opening= findViewById(R.id.opening_time__info_struct_promo);
        opening.setText(opening_time);
        TextView stop= findViewById(R.id.closing_time_info_struct_promo);
        stop.setText(closing_time);
         TextView work_day= findViewById(R.id.working_days_promo);
         work_day.setText(working_days);
        TextView description= findViewById(R.id.desc_info_struct_promo);
        description.setText(desc);
        TextView numberview= findViewById(R.id.number_info_struct_promo);
        numberview.setText(number);


        confirm.setOnClickListener(v -> {

            if(name_struct.getText().toString().equals("") || addre_struct.getText().toString().equals("") || stop.getText().toString().equals("") || opening.getText().toString().equals("")|| numberview.getText().toString().equals("") || work_day.getText().toString().equals("")){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setMessage("Riempire tutti i valori!").setPositiveButton("ok", null);
                AlertDialog alert=builder.create();
                alert.show();
            }else {
                //inserimento nuova struttura
                try{
                    JSONObject struct = new JSONObject();
                    struct.put("name", name_struct.getText().toString());
                    struct.put("description", description.getText().toString());
                    struct.put("start_time", opening.getText().toString());
                    struct.put("stop_time", stop.getText().toString());
                    struct.put("working_days", work_day.getText().toString());
                    struct.put("addr", addre_struct.getText().toString());
                    struct.put("number", Integer.parseInt(numberview.getText().toString()));
                    struct.put("token", MainActivity.utente_log.getToken());

                    FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/structure/", struct.toString(), "");
                    String res = req.execute().get();

                    if(res.equals("OK")){
                        // struttura inserita correttamente: refresh della pagina
                        AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                        builder.setMessage("Struttura modificata con successo").setPositiveButton("Ok", (dialog, which) -> {
                            Intent i = new Intent(info_struct_promo.this, new_structure.class);
                            startActivity(i);
                            finish();
                        });
                        AlertDialog alert=builder.create();
                        alert.show();

                    }else{
                      //errore nella richiesta
                            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                            builder.setMessage("Errore durante l'inserimento!").setPositiveButton("Ok", (dialog, which) -> {});
                            AlertDialog alert=builder.create();
                            alert.show();

                    }

                }catch(Exception e){
                    Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                    AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
                    builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
            AlertDialog.Builder builder=new AlertDialog.Builder(info_struct_promo.this);
            builder.setMessage("annullare l'inserimento").setPositiveButton("si", (dialog,which) -> {
                Intent i = new Intent(info_struct_promo.this, info_struct_promo.class);
                startActivity(i);
                finish();}).setNegativeButton("no", (dialog,which) ->{});
            AlertDialog alert=builder.create();
            alert.show();




        });











    }
}