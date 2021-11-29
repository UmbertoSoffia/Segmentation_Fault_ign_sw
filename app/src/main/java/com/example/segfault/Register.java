package com.example.segfault;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button confirm = findViewById(R.id.confirm_new_prom);
        Button cancel = findViewById(R.id.cancel_new_prom);
        confirm.setOnClickListener(v -> {
            String name=String.valueOf(findViewById(R.id.name_new_promo));
            String surname=String.valueOf(findViewById(R.id.surname_new_promo));
            String date=String.valueOf(findViewById(R.id.birthday_new_promo));
            String cf=String.valueOf(findViewById(R.id.cf_new_promo));
            String address=String.valueOf(findViewById(R.id.address_new_promo));
            String mail=String.valueOf(findViewById(R.id.mail_new_promo));
            String pwd1=String.valueOf(findViewById(R.id.pwd1_new_promo));
            String pwd2=String.valueOf(findViewById(R.id.pwd2_new_promo));
            if (!pwd1.equals(pwd2)){
                AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                builder.setMessage("Password differenti!");
                AlertDialog alert=builder.create();
                alert.show();
            }else{
                //inserisci roba nel db
                AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                builder.setMessage("Registrazione avvenuta con successo");
                AlertDialog alert=builder.create();
                alert.show();
                //direttamente va nell'home del promotore
                Intent i = new Intent(Register.this, home_promo.class);
                //inserisci id seriale
                i.putExtra("id_user", "");
                startActivity(i);
                finish();
            }
        });
        cancel.setOnClickListener(v->{
            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);
            finish();
        });

    }
}
