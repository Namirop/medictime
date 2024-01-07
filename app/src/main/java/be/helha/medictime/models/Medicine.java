package be.helha.medictime.models;


import android.annotation.SuppressLint;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Medicine implements Serializable {
    private UUID mId;
    public String mName;
    public String mStartDate;
    public String mEndDate;
    public Boolean mMorningIntake;
    public Boolean mLunchTimeIntake;
    public Boolean mEveningIntake;

    public Medicine(UUID id) {
        mId = id;
    }
    public Medicine() {
        mId = UUID.randomUUID();
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

    public void setStartDate(String startDate) {
        mStartDate = startDate;
    }
    public String getStartDate() {
        return mStartDate;
    }

    public void setEndDate(String endDate) {
        mEndDate = endDate;
    }

    public void setStartAndEndDate(int defaultTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        Date startDate = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        mStartDate = sdf1.format(startDate);

        calendar.add(Calendar.DAY_OF_YEAR, defaultTime);
        Date endDate = calendar.getTime();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");
        mEndDate = sdf2.format(endDate);
    }
    public String getEndDate() {
        return mEndDate;
    }
}
