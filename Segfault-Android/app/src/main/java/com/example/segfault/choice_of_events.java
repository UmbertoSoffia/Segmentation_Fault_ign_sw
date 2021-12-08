package com.example.segfault;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.util.ArrayList;
import java.util.Objects;

public class choice_of_events extends AppCompatActivity {
    private LinearLayout layoutList;
    // capire come passare valori io credevo si passassero come qui sotto
    // private String date =  getIntent().getExtras().get("data").toString();
    // private String id_User=  getIntent().getExtras().get("id_user").toString();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serch_activity);

        Objects.requireNonNull(getSupportActionBar()).setTitle("unisciti a noi");
        layoutList = findViewById(R.id.list_elem_serch);


        //bisogna mettere una ricerca perche senno poco usabile
        Spinner spinner= findViewById(R.id.serch_activity);
        ArrayList<String> sport= new ArrayList<>();
        sport.add("calcio");sport.add("nuoto");

        //qua butta tutti tipi di sport nell' arraylist sport
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,sport);
        spinner.setAdapter(adapter);
        Button find_with_constraint=findViewById(R.id.start_serch_activity2);
        find_with_constraint.setOnClickListener(v ->{
            // qua spari solo cose che ha ricercato lui
            String activity=spinner.getSelectedItem().toString();
            SearchView searchView= findViewById(R.id.searchView);
            String struct= String.valueOf(searchView.getQuery());

            layoutList.removeAllViews();
            for (int i = 0; i < 5; i++) {
                addView("query"+i);
            }
        } );
        Button find_without_constraint=findViewById(R.id.all_serch_activity);
        find_without_constraint.setOnClickListener(v ->{
            // qua spari tutti eventi

            layoutList.removeAllViews();
            for (int i = 0; i < 10; i++) {
                addView("pren"+i);
            }
        } );







    }
    private void addView( String s) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = cricketerView.findViewById(R.id.pop_actyvity);
        editText.setText(s);
        Button myButton1 = cricketerView.findViewById(R.id.pop_actyvity_button);
        myButton1.setText(R.string.partecipa);
        myButton1.setOnClickListener(view -> {


            // azione da fare per aggiungerre evento a utente
            Toast toast = Toast.makeText(getApplicationContext(), "evento confermato", Toast.LENGTH_SHORT);
            toast.show();
            //refresh qua (sottoa fatto già) per eliminare quello appena confermato
            Intent i = new Intent(choice_of_events.this, choice_of_events.class);
            i.putExtra("id", getIntent().getExtras().get("id").toString());
            startActivity(i);
        });


        layoutList.addView(cricketerView);

    }
}
