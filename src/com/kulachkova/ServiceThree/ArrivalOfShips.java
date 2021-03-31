package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import org.w3c.dom.UserDataHandler;

import java.sql.Timestamp;
import java.util.*;

public class ArrivalOfShips {

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
            case LOOSE -> loose.add(ship);
            case CONTAINER -> container.add(ship);
            case LIQUID -> liquid.add(ship);
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

    private List<Ship> loose;
    private List<Ship> liquid;
    private List<Ship> container;
}
