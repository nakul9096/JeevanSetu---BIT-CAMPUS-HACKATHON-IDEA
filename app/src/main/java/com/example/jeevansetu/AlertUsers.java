package com.example.jeevansetu;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;

public class AlertUsers extends AppCompatActivity {

    private static final String ESP32_URL = "http://192.168.4.1/status";
    private static final long POLLING_INTERVAL_MS = 2000;

    private Handler handler;
    private RequestQueue requestQueue;
    private MediaPlayer mediaPlayer;

    private LinearLayout ongoingAlertContainer;
    private TextView noAlertsTextView;

    // Removed OBJECT state as it's no longer needed
    private enum AlertState { NONE, RFID }
    private AlertState currentAlertState = AlertState.NONE;
    private String currentAlertUID = "No Card Yet";

    // Simplified DeviceStatus to only include UID
    private static class DeviceStatus {
        final String uid;
        DeviceStatus(String uid) {
            this.uid = uid;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alert_users);

        ongoingAlertContainer = findViewById(R.id.ongoingAlertContainer);
        noAlertsTextView = findViewById(R.id.textViewNoAlerts);

        requestQueue = Volley.newRequestQueue(this);
        handler = new Handler(Looper.getMainLooper());

        mediaPlayer = MediaPlayer.create(this, R.raw.alert_sound);
        mediaPlayer.setLooping(true);
    }

    private final Runnable pollingRunnable = new Runnable() {
        @Override
        public void run() {
            fetchDeviceStatus();
            handler.postDelayed(this, POLLING_INTERVAL_MS);
        }
    };

    private void fetchDeviceStatus() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, ESP32_URL,
                response -> {
                    DeviceStatus status = parseStatusFromJson(response);
                    processServerResponse(status);
                },
                // Simplified error response
                error -> processServerResponse(new DeviceStatus("No Card Yet"))
        );
        requestQueue.add(stringRequest);
    }

    // Simplified parsing to only handle UID
    private DeviceStatus parseStatusFromJson(String jsonResponse) {
        try {
            JSONObject json = new JSONObject(jsonResponse);
            String uid = json.getString("uid");
            return new DeviceStatus(uid);
        } catch (JSONException e) {
            return new DeviceStatus("No Card Yet");
        }
    }

    // Simplified response processing to only handle RFID cards
    private void processServerResponse(DeviceStatus newStatus) {
        boolean hasCard = !newStatus.uid.equals("No Card Yet");

        if (hasCard) {
            // If it's a new card, show the alert
            if (!newStatus.uid.equals(currentAlertUID)) {
                currentAlertUID = newStatus.uid;
                currentAlertState = AlertState.RFID;
                showRfidAlert(currentAlertUID);
                startSound();
            }
        } else {
            // If there's no card and an alert is active, clear it
            if (currentAlertState != AlertState.NONE) {
                clearOngoingAlert();
            }
        }
    }

    private void showRfidAlert(String tigerId) {
        ongoingAlertContainer.removeAllViews();
        noAlertsTextView.setVisibility(View.GONE);

        View alertCard = LayoutInflater.from(this).inflate(R.layout.card_alert_ongoing, ongoingAlertContainer, false);
        TextView textViewTigerId = alertCard.findViewById(R.id.textViewTigerId);
        Button actionButton = alertCard.findViewById(R.id.buttonAction);

        textViewTigerId.setText("ID: " + tigerId);
        actionButton.setText("Stop Sound");
        actionButton.setBackgroundResource(R.drawable.button_background_danger);

        actionButton.setOnClickListener(v -> {
            if (mediaPlayer.isPlaying()) {
                stopSound();
                actionButton.setText("Dismiss");
                actionButton.setBackgroundResource(R.drawable.button_background_dismiss);
            } else {
                clearOngoingAlert();
            }
        });
        ongoingAlertContainer.addView(alertCard);
    }

    // Removed the showObjectAlert() method entirely

    private void clearOngoingAlert() {
        currentAlertState = AlertState.NONE;
        currentAlertUID = "No Card Yet";
        stopSound();
        ongoingAlertContainer.removeAllViews();
        noAlertsTextView.setVisibility(View.VISIBLE);
    }

    private void startSound() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    private void stopSound() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        handler.post(pollingRunnable);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(pollingRunnable);
        stopSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
