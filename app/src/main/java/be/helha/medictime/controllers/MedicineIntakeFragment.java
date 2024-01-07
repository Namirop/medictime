package be.helha.medictime.controllers;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.Locale;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

    public class MedicineIntakeFragment extends Fragment {
        private static final String MEDICINE = "medicine";
        public static final String SELECTED_INDEX = "selected_index";
        private Medicine mMedicine;
        private Button mAddMedicineButton;
        private Button mValidButton;
        private Button mDeleteButton;
        private Spinner mMedicineSpinner;
        private Switch mMorningSwitch;
        private Switch mLunchTimeSwitch;
        private Switch mEveningSwitch;
        private TextView mStartDateTextView;
        private TextView mEndDateTextView;
        private ImageView mStartDateIcon;
        private ImageView mEndDateIcon;
        private List<Medicine> mMedicines;
        private int selectedIndex;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if(savedInstanceState != null) {
                mMedicine = (Medicine) savedInstanceState.getSerializable(MEDICINE);
            }
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            Bundle bundle = getArguments();
            if(bundle != null) {
                selectedIndex = bundle.getInt(SELECTED_INDEX, -1);
            }

            View view = inflater.inflate(R.layout.medicine_intake_fragment, container, false);
            mMorningSwitch = view.findViewById(R.id.morning_switch_intake);
            mLunchTimeSwitch = view.findViewById(R.id.lunchtime_switch_intake);
            mEveningSwitch = view.findViewById(R.id.evening_switch_intake);
            mStartDateTextView = view.findViewById(R.id.start_date_text_view);
            mEndDateTextView = view.findViewById(R.id.end_date_text_view);

            mAddMedicineButton = view.findViewById(R.id.add_medicine_button);
            mAddMedicineButton.setOnClickListener(v -> {
                AddMedicineFragment addMedicineFragment = new AddMedicineFragment();
                FragmentManager fm = requireActivity().getSupportFragmentManager();

                fm.beginTransaction()
                        .replace(R.id.medicine_intake_fragment_container, addMedicineFragment)
                        .addToBackStack(null)
                        .commit();
            });

            mMedicineSpinner = view.findViewById(R.id.medicine_spinner);
            mMedicines = MedicineLab.get(getContext()).getMedicines();

            ArrayAdapter<Medicine> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, mMedicines);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            mMedicineSpinner.setAdapter(adapter);

            mMedicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                    if (selectedIndex != -1) {
                        mMedicineSpinner.setSelection(selectedIndex);
                        selectedIndex = -1;
                    } else {
                        mMedicineSpinner.setSelection(position);
                    }

                    mMedicine = (Medicine) parent.getSelectedItem();

                    mStartDateTextView.setText(mMedicine.getStartDate());
                    mEndDateTextView.setText(mMedicine.getEndDate());

                    mMorningSwitch.setChecked(mMedicine.getMorningIntake());
                    mLunchTimeSwitch.setChecked(mMedicine.getLunchTimeIntake());
                    mEveningSwitch.setChecked(mMedicine.getEveningIntake());
                }
                @Override
                public void onNothingSelected(AdapterView<?> parent) {}

            });

            mStartDateIcon = view.findViewById(R.id.calendar_start_date);
            mStartDateIcon.setOnClickListener(v -> {

                String startDate = mMedicine.getStartDate();
                String[] startDateSplit = startDate.split("/");
                int startDay = Integer.parseInt(startDateSplit[0]);
                int startMonth = Integer.parseInt(startDateSplit[1]);
                int startYear = Integer.parseInt(startDateSplit[2]);

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (dpdView, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
                    mStartDateTextView.setText(date);
                    mMedicine.setStartDate(date);
                }, startYear, (startMonth - 1), startDay);

                datePickerDialog.show();
            });

            mEndDateIcon = view.findViewById(R.id.calendar_end_date);
            mEndDateIcon.setOnClickListener(v -> {

                String endDate = mMedicine.getEndDate();
                String[] endDateSplit = endDate.split("/");
                int endDay = Integer.parseInt(endDateSplit[0]);
                int endMonth = Integer.parseInt(endDateSplit[1]);
                int endYear = Integer.parseInt(endDateSplit[2]);

                DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (dpdView, year, month, dayOfMonth) -> {
                    String date = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, (month + 1), year);
                    mEndDateTextView.setText(date);
                    mMedicine.setEndDate(date);
                }, endYear, (endMonth - 1), endDay);

                datePickerDialog.show();
            });

            mMorningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mMedicine.setMorningIntake(isChecked);
            });
            mLunchTimeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mMedicine.setLunchTimeIntake(isChecked);
            });

            mEveningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
                mMedicine.setEveningIntake(isChecked);
            });

            mValidButton = view.findViewById(R.id.medicine_intake_add_button);
            mValidButton.setOnClickListener(v -> {
                MedicineLab.get(getContext()).updateMedicine(mMedicine);
                Intent intent = new Intent(getContext(), MedicineListActivity.class);
                startActivity(intent);
            });

            mDeleteButton = view.findViewById(R.id.medicine_intake_delete_button);
            mDeleteButton.setOnClickListener(v -> {
                MedicineLab.get(getContext()).deleteMedicine(mMedicine);
                Intent intent = new Intent(getContext(), MedicineListActivity.class);
                startActivity(intent);
            });

            return view;
        }
        @Override
        public void onSaveInstanceState(@NonNull Bundle outState) {
            super.onSaveInstanceState(outState);
            outState.putSerializable(MEDICINE, mMedicine);
        }
    }
