package be.helha.medictime.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MedicineBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "medicineBase.db";
    public MedicineBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+ MedicineDbSchema.MedicineTable.NAME + "("
                + "_id integer PRIMARY KEY AUTOINCREMENT, "
                + MedicineDbSchema.MedicineTable.cols.UUID + ", " + MedicineDbSchema.MedicineTable.cols.TITLE + ", "
                + MedicineDbSchema.MedicineTable.cols.DATE + ", " + MedicineDbSchema.MedicineTable.cols.SOLVED + ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {}
}
