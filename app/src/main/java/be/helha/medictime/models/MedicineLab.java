package be.helha.medictime.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import be.helha.medictime.db.MedicineBaseHelper;
import be.helha.medictime.db.MedicineCursorWrapper;
import be.helha.medictime.db.MedicineDbSchema;

public class MedicineLab implements Serializable {
    private static MedicineLab sMedicineLab;
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static MedicineLab get(Context context) {
        if (sMedicineLab == null) {
            sMedicineLab = new MedicineLab(context);
        }
        return sMedicineLab;
    }

    public MedicineLab(Context context) {
        this.mContext = context;
        mDatabase = new MedicineBaseHelper(mContext).getWritableDatabase();
    }

    public void addMedicine(Medicine medicine) {
        mDatabase.insert(MedicineDbSchema.MedicineTable.NAME, null, getContentValues(medicine));
    }

    public void updateMedicine(Medicine medicine) {
        String UUIDStr = medicine.getId().toString();
        ContentValues values = getContentValues(medicine);
        mDatabase.update(MedicineDbSchema.MedicineTable.NAME, values, MedicineDbSchema.MedicineTable.cols.UUID + " = ?", new String[]{UUIDStr});
    }

    public void deleteMedicine(Medicine medicine) {
        String UUIDStr = medicine.getId().toString();
        mDatabase.delete(MedicineDbSchema.MedicineTable.NAME, MedicineDbSchema.MedicineTable.cols.UUID + " = ?", new String[]{UUIDStr});
    }

    public List<Medicine> getMedicines() {
        ArrayList<Medicine> medicines = new ArrayList<>();

        MedicineCursorWrapper cursor = queryMedicines(null, null);
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                medicines.add(cursor.getMedicine());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
        return medicines;
    }

    private ContentValues getContentValues(Medicine medicine) {

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
}
