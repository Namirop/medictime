package be.helha.medictime.db;

public class MedicineDbSchema {

    public static final class MedicineTable {
        public static final String NAME = "medicines"; // 'NAME' vaut "crimes" et est accessible via MedicineDbSchema.MedicineTable.NAME
        public static final class cols {
            public static final String UUID = "uuid"; // 'UUID' vaut "uuid" et est accessible via MedicineDbSchema.MedicineTable.cols.UUID
            public static final String TITLE = "title";
            public static final String DATE = "date";
            public static final String SOLVED = "solved";
        }
    }
}
