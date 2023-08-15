package com.example.sleepstudytermproject.models;

import com.google.gson.Gson;

public class SleepStudy {

    private int age;

    private int bedTimeHour;
    private int bedTimeMinute;
    private int wakeUpHour;
    private int wakeUpMinute;

    public SleepStudy(int age, int bedTimeHour, int bedTimeMinute, int wakeUpHour, int wakeUpMinute) {
        this.age = checkAndGetGreaterThanZeroAge(age, "Age");
        this.bedTimeHour = checkAndGetGreaterThanZeroAge(bedTimeHour, "BedTime Hour");
        this.bedTimeMinute = checkAndGetGreaterThanZeroAge(bedTimeMinute, "BedTime Minute");
        this.wakeUpHour = checkAndGetGreaterThanZeroAge(wakeUpHour, "Wakeup Hour");
        this.wakeUpMinute = checkAndGetGreaterThanZeroAge(wakeUpMinute, "Wakeup Minute");
    }

    public SleepStudy() {
        this(0, 0, 0, 0, 0);
    }

    public void setAge (int age)
    {
        this.age = checkAndGetGreaterThanZeroAge(age, "Age");
    }

    public void setBedTimeHour(int bedTimeHour) {
        this.bedTimeHour = checkAndGetGreaterThanZeroAge(bedTimeHour, "BedTime Hour");
    }

    public void setBedTimeMinute(int bedTimeMinute) {
        this.bedTimeMinute = checkAndGetGreaterThanZeroAge(bedTimeMinute, "BedTime Minute");
    }

    public void setWakeUpHour(int wakeUpHour) {
        this.wakeUpHour = checkAndGetGreaterThanZeroAge(wakeUpHour, "Wakeup Hour");
    }

    public void setWakeUpMinute(int wakeUpMinute) {
        this.wakeUpMinute = checkAndGetGreaterThanZeroAge(wakeUpMinute, "Wakeup Minute");
    }

    public int getBedTimeHour() {
        return bedTimeHour;
    }

    public int getBedTimeMinute() {
        return bedTimeMinute;
    }

    public int getWakeUpHour() {
        return wakeUpHour;
    }

    public int getWakeUpMinute() {
        return wakeUpMinute;
    }

    private int checkAndGetGreaterThanZeroAge (int value, String description) {
        if (value >= 0)
            return value;
        else
            throw new IllegalArgumentException(description + " must be greater than zero.");
    }
    public int getAge ()
    {
        return age;
    }



/*
    public int getSleep()
    {
        if (age > 0 && bedTime > 0 && wakeUp >0 ) {
          return bedTime-wakeUp;
        }
        else
            throw new IllegalStateException ("Cannot get sleep before entering all required information.");
    }
*/
/*
    public String getSleepAmount ()
    {
        String sleepGroup;
        int totalSleep = getSleep();

        if (totalSleep < 5)
            sleepGroup = "You must get more sleep";
        else if (totalSleep < 8)
            sleepGroup = "Healthy amount of sleep!";
        else if (totalSleep < 12)
            sleepGroup = "WOW! Fantabulous sleep!";
        else
            sleepGroup = "That's enough sleep at this point!";

        return sleepGroup;
    }*/
public String getCalcResult() {
    //check for 0s
    if(wakeUpHour <0 || wakeUpMinute < 0 || bedTimeHour <0 || bedTimeMinute <0 ){
        throw new IllegalArgumentException("All times must be greater than 0");
    }

    // Calculate sleep duration
    int sleepHours = calculateSleepHours(wakeUpHour, wakeUpMinute, bedTimeHour, bedTimeMinute);

    // Determine sleep recommendation based on age and time person slept
    String result;
    if (age >= 6 && age <= 12 && sleepHours >= 9 && sleepHours <= 12) {
        result = "You slept for:" + sleepHours + " Hours" + "healthy for school age";
    } else if (age >= 13 && age <= 18 && sleepHours >= 8 && sleepHours <= 10) {
        result = "You slept for:" + sleepHours + " Hours" + "teenager";
    } else if (age >= 18 && age <= 60 && sleepHours >= 7) {
        result = "You slept for:" + sleepHours + " Hours" + "adult";
    } else {
        result = "You slept for:" + sleepHours + " Hours" + "not good";
    }
    return result;
}

    private int calculateSleepHours(int wakeUpHour, int wakeUpMinute, int bedtimeHour, int bedtimeMinute) {
        // Calculate sleep duration in hours
        int sleepHours = bedtimeHour - wakeUpHour;
        int sleepMinutes = bedtimeMinute - wakeUpMinute;

        if (sleepMinutes < 0) {
            sleepHours--;
            sleepMinutes += 60;
        }

        return sleepHours;
    }
    public static SleepStudy getObjectFromJSONString (String json)
    {
        Gson gson = new Gson ();
        return gson.fromJson (json, SleepStudy.class);
    }

    public static String getJSONStringFromObject (SleepStudy object)
    {
        Gson gson = new Gson ();
        return gson.toJson (object);
    }

    public String getJSONStringFromThis()
    {
        return SleepStudy.getJSONStringFromObject (this);
    }
}


