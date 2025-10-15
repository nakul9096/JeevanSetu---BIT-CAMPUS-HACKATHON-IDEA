package com.example.jeevansetu;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ScannerInformation extends AppCompatActivity {

    private WebView webView;
    private Button zoomInButton;
    private Button zoomOutButton;
    private Button refreshButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanner_information);

        // Initialize views from the layout
        webView = findViewById(R.id.webview);
        zoomInButton = findViewById(R.id.btn_zoom_in);
        zoomOutButton = findViewById(R.id.btn_zoom_out);
        refreshButton = findViewById(R.id.btn_refresh);

        // --- WebView Configuration ---

        // Set a WebViewClient to prevent links from opening in an external browser
        webView.setWebViewClient(new WebViewClient());

        // Get web settings
        WebSettings webSettings = webView.getSettings();

        // Enable JavaScript (important for many modern web pages)
        webSettings.setJavaScriptEnabled(true);

        // Enable built-in zoom controls
        webSettings.setBuiltInZoomControls(true);
        // Hide the on-screen zoom buttons (+/-), as we have our own buttons
        webSettings.setDisplayZoomControls(false);

        // Define the URL to load
        String url = "http://192.168.4.1/";

        // Load the URL into the WebView
        webView.loadUrl(url);


        // --- Button Click Listeners ---

        // Refresh button
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.reload();
            }
        });

        // Zoom In button
        zoomInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.zoomIn();
            }
        });

        zoomOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.zoomOut();
            }
        });
    }

    // Handle the back button press to navigate back in WebView history
    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
