package be.helha.medictime.db;

public class MedicineDbSchema {

    public static final class MedicineTable {
        public static final String NAME = "medicines";
        public static final class cols {
            public static final String UUID = "uuid";
            public static final String NAME = "name";
            public static final String START_DATE = "start_date";
            public static final String END_DATE = "end_date";
            public static final String MORNING_INTAKE = "morning_intake";
            public static final String LUNCH_TIME_INTAKE = "lunch_time_intake";
            public static final String EVENING_INTAKE = "evening_intake";
        }

    }
}
