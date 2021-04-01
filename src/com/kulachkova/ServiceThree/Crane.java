package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Phaser;

class Crane extends Thread {

    private int powerInHour = 0;

    public Crane (typeOfCargo type) {

        switch (type) {
            case LOOSE -> powerInHour = 1;
            case LIQUID -> powerInHour = 2;
            case CONTAINER -> powerInHour = 3;
        }
    }

    public void unloading (Ship ship) {
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, powerInHour * ship.getNumberOfCargo());
        int minuteRand = ship.getDelay_();
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
    }

}
