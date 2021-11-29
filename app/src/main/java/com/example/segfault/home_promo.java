package com.example.segfault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

public class home_promo extends Activity {
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
            finish();
        });
        new_game.setOnClickListener(v -> {
            //qua bisogna capire come passare roba oppure fare claase parallela per creazione attività
            Intent i = new Intent(home_promo.this, create_activities.class);
            i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
            startActivity(i);
            finish();
        });

        Button backhome = findViewById(R.id.back_home_user);
        backhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DrawerLayout menu_laterale = (DrawerLayout)findViewById(R.id.drawer_layout_promo);
                menu_laterale.openDrawer(Gravity.LEFT);
            }
        });
    }
}
