package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;

import java.util.Objects;

public class info_utent_prom extends AppCompatActivity {
    private LinearLayout layoutList;


    @Override
    protected void onCreate( Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.info_utent_prom);


        Objects.requireNonNull(getSupportActionBar()).setTitle("Utente promotore ");

        //fallisce in sto punto non riesce a beccare il layout di la per me ma bob
        layoutList = findViewById(R.id.layout_list_info_utentp);
        //String id_utent_prom=getIntent().getExtras().get("id_user").toString();
        //come aggiungere riga alla pagina con la stringa come testo della casella
        //il numero serve come id della struttura per poi dare valore al pulsante
        addView("top",1);
        addView("es",2);





    }
    private void addView( String s,Integer id_struct) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s);
//sto bottone fa fallire non so xk

       Button goInfo = (Button) cricketerView.findViewById(R.id.info_struct);
       goInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               //invio alla pagina delle info della struttura
               Intent i = new Intent(info_utent_prom.this, info_struct.class);
               ///qua bisogna capire come trovare id della struttura per passarlo in input di la
               //i.putExtra("id_struct", id_struct.toString());
               //i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
               startActivity(i);
               Toast toast = Toast.makeText(getApplicationContext(), "Nessun evento per questa gionata", Toast.LENGTH_SHORT);
               toast.show();
           }
       });


        layoutList.addView(cricketerView);

    }


}
