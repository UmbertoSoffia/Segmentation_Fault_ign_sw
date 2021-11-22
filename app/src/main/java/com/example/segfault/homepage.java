package com.example.segfault;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.EditText;
import android.widget.LinearLayout;

public class homepage extends Activity {

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
                EditText user= findViewById(R.id.username);
                EditText pwd= findViewById(R.id.pwd);
                //controlla dati
                    //se utente è promotore
                if(true){
                    Intent i = new Intent(homepage.this, home_promot.class);
                    finish();
                    startActivity(i);
                }
                else{
                    //controlla se è utente normale
                    if(true){
                        Intent i = new Intent(homepage.this, home_user.class);
                        startActivity(i);
                    }
                    else{
                        //allert inisistente utente
                        AlertDialog.Builder builder=new AlertDialog.Builder(homepage.this);
                        builder.setMessage("utente inesistente").setPositiveButton("ok", null);
                        AlertDialog alert=builder.create();
                        alert.show();
                        //ricarica la pagina

                    }
                }

            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(homepage.this, homepage.class);
                finish();
                startActivity(i);

            }
        });


    }
}
