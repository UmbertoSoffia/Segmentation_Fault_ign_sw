package com.example.segfault;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class info_utent_prom extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_utent_prom);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Utente promotore ");

    }
}
