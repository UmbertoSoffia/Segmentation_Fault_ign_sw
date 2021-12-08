package com.example.segfault;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class FSRequest extends AsyncTask<Void, Void, String> {

    private static final String SERVER = "http://10.0.2.2:4000/";
    final int CONNECTION_TIMEOUT = 15000;
    final int READ_TIMEOUT = 15000;

    String request_method; // metodo HTTP da utilizzare
    String route = ""; // route Node da chiamare
    String json; // eventuale json da mandare con la richiesta
    String urlParameters; // eventuali parametri per richieste di tipo text
    String token = "";
    JSONObject result; // json di risposta


    FSRequest(String method, String t, String r, String j, String param){
        request_method = method;
        route = r;
        json = j;
        token = t;
        urlParameters = param;
        result = null;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String answer; // risposta

        try{

            // connect to the server
            URL myUrl = new URL(SERVER + this.route + "?" + this.urlParameters);
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // status code
            int status;

            // risposta sempre in json
            connection.setRequestProperty("Accept", "application/json");

            // set request method
            if(this.request_method == "GET"){

                connection.setDoInput(true);
                connection.setRequestMethod("GET");

            }
            else if(this.request_method == "POST"){

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");

                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                //output stream
                OutputStream os = connection.getOutputStream();

                    byte[] input = this.json.getBytes("utf-8");
                    os.write(input);
                    os.flush();
                }

            // autenticazione

            if (this.token != "")
                connection.setRequestProperty("Authentication", "Bearer " + this.token);

            //esegui la richiesta
            connection.connect();

            //status code della risposta
            status = connection.getResponseCode();

            // richiesta andata a buon fine
            if(status <= 299){

                //input stream
                InputStream is = connection.getInputStream();

                // leggi la risposta
                BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // imposta il risultato
                result = new JSONObject(response.toString()) ;

                // richiesta andata a buon fine
                answer = "OK";

                // disconnessione
                connection.disconnect();
            }
            else{
                if (connection != null)
                    connection.disconnect();
                throw new Exception("Richiesta fallita! Errore: " + status);
            }


        }
        catch(Exception e) {
            e.printStackTrace();
            Log.println(Log.ERROR, "Errore", e.getMessage());

            // richiesta fallita
            answer = "KO";
        }


        return answer;
    }

}
