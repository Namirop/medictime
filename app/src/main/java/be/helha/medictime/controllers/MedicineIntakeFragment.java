package be.helha.medictime.controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.w3c.dom.Text;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineIntakeFragment extends Fragment {
    private static final String MEDICINE = "medicine";
    private Medicine mMedicine;
    private Button mAddMedicineButton;
    private Button mValidButton;
    private Spinner mMedicineSpinner;
    private Switch mMorningSwitch;
    private Switch mLunchTimeSwitch;
    private Switch mEveningSwitch;
    private TextView mStartDate;
    private TextView mEndDate;
    private List<Medicine> mMedicines;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState != null) {
            mMedicine = (Medicine) savedInstanceState.getSerializable(MEDICINE);
        }
    }

    // Permet d'initialiser les widgets avec leur listeners dans le cas d'un fragment, et de retourner la vue qui sera utilisée dans CrimeActivity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.medicine_intake_fragment, container, false);
        mMorningSwitch = view.findViewById(R.id.morning_switch_intake);
        mLunchTimeSwitch = view.findViewById(R.id.lunchtime_switch_intake);
        mEveningSwitch = view.findViewById(R.id.evening_switch_intake);
        mStartDate = view.findViewById(R.id.start_date);
        mEndDate = view.findViewById(R.id.end_date);

        // Initialisation du bouton 'Ajouter un médicament' et Listener dessus
        mAddMedicineButton = view.findViewById(R.id.add_medicine_button);
        mAddMedicineButton.setOnClickListener(v -> {
            AddMedicineFragment addMedicineFragment = new AddMedicineFragment();
            // Obtention du gestionnaire de fragments de l'activité parente
            FragmentManager fm = requireActivity().getSupportFragmentManager();

            // Remplacement du fragment 'MedicineIntakeFragment' par le nouveau fragment 'AddMedicineFragment'
            fm.beginTransaction()
                    .replace(R.id.medicine_intake_fragment_container, addMedicineFragment)
                    .addToBackStack(null) // Ajoute le fragment à la pile de retour, ce qui permet de revenir au fragment précédent en appuyant sur le bouton retour
                    .commit();
        });

        // Initialisation du spinner 'mMedicineSpinner' et Listener dessus
        mMedicineSpinner = view.findViewById(R.id.medicine_spinner);
        mMedicines = MedicineLab.get(getContext()).getMedicines();

        ArrayAdapter<Medicine> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, mMedicines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMedicineSpinner.setAdapter(adapter);

        mMedicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                // Permet d'enregistrer les modifications du médicament quand l'on change de médicament dans le spinner (sans devoir cliquer sur le bouton 'Valider')
                if(mMedicine != null) {
                    MedicineLab.get(getContext()).updateMedicine(mMedicine);
                    //position = adapter.getPosition(mMedicine);
                    //mMedicineSpinner.setSelection(position);
                }

                mMedicine = (Medicine) parent.getSelectedItem();

                mStartDate.setText(mMedicine.getStartDate());
                mEndDate.setText(mMedicine.getEndDate());

                if (mMedicine.getMorningIntake()) {
                    mMorningSwitch.setChecked(true);
                } else {
                    mMorningSwitch.setChecked(false);
                }
                if (mMedicine.getLunchTimeIntake()) {
                    mLunchTimeSwitch.setChecked(true);
                } else {
                    mLunchTimeSwitch.setChecked(false);
                }
                if (mMedicine.getEveningIntake()) {
                    mEveningSwitch.setChecked(true);
                } else {
                    mEveningSwitch.setChecked(false);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}

        });

        mStartDate.setOnClickListener(v -> {

            String startDate = mMedicine.getStartDate();
            String[] startDateSplit = startDate.split("/");
            int startDay = Integer.parseInt(startDateSplit[0]);
            int startMonth = Integer.parseInt(startDateSplit[1]);
            int startYear = Integer.parseInt(startDateSplit[2]);

            Log.i("DATE", startDay + "/" + startMonth + "/" + startYear);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (dpdView, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                mStartDate.setText(date);
                mMedicine.setStartDate(date);
            }, startYear, (startMonth - 1), startDay);

            datePickerDialog.show();
        });

        mEndDate.setOnClickListener(v -> {

            String endDate = mMedicine.getEndDate();
            String[] endDateSplit = endDate.split("/");
            int endDay = Integer.parseInt(endDateSplit[0]);
            int endMonth = Integer.parseInt(endDateSplit[1]);
            int endYear = Integer.parseInt(endDateSplit[2]);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(), (dpdView, year, month, dayOfMonth) -> {
                String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                mEndDate.setText(date);
                mMedicine.setEndDate(date);
            }, endYear, (endMonth - 1), endDay);

            datePickerDialog.show();
        });

        mMorningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMedicine.setMorningIntake(true);
            } else {
                mMedicine.setMorningIntake(false);
            }
        });
        mLunchTimeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMedicine.setLunchTimeIntake(true);
            } else {
                mMedicine.setLunchTimeIntake(false);
            }
        });

        mEveningSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mMedicine.setEveningIntake(true);
            } else {
                mMedicine.setEveningIntake(false);
            }
        });

        mValidButton = view.findViewById(R.id.medicine_intake_add_button);
        mValidButton.setOnClickListener(v -> {
            MedicineLab.get(getContext()).updateMedicine(mMedicine);
            Intent intent = new Intent(getContext(), MedicineListActivity.class);
            startActivity(intent);
        });

        return view;
    }


    // Permet d'indiquer ce que l'on souhaite sauvegarder quand l'activité est détruite (rotation, ...)
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(MEDICINE, mMedicine);
    }
}
