package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Phaser;

public class Worker {

    List<Ship> ships;
    typeOfCargo type;
    int lastShip;
    long fine = 0;

    public Worker (int number, List<Ship> ships, typeOfCargo type) throws InterruptedException {
        this.ships = ships;
        this.type = type;
        this.lastShip = 0;
        fine = 30000 * number;
        for (int i = 0; i < number; i++) {
            work();
        }
    }

    public long getFine()
    {
        return fine;
    }

    public void work () {
        new Thread(new Runnable() {
            @Override
            public void run () {
                Crane crane = new Crane(type);
                int last = -1;
                for (int i = 0; i < ships.size(); ) {
                    i = lastShip;
                    if (i >= ships.size()) {
                        return;
                    }
                    synchronized (Worker.class) {
                        lastShip++;
                    }
                    if (last != -1) {
                        queue(ships.get(last), ships.get(i));
                    }
                    if (i >= ships.size()) break;
                    crane.unloading(ships.get(i));
                    ships.get(i).setFine_();
                    last = i;
                    synchronized (Worker.class) {
                        fine += ships.get(i).getFine_();
                    }
                }
            }
        }).start();
    }

    public synchronized void queue (Ship shipFirst, Ship shipSecond) {
        Timestamp first = shipFirst.getRealTimeEnd_();
        Timestamp second = shipSecond.getRealTimeArrival_();
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


    public List<Ship> getShips () {
        return ships;
    }
}
