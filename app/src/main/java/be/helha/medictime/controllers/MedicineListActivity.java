package be.helha.medictime.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineListActivity extends AppCompatActivity {

    private static final String LIST_INDEX = "list_index";
    private LinearLayout mContainer;
    private Button mAddMedicineIntake;
    private List<Medicine> mMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Permet de récupérer les éléments que l'on a sauvegardé dans le Bundle, depuis la méthode onSaveInstanceState
        if(savedInstanceState != null) {
            mMedicines = (List<Medicine>) savedInstanceState.getSerializable(LIST_INDEX);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list_activity); // layout de l'activité

        mContainer = findViewById(R.id.medication_list); // conteneur des médicaments
        mAddMedicineIntake = findViewById(R.id.add_medicine_intake_button);
        mAddMedicineIntake.setOnClickListener(v -> {
            Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
            startActivity(intent);
        });
        // Dès que l'activité est créée (lors du lancement, après une rotation, quand on revient à cette activité, ...), on met à jour l'UI
        //updateUI();
    }

    private void updateUI() {
        mContainer.removeAllViews();
        // On recupère la liste des médicaments
        mMedicines = MedicineLab.get(getApplicationContext()).getMedicines();
        // Pour chaque médicament, on crée une vue et on l'ajoute au conteneur
        for (Medicine m : mMedicines) {
            View medicineView = getMedicineView(m);
            mContainer.addView(medicineView);
        }
    }

    // Visuel de la vue d'un médicament dans la liste
    private View getMedicineView(Medicine medicine) {
        // On récupère la vue du médicament depuis le layout list_item_medicine
        View medicineView = getLayoutInflater().inflate(R.layout.list_item_medicine, null);
        //((TextView)medicineView.findViewById(R.id.medicine_date)).setText(medicine.getDate());
        return medicineView;
    }

    // Permet d'indiquer ce que l'on souhaite sauvegarder quand l'activité est détruite (rotation, ...)
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(LIST_INDEX, (Serializable) mMedicines);
    }

    // Permet de mettre à jour l'UI quand l'activité est relancée (rotation, ...)
    @Override
    protected void onResume() {
        super.onResume();
        //updateUI();
    }
}