package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class registerAddress extends AppCompatActivity {
    Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_address);
        saveBtn = findViewById(R.id.saveAddressBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registerActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    public void skipToDashboard(View view) {
        startActivity(new Intent(getApplicationContext(), registerActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
