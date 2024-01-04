package be.helha.medictime.db;

import static java.lang.Boolean.getBoolean;
import static java.lang.Boolean.logicalAnd;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import java.util.Date;
import java.util.UUID;

import be.helha.medictime.models.Medicine;

public class MedicineCursorWrapper extends CursorWrapper {

    public MedicineCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Medicine getMedicine()
    {
        String uuidString = getString(getColumnIndex(MedicineDbSchema.MedicineTable.cols.UUID));
        String name = getString(getColumnIndex(MedicineDbSchema.MedicineTable.cols.NAME));
        String start_date = getString(getColumnIndex(MedicineDbSchema.MedicineTable.cols.START_DATE));
        String end_date = getString(getColumnIndex(MedicineDbSchema.MedicineTable.cols.END_DATE));
        int morningIntake = getInt(getColumnIndex(MedicineDbSchema.MedicineTable.cols.MORNING_INTAKE));
        int lunchTimeIntake = getInt(getColumnIndex(MedicineDbSchema.MedicineTable.cols.LUNCH_TIME_INTAKE));
        int eveningIntake = getInt(getColumnIndex(MedicineDbSchema.MedicineTable.cols.EVENING_INTAKE));

        Medicine medicine = new Medicine(UUID.fromString(uuidString));
        medicine.setName(name);
        medicine.setStartDate(start_date);
        medicine.setEndDate(end_date);
        if (morningIntake == 1) {
            medicine.setMorningIntake(true);
        } else {
            medicine.setMorningIntake(false);
        }
        if (lunchTimeIntake == 1) {
            medicine.setLunchTimeIntake(true);
        } else {
            medicine.setLunchTimeIntake(false);
        }
        if (eveningIntake == 1) {
            medicine.setEveningIntake(true);
        } else {
            medicine.setEveningIntake(false);
        }
        return medicine;
    }
}
