package com.example.segfault;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
public class all_structure extends AppCompatActivity {
    private LinearLayout layoutlist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               setContentView(R.layout.all_structure);
        //come fare x fare elenco dinamico di tutte le strutture->https://www.youtube.com/watch?v=RHqGiWluAzU
        //https://www.youtube.com/watch?v=EJrmgJT2NnI  tutorial su come fare a mettere tutti i n fila in all_structure (in row c'è la riga ma non è giusta in teoria)
        //layoutlist=findViewById();
        Button backhome = findViewById(R.id.back_new_struct);
        backhome.setOnClickListener(v -> {

            Intent i = new Intent(all_structure.this, home_user.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
        });
        Button goInfo = findViewById(R.id.info_structure);
        goInfo.setOnClickListener(v -> {
            //invio alla pagina delle info della struttura
            Intent i = new Intent(all_structure.this, info_struct.class);
            ///qua bisogna capire come trovare id della struttura per passarlo in input di la
            i.putExtra("id_struct", "");
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
        });

    }


}
