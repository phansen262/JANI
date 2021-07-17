package com.sticknology.jani.data2;

public class Interval2 {

    private Distance mDistance;
    private String mEffort;
    private MyTime mPace, mTime;

    //Object creation method
    public Interval2(Distance distance, String effort, MyTime pace, MyTime time){

        mDistance = distance;
        mEffort = effort;
        mPace = pace;
        mTime = time;
    }

    //Only getters and setters below here
    public Distance getDistance(){return mDistance;}

    public void setDistance(Distance distance){mDistance = distance;}

    public String getEffort(){return mEffort;}

    public void setEffort(String effort){mEffort = effort;}

    public MyTime getPace(){return mPace;}

    public void setPace(MyTime pace){mPace = pace;}

    public MyTime getTime(){return mTime;}

    public void setTime(MyTime time){mTime = time;}
}