package com.example.segfault;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class new_structure extends AppCompatActivity {
    static Integer convert(String str)
    {
        int val = 0;
        System.out.println("String = " + str);

        // Convert the String
        try {
            val = Integer.parseInt(str);
        }
        catch (NumberFormatException e) {

           return null;
        }
        return val;
    }
    static boolean control(String start_time, String stop_time){

        if(start_time.length()!=5 || stop_time.length()!=5)  return false;
        Integer start_h=convert(start_time.substring(0,2));
        Integer start_m=convert(start_time.substring(3,5));
        if(start_m==null || start_h==null) return false;
        if(start_h<0 || start_m<0 || start_m>60 ||start_h>24) return false;
        Integer stop_h=convert(stop_time.substring(0,2));
        Integer stop_m=convert(stop_time.substring(3,5));
        if(stop_m==null || stop_h==null) return false;
        if(stop_h<0 || stop_m<0 || stop_m>60 ||stop_h>24) return false;
        if(stop_h<start_h) {
            return false;}
        if(stop_h.equals(start_h) && stop_m>start_m)return false;


        return true;
    }



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_struct);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Inserimento nuova strutttura");

        Button confirm = findViewById(R.id.confirm_new_prom);
        Button cancel = findViewById(R.id.cancel_new_prom);
        TextView name_struct=findViewById(R.id.name_new_struct);
        TextView addre_struct=findViewById(R.id.Addre_new_struct);
        TextView opening= findViewById(R.id.start_time_new_struct);
        TextView stop= findViewById(R.id.stop_time_new_struct);
        TextView work_day= findViewById(R.id.work_days_new_struct);
        TextView description= findViewById(R.id.desc_new_struct);
        TextView number= findViewById(R.id.number_struct);


        confirm.setOnClickListener(v -> {


            if(name_struct.getText().toString().equals("") || addre_struct.getText().toString().equals("") || stop.getText().toString().equals("") || opening.getText().toString().equals("")|| number.getText().toString().equals("") || work_day.getText().toString().equals("")){
                AlertDialog.Builder builder=new AlertDialog.Builder(new_structure.this);
                builder.setMessage("Riempire tutti i valori!").setPositiveButton("ok", null);
                AlertDialog alert=builder.create();
                alert.show();
            }else {
                if(convert(number.getText().toString())==null || convert(number.getText().toString())<0){
                    AlertDialog.Builder builder=new AlertDialog.Builder(new_structure.this);
                    builder.setMessage("numero persone negativo!").setPositiveButton("ok", null);
                    AlertDialog alert=builder.create();
                    alert.show();
                }
                else {


                    if (!control(opening.getText().toString(), stop.getText().toString())) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                        builder.setMessage("orario non corretto \nutilizzare hh:mm oppure controllare che il periodo sia corretto").setPositiveButton("ok", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                    } else {

                        //inserimento nuova struttura
                        try {
                            JSONObject struct = new JSONObject();
                            struct.put("name", name_struct.getText().toString());
                            struct.put("description", description.getText().toString());
                            struct.put("start_time", opening.getText().toString());
                            struct.put("stop_time", stop.getText().toString());
                            struct.put("working_days", work_day.getText().toString());
                            struct.put("addr", addre_struct.getText().toString());
                            struct.put("number", Integer.parseInt(number.getText().toString()));
                            struct.put("token", MainActivity.utente_log.getToken());

                            FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/structure/", struct.toString(), "");
                            String res = req.execute().get();

                            if (res.equals("OK")) {
                                // struttura inserita correttamente: refresh della pagina
                                AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                                builder.setMessage("Struttura inserita con successo").setPositiveButton("Ok", (dialog, which) -> {
                                    Intent i = new Intent(new_structure.this, new_structure.class);
                                    startActivity(i);
                                    finish();
                                });
                                AlertDialog alert = builder.create();
                                alert.show();

                            } else {
                                // richiesta fallita
                                if (req.result != null) {
                                    int err = req.result.getInt("error_code");
                                    //struttura già esistente
                                    if (err == 409) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                                        builder.setMessage("Struttura già esistente!").setPositiveButton("Ok", (dialog, which) -> {
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    } else { //errore nella richiesta
                                        AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                                        builder.setMessage("Errore durante l'inserimento!").setPositiveButton("Ok", (dialog, which) -> {
                                        });
                                        AlertDialog alert = builder.create();
                                        alert.show();
                                    }
                                } else {//errore nella richiesta
                                    AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                                    builder.setMessage("Errore durante l'inserimento!").setPositiveButton("Ok", (dialog, which) -> {
                                    });
                                    AlertDialog alert = builder.create();
                                    alert.show();
                                }
                            }

                        } catch (Exception e) {
                            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                            AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
                            });
                            AlertDialog alert = builder.create();
                            alert.show();
                        }
                    }
                }
            }

        });

        cancel.setOnClickListener(v -> {
            AlertDialog.Builder builder=new AlertDialog.Builder(new_structure.this);
            builder.setMessage("annullare l'inserimento").setPositiveButton("si", (dialog,which) -> {
                Intent i = new Intent(new_structure.this, new_structure.class);
                startActivity(i);
                finish();

            }).setNegativeButton("no", (dialog,which) ->{});
            AlertDialog alert=builder.create();
            alert.show();

        });
    }
}
