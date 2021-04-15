package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

import java.sql.Timestamp;
import java.util.Calendar;

class Crane {

    private int powerInHour;

    public Crane (typeOfCargo type) {
        powerInHour = type.getHour();
    }

    public void unloading (Ship ship) {
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        int number = ship.getNumberOfCargo();
        while (number != 0) {
            cal.add(Calendar.HOUR, powerInHour);
            number--;
        }
        int minuteRand = ship.getDelay_();
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
    }

}
