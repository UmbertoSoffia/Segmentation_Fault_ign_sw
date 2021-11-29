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
                finish();
            } else {
                //controlla se è utente normale
                if (true) {


                    Intent i = new Intent(MainActivity.this, home_promo.class);
                    i.putExtra("id_user", user.toString());
                    startActivity(i);
                    //finish();

                } else {
                    //allert inisistente utente
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("utente inesistente").setPositiveButton("ok", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                    //ricarica la pagina

                }
            }
        });
        cancel.setOnClickListener(v -> {
            // in toria fa refresh va testato
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);
            finish();

        });
        register.setOnClickListener(v -> {
            // inserire pagina registrazione
            /*Intent i = new Intent(MainActivity.this, registration.class);

            startActivity(i);
            finish();*/
        });
        
    }




}