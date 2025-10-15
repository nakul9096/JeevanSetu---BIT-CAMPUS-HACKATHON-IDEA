package com.example.jeevansetu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.card.MaterialCardView;

public class Users extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This single line correctly sets your layout below the action bar
        // and above the navigation bar.
        setContentView(R.layout.activity_users);

        // --- Find all the views from your XML ---
        TextView locationTextView = findViewById(R.id.textViewLocation);
        MaterialCardView alertsCard = findViewById(R.id.cardActiveAlerts);
        MaterialCardView panicCard = findViewById(R.id.cardPanicEmergency);
        MaterialCardView helplineCard = findViewById(R.id.cardHelpline);

        // --- Get the selected area from the previous screen ---
        Intent intent = getIntent();
        String selectedArea = intent.getStringExtra("SELECTED_AREA");

        // --- Update the location TextView ---
        if (selectedArea != null && !selectedArea.isEmpty()) {
            locationTextView.setText(selectedArea);
        } else {
            locationTextView.setText("Default Area"); // Fallback text
        }

        // --- Set OnClickListeners for the cards ---
        alertsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, AlertUsers.class);
                startActivity(intent);
            }
        });

        panicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, SendPanicMessage.class);
                startActivity(intent);
            }
        });

        helplineCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Users.this, EmergencyContacts.class);
                startActivity(intent);
            }
        });
    }
}