package com.kulachkova.ServisThree;

import com.kulachkova.ServisOne.Ship;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Unloading {

    public Unloading () throws InterruptedException {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips();
        List<Ship> looseList = arrivalOfShips.getLoose();
        List<Ship> liquidList = arrivalOfShips.getLiquid();
        List<Ship> containerList = arrivalOfShips.getContainer();
        looseCrane = new ArrayList<Crane>();
        liquidCrane = new ArrayList<Crane>();
        containerCrane = new ArrayList<Crane>();
        looseCrane.add(new Crane());
        liquidCrane.add(new Crane());
        containerCrane.add(new Crane());
        Thread loose = new Thread() {
            public void run () {
                runWork(looseCrane.get(0), looseList);
            }
        };
        Thread liquid = new Thread() {
            public void run () {
                runWork(liquidCrane.get(0), liquidList);
            }
        };
        Thread container = new Thread() {
            public void run () {
                runWork(containerCrane.get(0), containerList);
            }
        };
        loose.start();
        liquid.start();
        container.start();
    }

    public void runWork (Crane crane, List<Ship> ships) {
        for (int i = 0; i < ships.size(); i++) {
            if (i != 0) {
                queue(ships.get(i - 1), ships.get(i));
            }
            int delay = 0;
            delay = crane.unloading(ships.get(i));
            setFine(ships.get(i));
            synchronized (Unloading.class) {
                numberOfShips++;
                if (delay > maxDelay) {
                    maxDelay = delay;
                }
                allDelay += delay;
                fine += ships.get(i).getFine_();
                print(ships.get(i));
            }
        }

    }

    public void print (Ship ship) {
        System.out.println(
                "=================================================================\nShip №" + numberOfShips + "\nName "
                        + ship
                        .getName_() + "\nTime arrive real " + ship
                        .getRealTimeArrival_() + "\nNeed arrival " + ship
                        .getTimeBegin_() + "\nTime begin unloading " + ship.getRealTimeBegin_() + "\nTime end " + ship
                        .getRealTimeEnd_() + "\nWait " + ship.getWaitTime_() + "\nTime uploading " + timeUploading(
                        ship) + "\nFine " + ship.getFine_());
    }

    public void getResult () {
        System.out.println("\n\n===========================================================\n       RESULT");
        System.out.println("Max daley " + maxDelay);
        System.out.println("Middle daley " + (allDelay / numberOfShips));
        System.out.println("Number of ships " + numberOfShips);
        System.out.println("All time wait " + convertTime(time));
        System.out.println("Time max " + convertTime(timeMax));
        System.out.println("Time min " + convertTime(timeMin));
        System.out.println("All fine " + fine);
    }

    public void queue (Ship shipFirst, Ship shipSecond) {
        Timestamp first = shipFirst.getRealTimeEnd_();
        Timestamp second = shipSecond.getRealTimeBegin_();
        long milliseconds = second.getTime() - first.getTime();
        if (milliseconds < 0) {
            milliseconds *= -1;
            Calendar cal = Calendar.getInstance();
            cal.setTimeInMillis(second.getTime());
            cal.add(Calendar.MILLISECOND, Math.toIntExact(milliseconds));
            synchronized (Unloading.class) {
                if (milliseconds < timeMin || timeMin == 0) {
                    timeMin = milliseconds;
                }
                if (milliseconds > timeMax) {
                    timeMax = milliseconds;
                }
                time += milliseconds;
            }
            second = new Timestamp(cal.getTime().getTime());
            shipSecond.setWaitTime_(convertTime(milliseconds));
        }
        shipSecond.setRealTimeBegin_(second);
    }

    public void setFine (Ship ship) {
        Timestamp need = ship.getTimeEnd_();
        Timestamp read = ship.getRealTimeEnd_();
        long milliseconds = read.getTime() - need.getTime();
        if (milliseconds < 0) {
            milliseconds = (int) (time / 1000 / 3600);
            ship.setFine_((int) (milliseconds * 100));
        }
    }

    public String timeUploading (Ship ship) {
        Timestamp need = ship.getRealTimeBegin_();
        Timestamp read = ship.getRealTimeEnd_();
        long milliseconds = read.getTime() - need.getTime();
        return convertTime(milliseconds);
    }

    public String convertTime (long time) {
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600);
        int min = (int) (time / 1000 / 60 % 60);
        return String.format("%02d:%02d:%02d", day, hour, min);
    }

    private List<Crane> looseCrane;
    private List<Crane> liquidCrane;
    private List<Crane> containerCrane;
    private List<Ship> all;
    private int fine = 0;
    private int numberOfShips = 0;
    private long time = 0;
    private long timeMin = 0;
    private long timeMax = 0;
    private int maxDelay = 0;
    private int allDelay = 0;

}
