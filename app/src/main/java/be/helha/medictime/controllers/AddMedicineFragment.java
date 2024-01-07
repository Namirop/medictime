package be.helha.medictime.controllers;

import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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

    // OnCreate est appelé lors de la création de l'activité
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lab = MedicineLab.get(getContext());
        mMedicine = new Medicine();
        mMedicineDefaultTime = 1;

        // Permet de récupérer les éléments que l'on a sauvegardé dans le Bundle, pour les réutiliser lors de la rotation/destruction de l'activité
        if (savedInstanceState != null) {
            mMedicineDefaultTime = savedInstanceState.getInt(DEFAULT_TIME);
            mMedicine = (Medicine) savedInstanceState.getSerializable(MEDICINE);
        }
    }


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_medicine_fragment, container, false);

        // Initialisation du TextView + Listener
        mMedicineNameEditText = view.findViewById(R.id.medicine_name_edit_text);
        mMedicineNameEditText.setOnEditorActionListener((v, actionId, event) -> {

            // TODO : Faire en sorte que le nom soit sauvegardé quand on quitte le champ

            // Vérifie si l'on a appuyé sur le bouton 'Done' ou 'Next' du clavier
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                mMedicineName = mMedicineNameEditText.getText().toString();
                mMedicine.setName(mMedicineName);
            }
            // On retourne false pour indiquer que l'on a pas géré l'évènement et que l'on souhaite que le clavier se ferme après avoir appuyé sur 'Done' ou 'Next'
            return false;
        });

        mMedicineNameEditText.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                // Quand le focus est perdu, on sauvegarde le nom du médicament
                mMedicineName = mMedicineNameEditText.getText().toString();
                mMedicine.setName(mMedicineName);
            }
        });


        // Initialisation du NumberPicker + Listener
        mDefaultTimeNumberPicker = view.findViewById(R.id.default_time_number_picker);
        mDefaultTimeNumberPicker.setMinValue(1);
        mDefaultTimeNumberPicker.setMaxValue(30);
        mDefaultTimeNumberPicker.setValue(mMedicineDefaultTime);

        mDefaultTimeNumberPicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            mMedicineDefaultTime = newVal;
            mMedicine.setStartAndEndDate(mMedicineDefaultTime);
        });


        // Initialisation des Switch + Listener
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


        // Initialisation du bouton 'Ajouter un médicament' + Listener
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

    // Permet de sauvegarder les éléments dans le Bundle, avant que l'activité ne soit détruite
    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(DEFAULT_TIME, mMedicineDefaultTime);
        outState.putSerializable(MEDICINE, mMedicine);
    }
}