package com.example.segfault;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
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

    String request_method; // HTTP method
    String route; // Node route to call
    String json; // eventual json to send with the request
    String urlParameters; // eventual query string parameters
    String token; //user token
    JSONObject result; // response json object
    JSONArray array = null; // response json array


    public FSRequest(String method, String t, String r, String j, String param){
        request_method = method;
        route = r;
        json = j;
        token = t;
        urlParameters = param;
        result = null;
    }


    @Override
    protected String doInBackground(Void... voids) {

        String answer; // response

        try{

            // connect to the server
            URL myUrl = new URL(SERVER + this.route + "?" + this.urlParameters);
            HttpURLConnection connection =(HttpURLConnection) myUrl.openConnection();
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECTION_TIMEOUT);

            // status code
            int status;

            // always json response
            connection.setRequestProperty("Accept", "application/json");

            // authentication

            if (!(this.token.equals("")))
                connection.setRequestProperty("Authentication", "Bearer " + this.token);

            // set request method
            if(this.request_method.equals("GET") || this.request_method.equals("DELETE")){

                connection.setDoInput(true);
                connection.setRequestMethod(this.request_method);

            }
            else if(this.request_method.equals("POST")){

                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestMethod(this.request_method);

                connection.setRequestProperty("Content-type", "application/json; charset=utf-8");
                //output stream
                OutputStream os = connection.getOutputStream();

                    byte[] input = this.json.getBytes(StandardCharsets.UTF_8);
                    os.write(input);
                    os.flush();
                }



            //execute the request
            connection.connect();

            //response status code
            status = connection.getResponseCode();

            // request ok
            if(status <= 299){

                //input stream
                InputStream is = connection.getInputStream();

                // read response
                BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                // set result
                try {
                    result = new JSONObject(response.toString());
                }catch(JSONException je){
                    array = new JSONArray(response.toString());
                }

                // request ok
                answer = "OK";

                // disconnessione
                connection.disconnect();
            }
            else{
                if (connection != null)
                    connection.disconnect();
                result = new JSONObject();
                result.put("error_code", status);
                throw new Exception("Richiesta fallita! Errore: " + status);
            }


        }
        catch(Exception e) {
            e.printStackTrace();
            Log.println(Log.ERROR, "Errore", e.getMessage());

            // request failed
            answer = "KO";
        }


        return answer;
    }

}
