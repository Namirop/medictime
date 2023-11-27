package be.helha.medictime.controllers;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import be.helha.medictime.R;

// On crée un Fragment, ce qui va créer sa vue (medicine_intake_fragment) et sa classe - controlleur (MedicineIntakeFragment) dans laquelle on va initialiser les widgets avec leur listeners
// On crée ensuite une activité qui va contenir une classe (MedicineIntakeActivity) et sa vue (medicine_intake_activity)
// Cette vue (medicine_intake_activity) contient un fragment container (FrameLayout) qui va contenir le fragment (MedicineIntakeFragment)
// Dans cette classe (MedicineIntakeActivity), on va récupérer l'ui du FrameLayout et on va lui attribuer le fragment (MedicineIntakeFragment)

public class MedicineIntakeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medicine_intake_activity); // on récupère la vue de cette classe, 'activity_medicine_intake.xml' (contient le FrameLayout)

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.medicine_intake_fragment_container); // on récupère le FrameLayout
        if(fragment==null) {
            fragment = new MedicineIntakeFragment(); // La vue du fragment dans 'MedicineIntakeFragment' (v) est retournée et affichée dans le FrameLayout
            fm.beginTransaction().add(R.id.medicine_intake_fragment_container, fragment).commit();
        }
    }
}
