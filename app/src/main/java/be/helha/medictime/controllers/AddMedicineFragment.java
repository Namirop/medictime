package be.helha.medictime.controllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.io.Serializable;
import java.util.UUID;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class AddMedicineFragment extends Fragment {

    private EditText mMedicineNameEditText;
    private EditText mDefaultTimeEditText;
    private Button mAddMedicineButton;
    private Switch mMorningSwitch;
    private Switch mLunchTimeSwitch;
    private Switch mEveningSwitch;
    private String medicineName;
    private int medicineDefaultTime;
    private MedicineLab lab;
    private Medicine medicine;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lab = MedicineLab.get(getContext());
        medicine = new Medicine();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_medicine_fragment, container, false);

        // Initialisation du bouton 'Ajouter un médicament' et Listener dessus
        mMedicineNameEditText = v.findViewById(R.id.medicine_name_edit_text);
        mMedicineNameEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Vérifie si l'on a appuyé sur le bouton 'Done' ou 'Enter' du clavier
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                    medicineName = mMedicineNameEditText.getText().toString();
                    medicine.setName(medicineName);
                }
                return false;
            }
        });

        mDefaultTimeEditText = v.findViewById(R.id.default_time_edit_text);
        mDefaultTimeEditText.setOnEditorActionListener((v1, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                medicineDefaultTime = Integer.parseInt(mDefaultTimeEditText.getText().toString());
                medicine.setFirstEndDate(medicineDefaultTime); // attribue la date de fin
            }
            return false;
        });

        mMorningSwitch = v.findViewById(R.id.morning_switch_add);
        mMorningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    medicine.setMorningIntake();
                }
            }
        });

        mLunchTimeSwitch = v.findViewById(R.id.lunchtime_switch_add);
        mLunchTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    medicine.setLunchTimeIntake();
                }
            }
        });

        mEveningSwitch = v.findViewById(R.id.evening_switch_add);
        mEveningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    medicine.setEveningIntake();
                }
            }
        });

        mAddMedicineButton = v.findViewById(R.id.add_medicine_add_button);
        mAddMedicineButton.setOnClickListener(e -> {
            lab.addMedicine(medicine);
            GoToMedicineIntakeFragment();
        });
        return v;
    }

    private void GoToMedicineIntakeFragment() {
        MedicineIntakeFragment medicineIntakeFragment = new MedicineIntakeFragment();
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.medicine_intake_fragment_container, medicineIntakeFragment)
                .addToBackStack(null)
                .commit();
    }
}