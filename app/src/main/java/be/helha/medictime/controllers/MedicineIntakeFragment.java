package be.helha.medictime.controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.List;
import java.util.UUID;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineIntakeFragment extends Fragment {
    private Medicine mMedicine;
    private Button mAddMedicineButton;
    private Button mValidButton;
    private Spinner mMedicineSpinner;
    private Switch mMorningSwitch;
    private Switch mLunchTimeSwitch;
    private Switch mEveningSwitch;
    private EditText mStartDate;
    private EditText mEndDate;
    private List<Medicine> mMedicines;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Permet d'initialiser les widgets avec leur listeners dans le cas d'un fragment, et de retourner la vue qui sera utilisée dans CrimeActivity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        /*
        // Permet de récupérer les éléments que l'on a sauvegardé dans le Bundle, depuis la méthode onSaveInstanceState
        if(savedInstanceState != null) {
            //mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }*/

        View v = inflater.inflate(R.layout.medicine_intake_fragment, container, false);
        mMorningSwitch = v.findViewById(R.id.morning_switch_intake);
        mLunchTimeSwitch = v.findViewById(R.id.lunchtime_switch_intake);
        mEveningSwitch = v.findViewById(R.id.evening_switch_intake);
        mStartDate = v.findViewById(R.id.start_date);
        mEndDate = v.findViewById(R.id.end_date);

        // Initialisation du bouton 'Ajouter un médicament' et Listener dessus
        mAddMedicineButton = v.findViewById(R.id.add_medicine_button);
        mAddMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddMedicineFragment addMedicineFragment = new AddMedicineFragment();
                // Obtention du gestionnaire de fragments de l'activité parente
                FragmentManager fm = requireActivity().getSupportFragmentManager();

                // Remplacement du fragment 'MedicineIntakeFragment' par le nouveau fragment 'AddMedicineFragment'
                fm.beginTransaction()
                        .replace(R.id.medicine_intake_fragment_container, addMedicineFragment)
                        .addToBackStack(null) // Ajoute le fragment à la pile de retour, ce qui permet de revenir au fragment précédent en appuyant sur le bouton retour
                        .commit();
            }
        });

        // Initialisation du spinner 'mMedicineSpinner' et Listener dessus
        mMedicineSpinner = v.findViewById(R.id.medicine_spinner);
        mMedicines = MedicineLab.get(getContext()).getMedicines();

        ArrayAdapter<Medicine> adapter = new ArrayAdapter<>(requireActivity(), android.R.layout.simple_spinner_item, mMedicines);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mMedicineSpinner.setAdapter(adapter);

        mMedicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mMedicine = (Medicine) parent.getSelectedItem();

                mStartDate.setText(mMedicine.getStartDate().toString());
                mEndDate.setText(mMedicine.getEndDate().toString());


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

        mMorningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMedicine.setMorningIntake(true);
                } else if (!isChecked) {
                    mMedicine.setMorningIntake(false);
                }
            }
        });
        mLunchTimeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMedicine.setLunchTimeIntake(true);
                } else if (!isChecked) {
                    mMedicine.setLunchTimeIntake(false);
                }
            }
        });

        mEveningSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mMedicine.setEveningIntake(true);
                } else if (!isChecked) {
                    mMedicine.setEveningIntake(false);
                }
            }
        });

        mValidButton = v.findViewById(R.id.medicine_intake_add_button);
        mValidButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return v;
    }

    /*
    // Permet d'indiquer ce que l'on souhaite sauvegarder quand l'activité est détruite (rotation, ...)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        //outState.putInt(KEY_INDEX, mCurrentIndex);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        //updateMedicine();
    }
}
