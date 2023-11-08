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

    /*
    public Crime getCrime()
    {
        String uuidString = getString(getColumnIndex(MedicineDbSchema.CrimeTable.cols.UUID));
        String title = getString(getColumnIndex(MedicineDbSchema.CrimeTable.cols.TITLE));
        long date = getLong(getColumnIndex(MedicineDbSchema.CrimeTable.cols.DATE));
        int isSolved = getInt(getColumnIndex(MedicineDbSchema.CrimeTable.cols.SOLVED));


        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved!=0);
        return crime;
    }
    */
}
