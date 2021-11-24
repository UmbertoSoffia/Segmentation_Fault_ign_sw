package com.example.segfault;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;


public class info_structure extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button back=findViewById(R.id.back_info_struct);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qua non sono sicuro perchè dipende da dove si arriva
                Intent i = new Intent(info_structure.this, home_user.class);
                startActivity(i);
            }
        });
        //bidogna capire come passare info del selezionato dalla pag precedente
        //passa info queri a sta struttura
        Structure structure=new Structure();
    }
}
