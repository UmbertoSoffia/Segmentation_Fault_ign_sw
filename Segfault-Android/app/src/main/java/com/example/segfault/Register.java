package com.example.segfault;


import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Registrati");
        Button confirm = findViewById(R.id.confirm_new_prom);
        Button cancel = findViewById(R.id.cancel_new_prom);
        confirm.setOnClickListener(v -> {
            EditText name=findViewById(R.id.name_new_promo);
            EditText address=findViewById(R.id.address_new_promo);
            EditText mail=findViewById(R.id.mail_new_promo);
            EditText pwd1=findViewById(R.id.pwd1_new_promo);
            EditText pwd2=findViewById(R.id.pwd2_new_promo);
            String p1 = pwd1.getText().toString();
            String p2 = pwd2.getText().toString();

            if (!(p1.equals(p2))){
                AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                builder.setMessage("Password differenti!");
                AlertDialog alert=builder.create();
                alert.show();
            }else{
                //registrazione nuovo promotore
                try{

                    JSONObject prom = new JSONObject();
                    prom.put("name", name.getText().toString());
                    prom.put("addr", address.getText().toString());
                    prom.put("email", mail.getText().toString());
                    prom.put("password", pwd1.getText().toString());
                    prom.put("language", "it");

                    FSRequest req = new FSRequest("POST", "", "api/promoters", prom.toString(), "");
                    String res = req.execute().get();

                    if(res.equals("OK")){
                        //torna alla pagina di login
                        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                        builder.setMessage("Registrazione avvenuta con successo").setPositiveButton("Ok", (dialog, which) -> {
                            Intent i = new Intent(Register.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        });
                        AlertDialog alert=builder.create();
                        alert.show();
                    } else{
                        AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                        builder.setMessage("Errore durante la registrazione").setPositiveButton("Ok", (dialog,which) -> {});
                        AlertDialog alert=builder.create();
                        alert.show();
                    }

                }catch(Exception e){
                    Log.println(Log.ERROR, "Errore connessione", e.getMessage());

                    AlertDialog.Builder builder=new AlertDialog.Builder(Register.this);
                    builder.setMessage("Errore di connessione").setPositiveButton("Ok", (dialog,which) -> {});
                    AlertDialog alert=builder.create();
                    alert.show();
                }
            }
        });

        cancel.setOnClickListener(v->{
            Intent i = new Intent(Register.this, MainActivity.class);
            startActivity(i);
            finish();
             
        });

    }
}
