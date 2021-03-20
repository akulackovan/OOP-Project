package com.kulachkova.ServiceThree;

import com.kulachkova.ServiceOne.Ship;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.*;


public class ArrivalOfShips {

    public ArrivalOfShips () {
        loose = new ArrayList<Ship>();
        liquid = new ArrayList<Ship>();
        container = new ArrayList<Ship>();
        read();
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

    private void read () {
        try (FileReader reader = new FileReader("src\\resourse\\ship.JSON")) {
            JSONParser jsonParser = new JSONParser();
            JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);
            for (Object o : jsonArray) {
                JSONObject object = (JSONObject) o;
                String typeCargo = (String) object.get("Type of cargo");
                String name = (String) object.get("Name ship");
                String arrivalDate = (String) object.get("Arrival date");
                String departureDate = (String) object.get("Departure date");
                Timestamp begin = Timestamp.valueOf(arrivalDate);
                Timestamp end = Timestamp.valueOf(departureDate);
                long numberStr = (long) object.get("Number of cargo");
                Ship ship = new Ship(name, begin, end, typeCargo, Math.toIntExact(numberStr));
                randomTime(ship);
                addToList(ship);
            }
        }
        catch (Exception e) {
            System.out.println(e);
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
    }

    private List<Ship> loose;
    private List<Ship> liquid;
    private List<Ship> container;
}
