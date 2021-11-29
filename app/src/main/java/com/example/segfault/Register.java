package com.example.segfault;


import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button confirm = findViewById(R.id.confirm_home);
        Button register = findViewById(R.id.register);
        Button cancel = findViewById(R.id.cancel);
    }
}
