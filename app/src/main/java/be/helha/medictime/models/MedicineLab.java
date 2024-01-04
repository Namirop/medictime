package be.helha.medictime.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import be.helha.medictime.db.MedicineBaseHelper;
import be.helha.medictime.db.MedicineCursorWrapper;
import be.helha.medictime.db.MedicineDbSchema;

// Laboratoire de médicaments qui contient la liste des médicaments et les méthodes pour les manipuler
public class MedicineLab implements Serializable {
    private static MedicineLab sMedicineLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;


    // Initialise le constructeur et le retourne, sinon le recupère si déjà initialisé
    // On a besoin d'un contexte lorsque l'on utilise une base de donnée, car derrière la base de données se trouvent des fichiers qui doivent connaitre le contexte de l'application
    public static MedicineLab get(Context context) {
        if (sMedicineLab == null) {
            sMedicineLab = new MedicineLab(context);
        }
        return sMedicineLab;
    }

    // Initialise la liste des médicaments (utilisé dans le 'get', pas ailleurs)
    public MedicineLab(Context context) {
        this.mContext = context;
        // Sur ce contexte la, on va écrire sur cette base de donnée
        // 'mDatabase' est un objet qui va nous permettre de modifier la base de donnée
        mDatabase = new MedicineBaseHelper(mContext).getWritableDatabase();
    }

    public void addMedicine(Medicine medicine) {
        mDatabase.insert(MedicineDbSchema.MedicineTable.NAME, null, getContentValues(medicine));
    }

    public List<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();

        // On demarre le curseur
        MedicineCursorWrapper cursor = queryMedicines(null, null);
        try {
            // on va au premier enregistrement
            cursor.moveToFirst();
            // tant qu'on est pas à la fin du curseur
            while (!cursor.isAfterLast()) {
                // on ajoute le crime à la liste
                medicines.add(cursor.getMedicine());
                // on passe à l'enregistrement suivant
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return medicines;
    }

    private ContentValues getContentValues(Medicine medicine) {

        // Permet de stocker les données dans un objet ContentValues, une sorte de dictionnaire, qui permet de stocker des paires clé/valeur
        ContentValues values = new ContentValues();
        values.put(MedicineDbSchema.MedicineTable.cols.UUID, medicine.getId().toString());
        values.put(MedicineDbSchema.MedicineTable.cols.NAME, medicine.getName());
        values.put(MedicineDbSchema.MedicineTable.cols.START_DATE, medicine.getStartDate());
        values.put(MedicineDbSchema.MedicineTable.cols.END_DATE, medicine.getEndDate());
        values.put(MedicineDbSchema.MedicineTable.cols.MORNING_INTAKE, medicine.getMorningIntake());
        values.put(MedicineDbSchema.MedicineTable.cols.LUNCH_TIME_INTAKE, medicine.getLunchTimeIntake());
        values.put(MedicineDbSchema.MedicineTable.cols.EVENING_INTAKE, medicine.getEveningIntake());

        return values;
    }

    private MedicineCursorWrapper queryMedicines(String whereClause, String[] whereArgs) {
        return new MedicineCursorWrapper(mDatabase.query(
                MedicineDbSchema.MedicineTable.NAME,
                null, //all columns
                whereClause,
                whereArgs,
                null, null, null));
    }

    public void updateMedicine(Medicine medicine) {
        String uuidString = medicine.getId().toString();
        // Permet de stocker les données dans un objet ContentValues, une sorte de dictionnaire, qui permet de stocker des paires clé/valeur.
        ContentValues values = getContentValues(medicine);
        // On modifie la db avec les nouvelles valeurs à l'uuid correspondant
        mDatabase.update(MedicineDbSchema.MedicineTable.NAME, values, MedicineDbSchema.MedicineTable.cols.UUID + " = ?", new String[]{uuidString});
    }
}
