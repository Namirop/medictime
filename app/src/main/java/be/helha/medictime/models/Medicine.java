package be.helha.medictime.models;

import java.util.UUID;

public class Medicine {
    private UUID mId;

    public Medicine() {
        mId = UUID.randomUUID();
    }

    public UUID getId() {
        return mId;
    }
}
