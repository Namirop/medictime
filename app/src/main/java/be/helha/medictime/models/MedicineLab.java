package be.helha.medictime.models;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

// Laboratoire de médicaments qui contient la liste des médicaments et les méthodes pour les manipuler
public class MedicineLab {
    private static MedicineLab sMedicineLab;
    private Context mContext;
    private ArrayList<Medicine> mMedicines;


    // Initialisation le constructeur et le retourne, sinon le recupère si déjà initialisé
    public static MedicineLab get(Context context) {
        if (sMedicineLab == null) {
            sMedicineLab = new MedicineLab(context);
        }
        return sMedicineLab;
    }

    //
    public MedicineLab(Context context) {
        mContext = context;
        mMedicines = new ArrayList<>();
    }

    public void addMedicine(Medicine medicine) {
        mMedicines.add(medicine);
    }
}
