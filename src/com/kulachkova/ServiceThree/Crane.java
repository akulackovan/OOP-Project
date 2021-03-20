package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

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
        cal.add(Calendar.HOUR, timeInHours * ship.getNumberOfCargo());
        int minuteRand = (int) (Math.random() * 1440);
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
        return minuteRand;
    }
}
