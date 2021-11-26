package com.example.segfault;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;


public class Reservation extends AppCompatActivity {
    Button confirm = (Button)findViewById(R.id.confirm);
    Button reject;
    Spinner n_people,sport;
    Spinner date;
    Spinner structure;

    private void nestedSpinner(ArrayList<Spinner> lst,ArrayList<String>query,int step,String value){
        if (step==3) return;
        ArrayList<String>element=new ArrayList<>();
        //aggiungi a elementmtutti i campi utilizzando la query[step]
        if (step==0){
            //prendi elem da query[0] e buttali in element
            
        }else{
            //altimenti utilizza campo selezionato dallo spinner precedente
            //sto campo viene passato in value e butta tutto in element
        }
        //https://www.youtube.com/watch?v=svCVKC7ByOE&t=385s x tutoril spinner innestati forse manca pezzo ma non penso
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,element);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lst.get(step).setAdapter(adapter);
        //spinner in posizione step
        lst.get(step).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selected=parent.getSelectedItem().toString();
                nestedSpinner(lst,query,step+1,selected);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void saveInCAlendar(){
        //bisogna capire come passsargli la data
        Intent intent= new Intent(Intent.ACTION_INSERT);
        intent.setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.Events.TITLE, sport.getSelectedItem().toString()+"match");
        //qua bisognerebbe mettere indirizzo struttura
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "partita amatoriale di"+sport.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,structure.getSelectedItem().toString());
        intent.putExtra(CalendarContract.Events.ALL_DAY,false);

        //sta parte è se non ha app calendario
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }else{
            Toast.makeText(Reservation.this, "There is no app that support this action", Toast.LENGTH_SHORT).show();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //stampa il layout x prenotora
        setContentView(R.layout.reservation);

        date=findViewById(R.id.spinner_date);
        structure=findViewById(R.id.spinner_struct);
        sport= findViewById(R.id.attività);


        reject= findViewById(R.id.cancel);
        confirm=findViewById(R.id.confirm);
        ArrayList<Spinner> list_spin = new ArrayList<>();
        list_spin.add(0,sport);
        list_spin.add(1,structure);
        list_spin.add(2,date);

        
        /*
            ordine spinner
            1-sport
            2-struttura
            3-npersone
            4-data/ora concatenate in sta maniera in sql c'è modo per concatenarle abbiamo visto in sicurezza mi pare

         */
     
        final ArrayList<String> query=new ArrayList<>();
        query.add(0," query sport");
        query.add(1,"Structure");
        query.add(2,"date");

        //nested spinner
        nestedSpinner(list_spin,query,0,null);







        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // bisogna controllare che ci sia disponibilità in quel giorno/ ora in quella struttura



                AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
                builder.setMessage("Salvare evento nel calendario?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            saveInCAlendar();
                    }
                }).setNegativeButton("Salva senza inserire nel calendario", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //salvare roba nel db
                    }});
                AlertDialog alert=builder.create();
                alert.show();
            }
        });
        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder=new AlertDialog.Builder(Reservation.this);
                builder.setMessage("Annullare l'inserimento?").setPositiveButton("Sì", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //qua va refreshata la pagina
                    }
                }).setNegativeButton("Continua l'inserimento", null);
                AlertDialog alert=builder.create();
                alert.show();
            }
        });


    }

}
