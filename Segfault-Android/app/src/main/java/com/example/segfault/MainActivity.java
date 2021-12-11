package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {


    // le richieste sono tutte GET oppure POST, le GET richiedono di compilare il campo "URLparameteres"
    // dell'oggetto FSRequest, mentre le POST richiedono di creare un oggetto JSON e metterlo nel campo
    // "json" dell'oggetto FSRequest. Entrambi i tipi di richieste restituiscono un oggetto JSON che sta
    // nel campo "result" dell' oggetto FSRequest. Se la richiesta fallisce "result" sarà null.
    // ogni richiesta restituisce la string "OK" se va a buon fine, "KO" se fallisce
    // qui trovate l'esempio del login

     static  public  User utente;
     static  public Structure struct;
     static public  Match match;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.homepage);
        Objects.requireNonNull(getSupportActionBar()).setTitle("login");
        Button confirm = findViewById(R.id.confirm_home);
        Button register = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);

        confirm.setOnClickListener(v -> {
            EditText user = findViewById(R.id.username_login);
            EditText pwd = findViewById(R.id.pwd);

            //prima richiesta: utente normale
            try {
                JSONObject ute = new JSONObject();
                ute.put("email", user.getText().toString());
                ute.put("password", pwd.getText().toString());
                ute.put("language", "it");
                FSRequest req = new FSRequest("POST", "", "api/users/authenticate/email", ute.toString(), "");
                String res = req.execute().get();

                //richiesta andata a buon fine: si logga
                if (res.equals("OK")) {
                    JSONObject response = req.result;
                    Log.println(Log.INFO, "Response", response.toString());
                    Intent i = new Intent(MainActivity.this, home_user.class);
                    utente=new User(response.getString("name"),response.getString("id"),response.getString("token"),response.getString("email"),"normal");


                    startActivity(i);
                } else { //richiesta fallita: controllo i promotori
                    JSONObject prom = new JSONObject();
                    prom.put("email", user.getText().toString());
                    prom.put("password", pwd.getText().toString());
                    prom.put("language", "it");
                    FSRequest req1 = new FSRequest("POST", "", "api/promoters/authenticate/email", prom.toString(), "");
                    String res1 = req1.execute().get();

                    // richiesta andata a buon fine: si logga
                    if(res1.equals("OK")) {
                        JSONObject response1 = req1.result;
                        Intent i = new Intent(MainActivity.this, home_promo.class);
                        utente=new User(response1.getString("name"),response1.getString("id"),response1.getString("token"),response1.getString("email"),"promotor");
                        startActivity(i);
                    }
                    else {// login fallito: utente inesistente
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Utente inesistente").setPositiveButton("ok", (dialog, which) -> {
                            Intent i = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(i);
                        });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                }

            } catch (Exception e) {
                Log.println(Log.ERROR, "Errore connessione", e.getMessage());
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Errore di connessione").setPositiveButton("ok", (dialog, which) -> {
                    Intent i = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(i);
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        cancel.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, MainActivity.class);
            startActivity(i);


        });
        register.setOnClickListener(v -> {
            Intent i = new Intent(MainActivity.this, Register.class);
            startActivity(i);

        });

    }


}