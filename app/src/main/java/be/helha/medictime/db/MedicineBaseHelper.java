package be.helha.medictime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import be.helha.medictime.db.MedicineDbSchema.MedicineTable;

public class MedicineBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "medicineBase.db";
    public MedicineBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ MedicineTable.NAME + "("
                + "_id integer PRIMARY KEY AUTOINCREMENT, "
                + MedicineTable.cols.UUID + ", " + MedicineTable.cols.NAME + ", "
                + MedicineTable.cols.START_DATE + ", " + MedicineTable.cols.END_DATE + ", " + MedicineTable.cols.MORNING_INTAKE + ", "
                + MedicineTable.cols.LUNCH_TIME_INTAKE + ", " + MedicineTable.cols.EVENING_INTAKE + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
