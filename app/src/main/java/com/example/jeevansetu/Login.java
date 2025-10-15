package com.example.jeevansetu; // Make sure this matches your actual package name

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This activity now acts as a launcher or splash screen.
 * It immediately redirects the user to the ForestDepartment activity
 * without requiring any login.
 */
public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button loginButton = findViewById(R.id.btn_login);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, ForestDepartment.class);
                startActivity(intent);
            }
        });
    }
}
