package com.example.segfault;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class info_struct extends AppCompatActivity {
    private String get_wd(String[] day){
        StringBuilder ret= new StringBuilder();
        for (int i = 0; i <7; i++) {
            if(!day[i].equals(" ")) ret.append(day[i]).append(" ");

        }
        return ret.toString();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Info struttura: "+MainActivity.struct.getName());
        setContentView(R.layout.info_struct_user);


        //structure info
        String name=MainActivity.struct.getName();
        String desc=MainActivity.struct.getDesc();
        String working_days=get_wd(MainActivity.struct.getWorking_days());
        String address=MainActivity.struct.getAddress();
        String number=((Integer)MainActivity.struct.getNumber()).toString();
        String closing_time=MainActivity.struct.getStop_time();
        String opening_time=MainActivity.struct.getStart_time();
        TextView textView = findViewById(R.id.name_info_struct);
        textView.setText(name);
        textView = findViewById(R.id.desc_info_struct);
        textView.setText(desc);
        textView = findViewById(R.id.working_days);
        textView.setText(working_days);
        textView = findViewById(R.id.number_info_struct);
        textView.setText(number);
        textView = findViewById(R.id.closing_time_info_struct);
        textView.setText(closing_time);
        textView = findViewById(R.id.opening_time__info_struct);
        textView.setText(opening_time);
        textView = findViewById(R.id.street_addr_info_struct);
        textView.setText(address);

        Button more_info=findViewById(R.id.button_info_struct);
        more_info.setOnClickListener(v->{

            Intent i = new Intent(info_struct.this, info_utent_prom.class);
            startActivity(i);

        });
    }
}
