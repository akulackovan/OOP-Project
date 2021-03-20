package com.kulachkova.ServisThree;

import com.kulachkova.ServisOne.*;

import java.sql.Timestamp;
import java.util.Calendar;

public class Crane {

    public int unloading (Ship ship) {
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int timeInHours = switch (ship.getTypeOfCargo()) {
            case LOOSE -> 1;
            case LIQUID -> 2;
            case CONTAINER -> 1;
        };
        for (int i = 0; i < ship.getNumberOfCargo(); i++) {
            cal.add(Calendar.HOUR, timeInHours);
        }
        int minuteRand = (int) (Math.random() * 1440);
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
        return minuteRand;
    }
}
