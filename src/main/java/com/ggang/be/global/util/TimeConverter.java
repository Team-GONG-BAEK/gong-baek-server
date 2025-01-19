package com.ggang.be.global.util;

import java.time.LocalTime;

public class TimeConverter {


    public static LocalTime toLocalTime(double time){
        int hour=(int) time;
        if(time-hour==0.5){
            return LocalTime.of(hour, 30);
        }
        return LocalTime.of(hour, 0);
    }

}
