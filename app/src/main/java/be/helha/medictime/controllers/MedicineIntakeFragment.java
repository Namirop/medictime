package be.helha.medictime.controllers;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    private UUID medicineId;
    private Button mAddMedicineButton;
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

        // Permet de récupérer les éléments que l'on a sauvegardé dans le Bundle, depuis la méthode onSaveInstanceState
        if(savedInstanceState != null) {
            //mCurrentIndex = savedInstanceState.getInt(KEY_INDEX);
        }

        View v = inflater.inflate(R.layout.medicine_intake_fragment, container, false);

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


        // Initialisation du switch 'mMorningSwitch' et Listener dessus
        mMorningSwitch = v.findViewById(R.id.morning_switch_intake);
        if(mMorningSwitch.isChecked()) {
            mMedicine.setMorningIntake();
        }

        // Initialisation du switch 'mLunchTimeSwitch' et Listener dessus
        mLunchTimeSwitch = v.findViewById(R.id.lunchtime_switch_intake);
        if(mLunchTimeSwitch.isChecked()) {
            mMedicine.setLunchTimeIntake();
        }

        // Initialisation du switch 'mEveningSwitch' et Listener dessus
        mEveningSwitch = v.findViewById(R.id.evening_switch_intake);
        if(mEveningSwitch.isChecked()) {
            mMedicine.setEveningIntake();
        }

        // Initialisation du champ 'mStartDate' et Listener dessus
        mStartDate = v.findViewById(R.id.start_date);

        // Initialisation du champ 'mEndDate' et Listener dessus
        mEndDate = v.findViewById(R.id.end_date);

        return v;
    }

    // Permet d'indiquer ce que l'on souhaite sauvegarder quand l'activité est détruite (rotation, ...)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.i(TAG, "onSaveInstanceState");
        //outState.putInt(KEY_INDEX, mCurrentIndex);
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMedicine();
    }

    private void updateMedicine() {

    }
}
