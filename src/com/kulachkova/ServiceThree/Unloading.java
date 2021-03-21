package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Unloading {

    public Unloading (List<Ship> ships) {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips(ships);
        looseList = arrivalOfShips.getLoose();
        liquidList = arrivalOfShips.getLiquid();
        containerList = arrivalOfShips.getContainer();
        for (int i = 0; i < looseList.size(); i++) {
            workCrane(typeOfCargo.LOOSE, looseList);
        }
        for (int i = 0; i < liquidList.size(); i++) {
            workCrane(typeOfCargo.LIQUID, liquidList);
        }
        for (int i = 0; i < containerList.size(); i++) {
            workCrane(typeOfCargo.CONTAINER, containerList);
        }
    }

    public List<Ship> getListAll () {
        List<Ship> all = new ArrayList<>();
        all.addAll(0, looseList);
        all.addAll(looseList.size(), liquidList);
        all.addAll(liquidList.size(), containerList);
        return all;
    }

    public void workCrane (typeOfCargo type, List<Ship> ships) {
        Thread loose = new Thread(() -> {
            int last = -1;
            Crane crane = new Crane(type);
            synchronized (Unloading.class) {
                numberOfCrane += 1;
            }
            for (int j = 0; j < ships.size(); j++) {
                int i = 0;
                synchronized (Unloading.class) {
                    while (i != ships.size() && ships.get(i).getUploading()) {
                        i++;
                    }
                    if (i == ships.size()) return;
                    ships.get(i).isUploading(true);
                    if (last != -1) {
                        queue(ships.get(last), ships.get(i));
                    }
                }
                int delay = crane.unloading(ships.get(i));
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
                last = i;
            }
        });
        loose.start();
    }

    public void print (Ship ship) {
        System.out.println("==========================================\nShip №" + numberOfShips + "\nName " +
                ship.getName_() + "\nTime arrive real " + ship.getRealTimeArrival_() +
                "\nType " + ship.getTypeOfCargo() + "\nNeed arrival " + ship.getTimeBegin_() +
                "\nTime begin unloading " + ship.getRealTimeBegin_() + "\nTime end real " + ship.getRealTimeEnd_() +
                "\nWait " + ship.getWaitTime_() + "\nTime uploading " + timeUploading(ship) +
                "\nFine " + ship.getFine_() + "\nTime end  " + ship.getTimeEnd_());
    }

    public void getResult () {
        fine += numberOfCrane * 30000;
        System.out.println("\n\n===========================================================\n       RESULT");
        System.out.println("Max daley " + maxDelay);
        System.out.println("Middle daley " + (allDelay / numberOfShips));
        System.out.println("Number of ships " + numberOfShips);
        System.out.println("All time wait " + convertTime(time));
        System.out.println("Time max " + convertTime(timeMax));
        System.out.println("Time min " + convertTime(timeMin));
        System.out.println("All fine " + fine);
        System.out.println("Number Of Crane " + numberOfCrane);
        if (numberOfShipsInQueue == 0) {
            numberOfShipsInQueue = 1;
        }
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
        if (milliseconds > 0) {
            long fine = (int) ((milliseconds / 1000 / 3600 % 60 ) + 24 * (milliseconds / (24 * 60 * 60 * 1000) ) * 100);
            ship.setFine_((int) (fine));
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
        int hour = (int) (time / 1000 / 3600 % 60);
        int min = (int) (time / 1000 / 60 % 60);
        return String.format("%02d:%02d:%02d", day, hour, min);
    }

    private final List<Ship> looseList;
    private final List<Ship> liquidList;
    private final List<Ship> containerList;
    private long fine = 0;
    private int numberOfShips = 0;
    private int numberOfShipsInQueue = 0;
    private int numberOfCrane = 0;
    private long time = 0;
    private long timeMin = 0;
    private long timeMax = 0;
    private int maxDelay = 0;
    private int allDelay = 0;
    private final int length = 0;

}
