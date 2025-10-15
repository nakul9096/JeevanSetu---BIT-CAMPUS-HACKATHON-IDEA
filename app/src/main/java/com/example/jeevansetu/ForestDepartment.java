package com.example.jeevansetu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.card.MaterialCardView;

public class ForestDepartment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forest_department);
        MaterialCardView cardSensorInformation = findViewById(R.id.cardSensorInformation);
        MaterialCardView cardActiveAlerts = findViewById(R.id.cardActiveAlerts1);
        cardActiveAlerts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForestDepartment.this, Alerts.class);
                startActivity(intent);
            }
        });
        cardSensorInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ForestDepartment.this, ScannerInformation.class);
                startActivity(intent);
            }
        });
    }
}