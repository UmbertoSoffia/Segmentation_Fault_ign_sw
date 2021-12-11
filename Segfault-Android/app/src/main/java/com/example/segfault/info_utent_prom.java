package com.example.segfault;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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
        txt.setText("nome");
        txt=findViewById(R.id.ind_info_utent_prom);
        txt.setText("indirizzo");
        txt=findViewById(R.id.mail_info_utent_prom);
        txt.setText("mail");




        //inserimento delle sue strutture
        //fallisce in sto punto non riesce a beccare il layout di la per me ma bob
        layoutList = findViewById(R.id.layout_list_info_utentprom);
        //come aggiungere riga alla pagina con la stringa come testo della casella
        //il numero serve come id della struttura per poi dare valore al pulsante
        addView("top",1);
        addView("es",2);





    }
    private void addView( String s,Integer id_struct) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_structure,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.nome_struct);
        editText.setText(s);
        Button myButton1 = cricketerView.findViewById(R.id.row_structure_button);
        myButton1.setOnClickListener(view -> {
            Intent i = new Intent(info_utent_prom.this, info_struct.class);
            startActivity(i);
        });
        layoutList.addView(cricketerView);

    }


}
