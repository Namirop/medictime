package be.helha.medictime.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;

import be.helha.medictime.R;

public class MedicineListActivity extends AppCompatActivity {

    private LinearLayout mContainer;
    private Button mAddMedicineIntake;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list); // layout de l'activité
        mContainer = findViewById(R.id.medication_list); // conteneur des médicaments
        mAddMedicineIntake = findViewById(R.id.add_medicine_intake_button);
        mAddMedicineIntake.setOnClickListener(v -> {
            Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
            startActivity(intent);
            Log.d("MedicineListActivity", "Add medicine intake button clicked");
        });
        //updateUI();
    }

    private void updateUI() {

    }
}