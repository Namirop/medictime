package be.helha.medictime.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineListActivity extends AppCompatActivity {

    private static final String LIST_INDEX = "list_index";
    private LinearLayout mContainer;
    private List<Medicine> mMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Permet de récupérer les éléments que l'on a sauvegardé dans le Bundle, depuis la méthode onSaveInstanceState
        //if(savedInstanceState != null) {
          //  mMedicines = (List<Medicine>) savedInstanceState.getSerializable(LIST_INDEX);
        //}

        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list_activity); // layout de l'activité

        mContainer = findViewById(R.id.medication_list); // conteneur des médicaments
        Button mAddMedicineIntake = findViewById(R.id.add_medicine_intake_button);
        mAddMedicineIntake.setOnClickListener(v -> {
            Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
            startActivity(intent);
        });
        // Dès que l'activité est créée (lors du lancement, après une rotation, quand on revient à cette activité, ...), on met à jour l'UI
        updateUI();
    }

    private void updateUI() {
        mContainer.removeAllViews();
        Calendar calendar = Calendar.getInstance();

        // On recupère la liste des médicaments
        mMedicines = MedicineLab.get(getApplicationContext()).getMedicines();
        // Récupération des médicaments à prendre chaque jour sur 30 jours
        for (int i = 0; i < 30; i++) {
            // On récupère la date du jour
            Date currentDate = calendar.getTime();
            SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
            String currendFormatedDate = formatter.format(currentDate);


            List<Medicine> medicinesOfTheDay = new ArrayList<>();
            // On parcourt la liste des médicaments
            for (Medicine medicine : mMedicines) {
                // On vérifie si la date de fin du médicament est égale ou supérieure à la date du jour, pour savoir si on doit l'afficher
                if(medicine.getEndDate().compareTo(currendFormatedDate) >= 0) {
                    medicinesOfTheDay.add(medicine);
                }
            }

            for (Medicine medicine : medicinesOfTheDay) {
                Log.i("MedicineListActivity", "Medicine: " + medicine.getName());
            }

            mContainer.addView(getDayView(medicinesOfTheDay, currendFormatedDate));

            // On passe au jour suivant
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
    }

    // Visuel de la vue d'une journée avec les médicaments à prendre
    private View getDayView(List<Medicine> medicinesOfTheDay, String currendFormatedDate) {

        View dayView = getLayoutInflater().inflate(R.layout.list_item_medicine, mContainer, false);

        TextView dayTextView = dayView.findViewById(R.id.medicine_date);
        dayTextView.setText(currendFormatedDate + " : ");

        LinearLayout morningMedicines = dayView.findViewById(R.id.morning_medicines);
        LinearLayout lunchtimeMedicines = dayView.findViewById(R.id.lunchtime_medicines);
        LinearLayout eveningMedicines = dayView.findViewById(R.id.evening_medicines);
        for (Medicine medicine : medicinesOfTheDay) {
            if (medicine.getMorningIntake()) {
                TextView morningMedicineTextView = new TextView(getApplicationContext());
                morningMedicineTextView.setText("   - " + medicine.getName());
                morningMedicineTextView.setOnClickListener(v -> {
                    Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
                    intent.putExtra("medicine", medicine);
                    startActivity(intent);
                });
                morningMedicines.addView(morningMedicineTextView);
            }

            if (medicine.getLunchTimeIntake()) {
                TextView lunchtimeMedicineTextView = new TextView(getApplicationContext());
                lunchtimeMedicineTextView.setText("   - " + medicine.getName() + "\n");
                lunchtimeMedicines.addView(lunchtimeMedicineTextView);
            }
            if (medicine.getEveningIntake()) {
                TextView eveningMedicineTextView = new TextView(getApplicationContext());
                eveningMedicineTextView.setText("   - " + medicine.getName() + "\n");
                eveningMedicines.addView(eveningMedicineTextView);
            }
        }
        return dayView;
    }
}