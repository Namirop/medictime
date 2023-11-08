package be.helha.medictime.controllers;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineIntakeFragment extends Fragment {
    private Medicine mMedicine;
    private Button mAddMedicineButton;
    private Spinner mMedicineSpinner;
    private Switch mMorningSwitch;
    private Switch mLunchTimeSwitch;
    private Switch mEveningSwitch;

    private EditText mStartDate;
    private EditText mEndDate;

    public MedicineIntakeFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Permet d'initialiser les widgets avec leur listeners dans le cas d'un fragment, et de retourner la vue qui sera utilisée dans CrimeActivity
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_medicine_intake, container, false);

        // Initialisation du bouton 'Ajouter un médicament' et Listener dessus
        mAddMedicineButton = (Button) v.findViewById(R.id.add_medicine_button);
        mAddMedicineButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {}
        });

        // Initialisation du spinner 'mMedicineSpinner' et Listener dessus
        mMedicineSpinner = v.findViewById(R.id.medicine_spinner);

        // Initialisation du switch 'mMorningSwitch' et Listener dessus
        mMorningSwitch = v.findViewById(R.id.morning_switch);

        // Initialisation du switch 'mLunchTimeSwitch' et Listener dessus
        mLunchTimeSwitch = v.findViewById(R.id.lunchtime_switch);

        // Initialisation du switch 'mEveningSwitch' et Listener dessus
        mEveningSwitch = v.findViewById(R.id.evening_switch);

        // Initialisation du champ 'mStartDate' et Listener dessus
        mStartDate = v.findViewById(R.id.start_date);

        // Initialisation du champ 'mEndDate' et Listener dessus
        mEndDate = v.findViewById(R.id.end_date);

        return v;
    }
}
