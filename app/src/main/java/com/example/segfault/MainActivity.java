package com.example.segfault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.Date;

public class MainActivity extends Activity {




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Button confirm = findViewById(R.id.confirm_home);
        Button register = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);

        confirm.setOnClickListener(v -> {
            EditText user = findViewById(R.id.username_login);
            EditText pwd = findViewById(R.id.pwd);
            //controlla dati
            //se utente è promotore
            if (false) {
                Intent i = new Intent(MainActivity.this, home_promo.class);
                i.putExtra("id_user", user.toString());
                startActivity(i);
                 
            } else {
                //controlla se è utente normale
                if (true) {
                    Intent i = new Intent(MainActivity.this, home_promo.class);
                    i.putExtra("id_user", user.toString());
                    startActivity(i);

                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("utente inesistente").setPositiveButton("ok",(dialog, which) -> {
                        Intent i = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(i);
                    } );
                    AlertDialog alert = builder.create();
                    alert.show();


                }
            }
        });
        cancel.setOnClickListener(v -> {
            // in toria fa refresh va testato
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            

        });
        register.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);
             
        });
        
    }




}