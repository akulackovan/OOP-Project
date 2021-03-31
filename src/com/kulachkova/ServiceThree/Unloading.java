package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.typeOfCargo;

import javax.swing.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Phaser;

public class Unloading {

    public Unloading (List<Ship> ships) throws InterruptedException {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips(ships);
        looseList = arrivalOfShips.getLoose();
        liquidList = arrivalOfShips.getLiquid();
        containerList = arrivalOfShips.getContainer();
        Phaser phaser = new Phaser(3);
        new Crane(phaser, typeOfCargo.LOOSE, looseList);
        new Crane(phaser, typeOfCargo.LIQUID, liquidList);
        new Crane(phaser, typeOfCargo.CONTAINER, containerList);
    }

    public List<Ship> getListAll () {
        List<Ship> all = new ArrayList<>();
        all.addAll(0, looseList);
        all.addAll(looseList.size(), liquidList);
        all.addAll(liquidList.size(), containerList);
        return all;
    }

    public void getResult () {
        /*List<Ship> ships = getListAll();
        for (Ship ship : ships) {
            print(ship);
        }
        System.out.println("\n\n===========================================================\n       RESULT");
        System.out.println("Max daley " + maxDelay);
        System.out.println("Middle daley " + (allDelay / numberOfShips));
        System.out.println("Number of ships " + numberOfShips);
        System.out.println("All time wait " + convertTime(time));
        System.out.println("Time max " + convertTime(timeMax));
        System.out.println("Time min " + convertTime(timeMin));
        System.out.println("All fine " + fine);
        System.out.println("Number Of Crane " + numberOfCrane);*/
    }

    public void print (Ship ship) {
        /*.out.println("==========================================\nShip №" + numberOfShips + "\nName " +
                ship.getName_() + "\nTime arrive real " + ship.getRealTimeArrival_() +
                "\nType " + ship.getTypeOfCargo() + "\nNeed arrival " + ship.getTimeBegin_() +
                "\nTime begin unloading " + ship.getRealTimeBegin_() + "\nTime end real " + ship.getRealTimeEnd_() +
                "\nWait " + ship.getWaitTime_() + "\nTime uploading " + timeUploading(ship) +
                "\nFine " + ship.getFine_() + "\nTime end  " + ship.getTimeEnd_());*/
    }



    public String timeUploading (Ship ship) {
        Timestamp need = ship.getRealTimeBegin_();
        Timestamp read = ship.getRealTimeEnd_();
        long milliseconds = read.getTime() - need.getTime();
        return convertTime(milliseconds);
    }

    public String convertTime (long time) {
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 24);
        int min = (int) (time / 1000 / 60 % 60);
        return String.format("%02d:%02d:%02d", day, hour, min);
    }

    private List<Ship> looseList;
    private List<Ship> liquidList;
    private List<Ship> containerList;
    private long fine = 0;
    private int numberOfShips = 0;
    private int numberOfShipsInQueue = 0;
    private int numberOfCrane = 0;
    private long time = 0;
    private long timeMin = 0;
    private long timeMax = 0;
    private int maxDelay = 0;
    private int allDelay = 0;

}
