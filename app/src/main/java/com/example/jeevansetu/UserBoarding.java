package com.example.jeevansetu;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Button;

public class UserBoarding extends AppCompatActivity {

    private Spinner areaSpinner;
    private Button proceedButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Make sure your XML file is named activity_user_boarding.xml or change this line
        setContentView(R.layout.activity_user_boarding);

        // --- 1. Initialize Views ---
        areaSpinner = findViewById(R.id.spinnerArea);
        proceedButton = findViewById(R.id.buttonProceed);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this,
                R.array.area_options,
                android.R.layout.simple_spinner_item
        );

        // --- 3. Specify the Dropdown Layout ---
        // Specify the layout to use when the list of choices appears.
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // --- 4. Apply the Adapter to the Spinner ---
        areaSpinner.setAdapter(adapter);

        // --- 5. Set a Listener for Item Selection ---
        areaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // Get the selected item
                String selectedArea = parent.getItemAtPosition(position).toString();

                // You can ignore the first item ("Select an Area...") if you want
                if (position > 0) {
                    // Show a Toast message with the selected item
                    Toast.makeText(UserBoarding.this, "Selected: " + selectedArea, Toast.LENGTH_SHORT).show();
                    // You can also enable the proceed button here
                    proceedButton.setEnabled(true);
                } else {
                    // Optionally disable the proceed button if the prompt is selected
                    proceedButton.setEnabled(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback.
                // You can leave this empty for this use case.
            }
        });

        // Initially disable the button until a valid area is selected
        proceedButton.setEnabled(false);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the currently selected item from the spinner
                String finalSelection = areaSpinner.getSelectedItem().toString();

                // Prevent proceeding if the default prompt is selected
                if (areaSpinner.getSelectedItemPosition() == 0) {
                    Toast.makeText(UserBoarding.this, "Please select an area", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Create an Intent to start the Users activity
                Intent intent = new Intent(UserBoarding.this, Users.class);

                // Add the selected area as an "extra" to the intent
                intent.putExtra("SELECTED_AREA", finalSelection);

                // Start the new activity
                startActivity(intent);
            }
        });
    }
}