package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

import java.sql.Timestamp;
import java.util.Calendar;

public class Crane {

    public Crane (typeOfCargo type) {
        switch (type) {
            case LOOSE -> powerInHour = 1;
            case LIQUID -> powerInHour = 2;
            case CONTAINER -> powerInHour = 3;
        }
    }

    public int unloading (Ship ship) {
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, powerInHour * ship.getNumberOfCargo());
        int minuteRand = (int) (Math.random() * 1440);
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
        return minuteRand;
    }

    int powerInHour = 0;
}
