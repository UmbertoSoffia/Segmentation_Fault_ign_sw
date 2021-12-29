package com.example.segfault;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class info_match extends AppCompatActivity {
    Match match;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        match=MainActivity.match;


        setContentView(R.layout.info_match);
        Objects.requireNonNull(getSupportActionBar()).setTitle("informazioni evento "+match.nome);
        TextView range= findViewById(R.id.range_info_match);
        range.setText(match.age_range);
        TextView name= findViewById(R.id.name_info_match);
        name.setText(match.nome);
        TextView number= findViewById(R.id.number_info_match);
        number.setText(match.number);
        TextView time= findViewById(R.id.time_info_match);
        String bin=match.start_time+" - "+match.stop_time;
        time.setText(bin);
        TextView data= findViewById(R.id.data_info_match);
        String str=match.date.get(Calendar.DAY_OF_MONTH) + "-" + (match.date.get(Calendar.MONTH)+1) + "-" + match.date.get(Calendar.YEAR);
        data.setText(str);
        TextView stru= findViewById(R.id.struct_nam_info_match);
        stru.setText(match.struttura);
        TextView desc= findViewById(R.id.desc_info_match);
        desc.setText(match.desc);

        Button button=findViewById(R.id.button_info_match);

        if(MainActivity.match.creatoreid.equals(MainActivity.utente_log.getCod_id()) && MainActivity.utente_log.isPromoter()){
            String s="elimina evento";
            button.setText(s);
            button.setBackgroundColor(Color.RED);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                    builder.setMessage("eliminare l'incontro?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ////////elimina incontro
                            try{

                                FSRequest req = new FSRequest("DELETE", MainActivity.utente_log.getToken(), "api/match/" + match.id, "", "token=" + MainActivity.utente_log.getToken());
                                String res = req.execute().get();
                                if (res.equals("OK")){
                                    Toast toast = Toast.makeText(getApplicationContext(), "incontro eliminato", Toast.LENGTH_SHORT);
                                    toast.show();

                                    //aspetta due secondi e poi esce
                                    Thread.sleep(2000);

                                    finish();
                                }
                                else{
                                    AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                    build.setMessage("Errore dureante la disiscrizione").setPositiveButton("Ok", (dial, whi) -> {
                                    });
                                    AlertDialog aler = build.create();
                                    aler.show();
                                }


                            }catch(Exception e){
                                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                                AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                build.setMessage("Errore di connessione").setPositiveButton("Ok", (dial, whi) -> {
                                });
                                AlertDialog aler = build.create();
                                aler.show();
                            }
                        }

                    }).setNegativeButton("annulla", null);
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                });


        }
        else{
            ArrayList<Match> incontri=new ArrayList<Match>();
            try{
                FSRequest req = new FSRequest("GET", MainActivity.utente_log.getToken(), "api/reservation", "", "user=" + MainActivity.utente_log.getCod_id() + "&token=" + MainActivity.utente_log.getToken());
                String res = req.execute().get();


                //richiesta andata a buon fine: disegno la lista delle reservation
                if (res.equals("OK")) {
                    JSONArray response = req.array;
                    // scorri l'array, ogni oggetto nell'array ha un campo match che è un oggetto json
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject ogg=(JSONObject) ((JSONObject) response.get(i)).get("match");
                        incontri.add(new Match(ogg.get("match_id").toString(),
                                ogg.get("name").toString(),
                                ogg.get("structure_id").toString(),
                                ogg.get("date").toString(),
                                ogg.get("start_time").toString(),
                                ogg.get("stop_time").toString(),
                                ogg.get("creator_id").toString(),
                                ogg.get("age_range").toString(),
                                ogg.get("description").toString(),
                                ogg.get("number").toString())

                        );

                    }



                }
            }
            catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
                });
                AlertDialog alert = builder.create();
                alert.show();
            }




            //controllare se utente registrato in mathc
            if(incontri.contains(match)) {
                // se iscritto
                String s="disiscrivimi";
                button.setText(s);
                button.setBackgroundColor(Color.RED);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                        builder.setMessage("Confermi?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ////////disiscrivi utente_log


                                try{
                                    JSONObject reservation = new JSONObject();
                                    reservation.put("match_id", match.id);
                                    reservation.put("user_id", MainActivity.utente_log.getId());
                                    FSRequest req = new FSRequest("DELETE", MainActivity.utente_log.getToken(), "api/reservation", reservation.toString(), "token=" + MainActivity.utente_log.getToken());
                                    String res = req.execute().get();
                                    if (res.equals("OK")){
                                        Toast toast = Toast.makeText(getApplicationContext(), "ti sei disiscritto dall'incontro", Toast.LENGTH_SHORT);
                                        toast.show();

                                        //aspetta due secondi e poi esce
                                        Thread.sleep(2000);

                                        finish();
                                    }
                                    else{
                                        AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                        build.setMessage("Errore dureante la disiscrizione").setPositiveButton("Ok", (dial, whi) -> {
                                        });
                                        AlertDialog aler = build.create();
                                        aler.show();
                                    }


                                }catch(Exception e){
                                    Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                                    AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                    build.setMessage("Errore di connessione").setPositiveButton("Ok", (dial, whi) -> {
                                    });
                                    AlertDialog aler = build.create();
                                    aler.show();
                                }
                            }

                        }).setNegativeButton("annulla", null);
                        AlertDialog alert = builder.create();
                        alert.show();




                    }
                });
            }else {
                String s="iscrivimi";
                button.setText(s);
                button.setBackgroundColor(Color.GREEN);
                final boolean[] ris = {true};
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        AlertDialog.Builder builder = new AlertDialog.Builder(info_match.this);
                        builder.setMessage("Confermi?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ////////iscrivi utente_log
                                try{
                                    JSONObject reservation = new JSONObject();
                                    reservation.put("match_id", match.id);
                                    reservation.put("user_id", MainActivity.utente_log.getId());
                                    FSRequest req = new FSRequest("POST", MainActivity.utente_log.getToken(), "api/reservation", reservation.toString(), "");
                                    String res = req.execute().get();
                                    if (res.equals("OK")){
                                        Toast toast = Toast.makeText(getApplicationContext(), "ti sei iscritto all'incontro", Toast.LENGTH_SHORT);
                                        toast.show();

                                        //aspetta due secondi e poi esce
                                        Thread.sleep(2000);

                                        finish();
                                    }
                                    else{
                                        AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                        build.setMessage("Errore dureante l'iscrizione").setPositiveButton("Ok", (dial, whi) -> {
                                        });
                                        AlertDialog aler = build.create();
                                        aler.show();
                                    }


                                }catch(Exception e){
                                    Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                                    AlertDialog.Builder build = new AlertDialog.Builder(info_match.this);
                                    build.setMessage("Errore di connessione").setPositiveButton("Ok", (dial, whi) -> {
                                    });
                                    AlertDialog aler = build.create();
                                    aler.show();
                                }




                            }

                        }).setNegativeButton("no", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                });


            }
        }




    }
}
