package com.example.segfault;



import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Objects;

public class list_act_user extends AppCompatActivity {
    private LinearLayout layoutList;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_act_user);
       // Timestamp timestamp= (Timestamp) getIntent().getExtras().get("data");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        //String date = dateFormat.format(timestamp);
        Objects.requireNonNull(getSupportActionBar()).setTitle("06/12/21");
        layoutList = findViewById(R.id.list_act_user_scroll);

        for (int i = 0; i < 50; i++) {
            addView("pren"+i,1);
        }


        // capire come prendere l'id di una prenotazione per poi eliminarla quando viene schiacciato il bottone elimina



       Button myButton1 = (Button) findViewById(R.id.pop_actyvity_button);
        myButton1.setText("elimina");

        final TextView prenotazione=(TextView) findViewById(R.id.pop_actyvity);
        myButton1.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                // azione da fare per eliminare la prenotazione
                prenotazione.clearComposingText();

            }
        });




    }
    private void addView( String s,Integer id_pren) {

        final View cricketerView = getLayoutInflater().inflate(R.layout.row_popup,null,false);

        TextView editText = (TextView)cricketerView.findViewById(R.id.pop_actyvity);
        editText.setText(s);
/*sto bottone fa fallire non so xk
       Button goInfo = findViewById(R.id.info_structure_user);
       goInfo.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              /* //invio alla pagina delle info della struttura
               Intent i = new Intent(all_structure_user.this, info_struct.class);
               ///qua bisogna capire come trovare id della struttura per passarlo in input di la
               i.putExtra("id_struct", id_struct.toString());
               i.putExtra("id_user", getIntent().getExtras().get("id_user").toString());
               startActivity(i);
               Toast toast = Toast.makeText(getApplicationContext(), "Nessun evento per questa gionata", Toast.LENGTH_SHORT);
               toast.show();
           }
       });
*/
    /*
        Button myButton = new Button(this);
        myButton.setText("Push Me");

        LinearLayout ll = (LinearLayout)findViewById(R.id.test);

        LinearLayout.LayoutParams lp;
        lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        Log.d("", lp.toString());
        Log.d("", myButton.toString());
        Log.d("", ll.toString());
        ll.addView(myButton, lp);
*/
        layoutList.addView(cricketerView);

    }
}
