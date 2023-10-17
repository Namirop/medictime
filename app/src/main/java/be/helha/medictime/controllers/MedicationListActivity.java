package be.helha.medictime.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import be.helha.medictime.R;

public class MedicationListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medication_list);
        updateUI();
    }

    private void updateUI() {

    }
}