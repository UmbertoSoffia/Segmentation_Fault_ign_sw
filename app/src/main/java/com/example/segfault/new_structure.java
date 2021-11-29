package com.example.segfault;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class new_structure extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_promo);
        Button back=findViewById(R.id.back_new_struct);
        Button confirm = findViewById(R.id.confirm_new_prom);
        Button cancel = findViewById(R.id.cancel_new_prom);
        TextView name_struct=findViewById(R.id.name_new_struct);
        TextView addre_struct=findViewById(R.id.Addre_new_struct);
        TextView opening=findViewById(R.id.start_time_new_struct);
        TextView stop=findViewById(R.id.stop_time_new_struct);
        TextView work_day=findViewById(R.id.work_days_new_struct);
        TextView description=findViewById(R.id.desc_new_struct);
        confirm.setOnClickListener(v -> {
            if(name_struct.toString().equals("") || addre_struct.toString().equals("") || stop.toString().equals("") || opening.toString().equals("")|| work_day.toString().equals("")){
                AlertDialog.Builder builder=new AlertDialog.Builder(new_structure.this);
                builder.setMessage("Riempire tutti i valori!");
                AlertDialog alert=builder.create();
                alert.show();
            }else {
                ArrayList<String> name_struct_prom = new ArrayList<>();
                name_struct_prom.add("qua fai query e butta dentro tutti nome strutture dell'utente che fa la richiesta");
                if(name_struct_prom.contains(name_struct.toString())){
                    AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                    builder.setMessage("Nome struttura già presente nel tuo elenco");
                    AlertDialog alert = builder.create();
                    alert.show();

                }
                 else{
                    //butta roba nel db le robe le hai sopra
                    AlertDialog.Builder builder = new AlertDialog.Builder(new_structure.this);
                    builder.setMessage("Inserimento confermato");
                    AlertDialog alert = builder.create();
                    alert.show();
                    //verificare se la pagina si refresha
                    Intent i = new Intent(new_structure.this, new_structure.class);
                    i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                    startActivity(i);
                     
                 }
            }




        });
        cancel.setOnClickListener(v -> {
            //verificare se la pagina si refresha
            Intent i = new Intent(new_structure.this, new_structure.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
             
        });
        back.setOnClickListener(v->{

            Intent i = new Intent(new_structure.this, home_promo.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
             
        });
    }
}
