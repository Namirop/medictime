package be.helha.medictime.db;

import android.database.Cursor;
import android.database.CursorWrapper;

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
        //int default_intake_time = getInt(getColumnIndex(MedicineDbSchema.MedicineTable.cols.DEFAULT_INTAKE_TIME));
        long start_date = getLong(getColumnIndex(MedicineDbSchema.MedicineTable.cols.START_DATE));
        long end_date = getLong(getColumnIndex(MedicineDbSchema.MedicineTable.cols.END_DATE));


        Medicine medicine = new Medicine(UUID.fromString(uuidString));
        medicine.setName(name);
        //medicine.setDefaultTime(default_intake_time);
        medicine.setStartDate(new Date(start_date));
        medicine.setEndDate(new Date(end_date));
        return medicine;
    }
}
