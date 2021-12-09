package com.example.segfault;

import static com.example.segfault.R.id.nav_user_promo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class home_promo extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_promo);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Definisci i tuoi passi ");



        Button new_game = findViewById(R.id.new_match_prom);
        Button new_struct = findViewById(R.id.new_space);
        new_struct.setOnClickListener(v -> {

            Intent i = new Intent(home_promo.this, new_structure.class);
            startActivity(i);

        });
        new_game.setOnClickListener(v -> {
            //qua bisogna capire come passare roba oppure fare claase parallela per creazione attività
            Intent i = new Intent(home_promo.this, create_activities.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);

        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater= getMenuInflater();
        inflater.inflate(R.menu.menu_promo,menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout_promo:
                Intent i = new Intent(home_promo.this, MainActivity.class);
                startActivity(i);
                return true;

            case nav_user_promo:

                Intent j = new Intent(home_promo.this, info_utent_prom.class);

                //j.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(j);

                return true;
            case R.id.nav_structure_promo:
                Intent k = new Intent(home_promo.this, All_structure_prom.class);
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
