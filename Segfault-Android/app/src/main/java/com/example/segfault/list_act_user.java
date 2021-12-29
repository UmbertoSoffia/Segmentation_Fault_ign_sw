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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

public class list_act_user extends AppCompatActivity {
    private LinearLayout layoutList;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_act_user);
        Calendar cal=MainActivity.eventDay.getCalendar();
        Objects.requireNonNull(getSupportActionBar()).setTitle(cal.get(Calendar.DAY_OF_MONTH)+"/"+cal.get(Calendar.MONTH)+"/"+cal.get(Calendar.YEAR));
        layoutList = findViewById(R.id.list_act_user_scroll);
        try {
            //dato giorno e id deve darmi tutti event giornata
            //dal.getTime ritorna la data
            FSRequest req = new FSRequest("GET", MainActivity.utente_supp.getToken(), "api/match", "", "user="+MainActivity.utente_log+ "&date="+cal.getTime()+"&token=" + MainActivity.utente_supp.getToken());
            String res = req.execute().get();

            //richiesta andata a buon fine: disegno la lista delle strutture
            if (res.equals("OK")) {
                JSONArray response = req.array;

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
                    String str=m.date.get(Calendar.DAY_OF_MONTH) + "-" + (m.date.get(Calendar.MONTH)+1) + "-" + m.date.get(Calendar.YEAR);
                    String gr=cal.get(Calendar.DAY_OF_MONTH)+ "-" + (cal.get(Calendar.MONTH)+1) + "-" + cal.get(Calendar.YEAR);
                    if(str.equals(gr))
                        addView(m);

                }
            }
        }
        catch (Exception e) {
            Log.println(Log.ERROR, "Errore connessione", e.getMessage());

            AlertDialog.Builder builder = new AlertDialog.Builder(list_act_user.this);
            builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog, which) -> {
            });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    @SuppressLint("SetTextI18n")
    private void addView(Match m) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        int giorno=m.date.get(Calendar.DAY_OF_MONTH);
        int mese=m.date.get(Calendar.MONTH) + 1;
        int anno=m.date.get(Calendar.YEAR);
        editText.setText("nome: "+m.nome+ "\n" +giorno+"/"+mese+"/"+anno);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setOnClickListener(view -> {

           MainActivity.match=m;
            Intent i = new Intent(list_act_user.this, info_match.class);
            startActivity(i);
            finish();
        });

        layoutList.addView(cricketerView);

    }
}
