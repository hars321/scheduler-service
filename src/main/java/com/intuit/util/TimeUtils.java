package com.intuit.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;

@UtilityClass
public class TimeUtils {
    public static Long getCurrentTimeStamp(){
        return Instant.now().toEpochMilli();
    }

}
