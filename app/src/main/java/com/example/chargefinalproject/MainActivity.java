package com.example.chargefinalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    Button fixedLineButton;
    Button chargeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chargeButton = (Button) findViewById(R.id.button_charge);
        fixedLineButton = (Button) findViewById(R.id.button_fixedline);

        chargeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChargeActivity.class);
                startActivity(intent);
            }
        });


        fixedLineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FixedLineActivity.class);
                startActivity(intent);
            }
        });

    }
}