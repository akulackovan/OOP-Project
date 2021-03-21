package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

import java.sql.Timestamp;
import java.util.Calendar;

public class Crane {

    public Crane (typeOfCargo type) {
        switch (type) {
            case LOOSE -> power = 1;
            case LIQUID -> power = 2;
            case CONTAINER -> power = 1;
        }
    }

    public int unloading (Ship ship) {
        work = true;
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, power * ship.getNumberOfCargo());
        int minuteRand = (int) (Math.random() * 1440);
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
        work = false;
        return minuteRand;
    }

    int power = 0;
    boolean work = false;
}
