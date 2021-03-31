package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.*;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Phaser;

class Crane extends Thread {

    public Crane (Phaser phaser, typeOfCargo type, List<Ship> ships) {
        this.phaser = phaser;
        this.ships = ships;
        switch (type) {
            case LOOSE -> powerInHour = 1;
            case LIQUID -> powerInHour = 2;
            case CONTAINER -> powerInHour = 3;
        }
        start();
    }

    @Override
    public void run()
    {
        int last = -1;
        for (int j = 0; j < ships.size(); j++) {
            int i = findShip(ships, last);
            if (i == ships.size()) break;
            unloading(ships.get(i));
            ships.get(i).setFine_();
            last = i;
            System.out.println("\n\n" + getName() + " " + ships.get(j));
        }
    }


    public int unloading (Ship ship) {
        Timestamp timestamp = ship.getRealTimeBegin_();
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, powerInHour * ship.getNumberOfCargo());
        int minuteRand = ship.getDelay_();
        cal.add(Calendar.MINUTE, minuteRand);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeEnd_(timestamp);
        return minuteRand;
    }

    private synchronized int findShip (List<Ship> ships, int last) {
        int i = 0;
        while (i != ships.size() && ships.get(i).getUploading()) {
            i++;
        }
        if (i == ships.size()) return i;
        ships.get(i).isUploading(true);
        if (last != -1) {
            queue(ships.get(last), ships.get(i));
        }
        return i;
    }

    public void queue (Ship shipFirst, Ship shipSecond) {
        Timestamp first = shipFirst.getRealTimeEnd_();
        Timestamp second = shipSecond.getRealTimeBegin_();
        long milliseconds = second.getTime() - first.getTime();
        if (milliseconds < 0) {
            milliseconds *= -1;
            int minute = (int) (milliseconds / 1000 / 60 % 60);
            int day = (int) (milliseconds / (24 * 60 * 60 * 1000));
            int hour = (int) (milliseconds / 1000 / 3600 % 60);
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second.getTime());
            cal.add(Calendar.MINUTE, Math.toIntExact(minute));
            cal.add(Calendar.HOUR, Math.toIntExact(hour));
            cal.add(Calendar.DAY_OF_YEAR, Math.toIntExact(day));
            second = new Timestamp(cal.getTime().getTime());
            shipSecond.setWaitTime_(convertTime(milliseconds));
        }
        shipSecond.setRealTimeBegin_(second);
    }

    public String convertTime (long time) {
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 24);
        int min = (int) (time / 1000 / 60 % 60);
        return String.format("%02d:%02d:%02d", day, hour, min);
    }

    int powerInHour = 0;
    Phaser phaser;
    List<Ship> ships;
}
