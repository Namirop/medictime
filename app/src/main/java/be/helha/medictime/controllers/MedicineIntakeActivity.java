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
        setContentView(R.layout.medicine_intake_activity);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.medicine_intake_fragment_container);
        if(fragment==null) {
            fragment = new MedicineIntakeFragment();
            fm.beginTransaction().add(R.id.medicine_intake_fragment_container, fragment).commit();
        }

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            int selectedIndex = bundle.getInt(MedicineIntakeFragment.SELECTED_INDEX);
            Bundle bundle1 = new Bundle();
            bundle1.putInt(MedicineIntakeFragment.SELECTED_INDEX, selectedIndex);
            fragment.setArguments(bundle1);
        }
    }
}
