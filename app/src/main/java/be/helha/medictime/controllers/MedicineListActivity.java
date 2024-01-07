package be.helha.medictime.controllers;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import be.helha.medictime.R;
import be.helha.medictime.models.Medicine;
import be.helha.medictime.models.MedicineLab;

public class MedicineListActivity extends AppCompatActivity {
    private LinearLayout mContainer;
    private List<Medicine> mMedicines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_list_activity); // layout de l'activité

        mContainer = findViewById(R.id.medication_list); // conteneur des médicaments
        Button mAddMedicineIntake = findViewById(R.id.add_medicine_intake_button);
        mAddMedicineIntake.setOnClickListener(v -> {
            Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
            startActivity(intent);
        });
        updateUI();
    }

    @SuppressLint("NewApi")
    private void updateUI() {
        mContainer.removeAllViews();

        // On recupère la liste des médicaments
        mMedicines = MedicineLab.get(getApplicationContext()).getMedicines();
        List<Medicine> medicinesOfTheDay;

        LocalDate currentDate = LocalDate.now();

        // Récupération des médicaments à prendre chaque jour sur 30 jours
        for (int i = 0; i < 30; i++) {
            medicinesOfTheDay = new ArrayList<>();

            for (Medicine medicine : mMedicines) {
                LocalDate medicineEndDate = LocalDate.parse(medicine.getEndDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()));

                // Si la date de fin du médicament est égale ou supérieure à la date actuelle, on l'ajoute à la liste des médicaments à prendre
                if (medicineEndDate.isEqual(currentDate) || medicineEndDate.isAfter(currentDate)) {
                    medicinesOfTheDay.add(medicine);
                }
            }
            String currentDateStrFormat = currentDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault()));
            View dayView = getDayView(medicinesOfTheDay, currentDateStrFormat);
            mContainer.addView(dayView);

            // Passe au jour suivant
            currentDate = currentDate.plusDays(1);
        }

    }

    // Visuel de la vue d'une journée avec les médicaments à prendre
    @SuppressLint("SetTextI18n")
    private View getDayView(List<Medicine> medicinesOfTheDay, String currendFormatedDate) {

        View dayView = getLayoutInflater().inflate(R.layout.list_item_medicine, mContainer, false);

        TextView dayTextView = dayView.findViewById(R.id.medicine_date);
        dayTextView.setText(currendFormatedDate + " : ");

        LinearLayout morningLinearLayout = dayView.findViewById(R.id.morning_medicines);
        LinearLayout lunchTimeLinearLayout = dayView.findViewById(R.id.lunchtime_medicines);
        LinearLayout eveningLinearLayout = dayView.findViewById(R.id.evening_medicines);
        LinearLayout mainLinearLayout = dayView.findViewById(R.id.main_linear_layout);

        if (medicinesOfTheDay.size() == 0) {
            // Si aucun médicament n'est à prendre pour la journée, on affiche un message
            setMedicineTextView("Aucun médicament à prendre pour cette journée", mainLinearLayout);
        } else {
            for (Medicine medicine : medicinesOfTheDay) {
                if (medicine.getMorningIntake()) {
                    TextView medicinesTextView = setMedicineTextView("   - " + medicine.getName() + "\n", morningLinearLayout);
                    onMedicineTextViewClick(medicine, medicinesTextView);
                }

                if (medicine.getLunchTimeIntake()) {
                    TextView medicinesTextView = setMedicineTextView("   - " + medicine.getName() + "\n", lunchTimeLinearLayout);
                    onMedicineTextViewClick(medicine, medicinesTextView);
                }

                if (medicine.getEveningIntake()) {
                    TextView medicinesTextView = setMedicineTextView("   - " + medicine.getName() + "\n", eveningLinearLayout);
                    onMedicineTextViewClick(medicine, medicinesTextView);
                }
            }

            // Si aucun médicament n'est à prendre à un moment de la journée, on affiche un message
            setNoMedicineTextViewIfNeeded(morningLinearLayout);
            setNoMedicineTextViewIfNeeded(lunchTimeLinearLayout);
            setNoMedicineTextViewIfNeeded(eveningLinearLayout);
        }
        return dayView;
    }

    private void onMedicineTextViewClick(Medicine medicine, TextView medicineTextView) {
        medicineTextView.setOnClickListener(v -> {
            Intent intent = new Intent(MedicineListActivity.this, MedicineIntakeActivity.class);
            int selectedIndex = mMedicines.indexOf(medicine);
            intent.putExtra(MedicineIntakeFragment.SELECTED_INDEX, selectedIndex);
            intent.putExtra(MedicineIntakeFragment.MEDICINE_FROM_LIST, medicine);
            startActivity(intent);
        });
    }

    private TextView setMedicineTextView(String text, LinearLayout linearLayout) {
        TextView medicinesTextView = new TextView(getApplicationContext());
        medicinesTextView.setText(text);
        medicinesTextView.setTextSize(18);
        if(text.contains("Aucun médicament à prendre pour cette journée")) {
            medicinesTextView.setPadding(0, 5, 0, 25);
            medicinesTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            linearLayout.removeAllViews();
        }
        linearLayout.addView(medicinesTextView);
        return medicinesTextView;
    }

    private void setNoMedicineTextViewIfNeeded(LinearLayout linearLayout) {
        if (linearLayout.getChildCount() == 0) {
            setMedicineTextView("   Aucun médicament à prendre", linearLayout);
        }
    }
}