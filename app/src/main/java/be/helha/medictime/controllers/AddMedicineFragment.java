package be.helha.medictime.controllers;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Switch;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class AddMedicineFragment extends Fragment {

    private static final String DEFAULT_TIME = "default_time";
    private static final String MEDICINE = "medicine";
    private EditText mMedicineNameEditText;
    private NumberPicker mDefaultTimeNumberPicker;
    private Button mAddMedicineButton;
    private Switch mMorningSwitch;
    private Switch mLunchTimeSwitch;
    private Switch mEveningSwitch;
    private String mMedicineName;
    private int mMedicineDefaultTime;
    private MedicineLab lab;
    private Medicine mMedicine;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lab = MedicineLab.get(getContext());
        mMedicine = new Medicine();
        mMedicineDefaultTime = 1;

        if (savedInstanceState != null) {
            mMedicineDefaultTime = savedInstanceState.getInt(DEFAULT_TIME);
            mMedicine = (Medicine) savedInstanceState.getSerializable(MEDICINE);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_medicine_fragment, container, false);

        mMedicineNameEditText = view.findViewById(R.id.medicine_name_edit_text);
        mMedicineNameEditText.setOnEditorActionListener((v, actionId, event) -> {

            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                mMedicineName = mMedicineNameEditText.getText().toString();
                mMedicine.setName(mMedicineName);
            }
            return false;
        });

        mMedicineNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                mMedicineName = mMedicineNameEditText.getText().toString();
                mMedicine.setName(mMedicineName);
            }
        });


        mDefaultTimeNumberPicker = view.findViewById(R.id.default_time_number_picker);
        mDefaultTimeNumberPicker.setMinValue(1);
        mDefaultTimeNumberPicker.setMaxValue(30);
        mDefaultTimeNumberPicker.setValue(mMedicineDefaultTime);

        mDefaultTimeNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mMedicineDefaultTime = newVal;
            mMedicine.setStartAndEndDate(mMedicineDefaultTime);
        });


        mMorningSwitch = view.findViewById(R.id.morning_switch_add);
        mMorningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mMedicine.setMorningIntake(isChecked);
        });

        mLunchTimeSwitch = view.findViewById(R.id.lunchtime_switch_add);
        mLunchTimeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mMedicine.setLunchTimeIntake(isChecked);
        });

        mEveningSwitch = view.findViewById(R.id.evening_switch_add);
        mEveningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            mMedicine.setEveningIntake(isChecked);
        });


        mAddMedicineButton = view.findViewById(R.id.add_medicine_add_button);
        mAddMedicineButton.setOnClickListener(v -> {
            lab.addMedicine(mMedicine);
            GoToMedicineIntakeFragment();
        });

        return view;
    }

    private void GoToMedicineIntakeFragment() {
        MedicineIntakeFragment medicineIntakeFragment = new MedicineIntakeFragment();
        FragmentManager fm = requireActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.medicine_intake_fragment_container, medicineIntakeFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DEFAULT_TIME, mMedicineDefaultTime);
        outState.putSerializable(MEDICINE, mMedicine);
    }
}