package com.sticknology.jani.data;

import java.util.ArrayList;

public class Interval {

    private final String mDistance;
    private final String mPace;
    private final String mTime;
    private final String mEffort;

    //Setter for instance construction
    public Interval(String distance, String pace, String time, String effort){

        mDistance = distance;
        mPace = pace;
        mTime = time;
        mEffort = effort;
    }

    public String getIntervalDistance(){
        return mDistance;
    }

    public String getIntervalPace(){
        return mPace;
    }

    public String getIntervalTime(){
        return mTime;
    }

    public String getIntervalEffort(){
        return mEffort;
    }
}
