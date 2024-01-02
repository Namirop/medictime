package be.helha.medictime.models;

import androidx.annotation.NonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Medicine {
    private UUID mId;
    public String mName;
    public Date mStartDate;
    public Date mEndDate;
    public Boolean mMorningIntake;
    public Boolean mLunchTimeIntake;
    public Boolean mEveningIntake;

    public Medicine(UUID id) {
        mId = id;
    }
    public Medicine() {
        mId = UUID.randomUUID();
        mStartDate = new Date();
        mMorningIntake = false;
        mLunchTimeIntake = false;
        mEveningIntake = false;
    }

    public UUID getId() {
        return mId;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
    public String getName() {
        return mName;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }

    public void setMorningIntake(Boolean morningIntake) {
        mMorningIntake = morningIntake;
    }
    public Boolean getMorningIntake() {
        return mMorningIntake;
    }

    public void setLunchTimeIntake(Boolean lunchTimeIntake) {
        mLunchTimeIntake = lunchTimeIntake;
    }
    public Boolean getLunchTimeIntake() {
        return mLunchTimeIntake;
    }

    public void setEveningIntake(Boolean eveningIntake) {
        mEveningIntake = eveningIntake;
    }
    public Boolean getEveningIntake() {
        return mEveningIntake;
    }

    public void setStartDate(Date startDate) {
        mStartDate = startDate;
    }
    public Date getStartDate() {
        return mStartDate;
    }

    public void setEndDate(Date endDate) {
        mEndDate = endDate;
    }
    public void setFirstEndDate(int defaultTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_YEAR, defaultTime);
        this.mEndDate = calendar.getTime();
    }
    public Date getEndDate() {
        return mEndDate;
    }
}
