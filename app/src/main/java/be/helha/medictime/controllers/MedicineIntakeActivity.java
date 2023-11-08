package be.helha.medictime.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import be.helha.medictime.R;

public class MedicineIntakeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_intake); // on récupère le layout

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_container); // on récupère le fragment du layout
        if(fragment==null) {
            fragment = new MedicineIntakeFragment(); // la vue retourné dans onCreateView() de MedicineIntakeFragment est attribué au fragment
            fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
        }
    }
}
