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
        //passare info tra le ganine ->https://www.youtube.com/watch?v=eL69kj-_Wvs

        //passa info queri a sta struttura
        String name="";
        Integer id=0;
        String desc="";
        String mail="";
        String number="";
        String address="";
        String phone="";
        Structure structure=new Structure(name,id,desc,mail,number,address,phone);

        //come fare x fare elenco dinamico di tutte le strutture->https://www.youtube.com/watch?v=RHqGiWluAzU
    }
}
