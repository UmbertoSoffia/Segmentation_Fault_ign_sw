package com.example.segfault;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class All_structure_prom extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_promo);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutte le strutture ");
    }
}
