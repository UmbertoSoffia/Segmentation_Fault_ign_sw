package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Button confirm = findViewById(R.id.confirm);
        Button register = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText user = findViewById(R.id.username);
                EditText pwd = findViewById(R.id.pwd);
                //controlla dati
                //se utente è promotore
                if (true) {
                    Intent i = new Intent(MainActivity.this, home_promot.class);
                    finish();
                    startActivity(i);
                } else {
                    //controlla se è utente normale
                    if (true) {
                        Intent i = new Intent(MainActivity.this, home_user.class);
                        startActivity(i);
                    } else {
                        //allert inisistente utente
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("utente inesistente").setPositiveButton("ok", null);
                        AlertDialog alert = builder.create();
                        alert.show();
                        //ricarica la pagina

                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, homepage.class);
                finish();
                startActivity(i);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // inserire pagina registrazione
                Intent i = new Intent(MainActivity.this, registration.class);
                finish();
                startActivity(i);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

   }