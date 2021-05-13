package com.akulackovan.Service.serviceThree;


import com.akulackovan.Service.entity.Ship;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;

public class ArrivalOfShips {

    private final List<Ship> loose;
    private final List<Ship> liquid;
    private final List<Ship> container;

    public ArrivalOfShips (List<Ship> ships) {
        loose = new ArrayList<>();
        liquid = new ArrayList<>();
        container = new ArrayList<>();
        read(ships);
        loose.sort(Comparator.comparing(Ship::getRealTimeArrival_));
        liquid.sort(Comparator.comparing(Ship::getRealTimeArrival_));
        container.sort(Comparator.comparing(Ship::getRealTimeArrival_));
    }

    public List<Ship> getContainer () {
        return container;
    }

    public List<Ship> getLiquid () {
        return liquid;
    }

    public List<Ship> getLoose () {
        return loose;
    }

    private void read (List<Ship> ships) {
        for (Ship o : ships) {
            randomTime(o);
            addToList(o);
        }
    }

    private void addToList (Ship ship) {
        switch (ship.getTypeOfCargo()) {
            case LOOSE: {
                loose.add(ship);
            }
            case CONTAINER: {
                container.add(ship);
            }
            case LIQUID: {
                liquid.add(ship);
            }
        }
    }

    private void randomTime (Ship ship) {
        Calendar cal = Calendar.getInstance();
        Timestamp timestamp = ship.getTimeBegin_();
        cal.setTimeInMillis(timestamp.getTime());
        int minutes = (int) (Math.random() * (10080 * 2 + 1)) - 10080; //7 days is 10080 minutes
        cal.add(Calendar.MINUTE, minutes);
        timestamp = new Timestamp(cal.getTime().getTime());
        ship.setRealTimeArrival_(timestamp);
        ship.setRealTimeBegin_(timestamp);
        ship.setDelay_((int) (Math.random() * 1440));
    }

}
