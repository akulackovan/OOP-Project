package com.akulackovan.Service.serviceThree;


import com.akulackovan.Service.entity.Ship;
import com.akulackovan.Service.entity.typeOfCargo;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;


public class Unloading {

    private List<Ship> looseList;
    private List<Ship> liquidList;
    private List<Ship> containerList;
    private long fine;
    private int numberOfShips = 0;
    private int numberOfCraneLoose = 0;
    private int numberOfCraneLiquid = 0;
    private int numberOfCraneContainer = 0;
    private long time = 0;
    private int maxDelay = 0;
    private int allDelay = 0;

    public Unloading (List<Ship> ships) throws InterruptedException {
        ArrivalOfShips arrivalOfShips = new ArrivalOfShips(ships);
        looseList = arrivalOfShips.getLoose();
        liquidList = arrivalOfShips.getLiquid();
        containerList = arrivalOfShips.getContainer();
        looseList = working("Loose", typeOfCargo.LOOSE, looseList);
        liquidList = working("Liquid", typeOfCargo.LIQUID, liquidList);
        containerList = working("Container", typeOfCargo.CONTAINER, containerList);
        numberOfShips += looseList.size() + liquidList.size() + containerList.size();
    }

    public List<Ship> working (String typeString, typeOfCargo type, List<Ship> ships) throws InterruptedException {
        System.out.println(typeString);
        long fine = 0;
        List<Ship> shipArrayList = new ArrayList<>();
        Worker worker = null;
        if (ships.size() == 0) {
            return worker.getShips();
        }
        for (int i = 1; i <= ships.size(); i++) {
            worker = new Worker(i, ships, type);
            System.out.println("Crane " + i + " Fine " + worker.getFine());
            if (i != 1 && fine < worker.getFine()) {
                this.fine += worker.getFine();
                setNumberOfCargo(type, i - 1);
                return shipArrayList;
            }
            fine = worker.getFine();
            shipArrayList = worker.getShips();
        }
        setNumberOfCargo(type, ships.size());
        return worker.getShips();
    }

    public List<Ship> getList () {
        List<Ship> all = new ArrayList<>();
        all.addAll(0, looseList);
        all.addAll(looseList.size(), liquidList);
        all.addAll(liquidList.size(), containerList);
        return all;
    }

    public void report () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            System.out.println("\nName: " + ship.getName_() + "\nTime arrive: " + ship.getRealTimeArrival_()
                    + "\nTime wait: " + ship.getWaitString() + "\nTime real begin: "
                    + ship.getRealTimeBegin_() + "\nTime uploading: " + ship.getUploadingTime());
        }
        System.out.println(
                "\n\nREPORT\nNumber of ships: " + ships.size() + "\nAll delay: " + getAllDelay() + "\nMax delay: "
                        + getMaxDelay() + "\nNumber of crane loose: " + numberOfCraneLoose + "\nNumber of crane liquid: "
                        + numberOfCraneLiquid + "\nNumber of crane container: " + numberOfCraneContainer + "\nFine: " + fine + "\nAverage waiting time: " + getTimeWait());
    }

    public long getFine () {
        return fine;
    }

    public int getNumberOfShips () {
        return numberOfShips;
    }

    public int getNumberOfCraneLoose () {
        return numberOfCraneLoose;
    }

    public int getNumberOfCraneLiquid () {
        return numberOfCraneLiquid;
    }

    public int getNumberOfCraneContainer () {
        return numberOfCraneContainer;
    }

    public int getMaxDelay () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            if (ship.getDelay_() > maxDelay) {
                maxDelay = ship.getDelay_();
            }
        }
        return maxDelay;
    }

    public int getAllDelay () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            allDelay += ship.getDelay_();
        }
        return allDelay / ships.size();
    }

    public String getTimeWait () {
        List<Ship> ships = getList();
        for (Ship ship : ships) {
            time += ship.getWaitTime_();
        }
        time /= ships.size();
        int minute = (int) (time / 1000 / 60 % 60);
        int day = (int) (time / (24 * 60 * 60 * 1000));
        int hour = (int) (time / 1000 / 3600 % 60);
        return String.format("%02d:%02d:%02d", day, hour, minute);
    }

    private void setNumberOfCargo (typeOfCargo type, int number) {
        switch (type) {
            case LIQUID: {
                numberOfCraneLiquid = number;
            }
            case LOOSE: {
                numberOfCraneLoose = number;
            }
            case CONTAINER: {
                numberOfCraneContainer = number;
            }
        }
    }
}
