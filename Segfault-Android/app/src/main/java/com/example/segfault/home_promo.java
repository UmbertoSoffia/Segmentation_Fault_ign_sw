package com.example.segfault;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.browse.MediaBrowser;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class home_promo extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_layout_promo);

        Button new_game = findViewById(R.id.new_match_prom);
        Button new_struct = findViewById(R.id.new_space);
        new_struct.setOnClickListener(v -> {

            Intent i = new Intent(home_promo.this, new_structure.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);

        });
        new_game.setOnClickListener(v -> {
            //qua bisogna capire come passare roba oppure fare claase parallela per creazione attività
            Intent i = new Intent(home_promo.this, create_activities.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);

        });

        Button backhome = findViewById(R.id.menu_promo);
        backhome.setOnClickListener(v -> {
            DrawerLayout menu_laterale = findViewById(R.id.drawer_layout_promo);
            menu_laterale.openDrawer(Gravity.LEFT);
        });
        DrawerLayout drawerLayout=findViewById(R.id.drawer_layout_promo);


    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_logout_promo:
                Intent i = new Intent(home_promo.this, MainActivity.class);
                startActivity(i);
                return true;

            case R.id.nav_user_promo:
                Intent j = new Intent(home_promo.this, info_utent_prom.class);
                j.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(j);

                return true;
            case R.id.nav_structure_promo:
                Intent k = new Intent(home_promo.this, All_structure_prom.class);
                k.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
                startActivity(k);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
