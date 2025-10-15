package com.example.jeevansetu;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import java.util.HashMap;
public class SendPanicMessage extends AppCompatActivity {
    private EditText etArea;
    private Button btnSubmit;
    private ProgressBar progress;
    private DatabaseReference dbRef;
    private String selectedArea = "Unknown Area";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_panic_message);


        etArea = findViewById(R.id.etArea);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);


        dbRef = FirebaseDatabase.getInstance().getReference("panicmessages");


        if (getIntent() != null) {
            String areaFromIntent = getIntent().getStringExtra("selectedArea");
            if (areaFromIntent != null && !areaFromIntent.isEmpty()) {
                selectedArea = areaFromIntent;
            }
        }


        etArea.setText(selectedArea);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitPanicMessage();
            }
        });
    }


    private void submitPanicMessage() {
        final String area = etArea.getText().toString().trim();
        if (area.isEmpty()) {
            Toast.makeText(this, "Area is empty, cannot submit", Toast.LENGTH_SHORT).show();
            return;
        }


        progress.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false);


        DatabaseReference messageRef = dbRef.push();
        HashMap<String, Object> data = new HashMap<>();
        data.put("area", area);
        data.put("timestamp", ServerValue.TIMESTAMP);


        messageRef.setValue(data)
                .addOnSuccessListener(aVoid -> {
                    progress.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                    Toast.makeText(SendPanicMessage.this, "Panic message submitted", Toast.LENGTH_SHORT).show();
                    finish();
                })
                .addOnFailureListener(e -> {
                    progress.setVisibility(View.GONE);
                    btnSubmit.setEnabled(true);
                    Toast.makeText(SendPanicMessage.this, "Failed to submit: " + e.getMessage(), Toast.LENGTH_LONG).show();
                });
    }
}