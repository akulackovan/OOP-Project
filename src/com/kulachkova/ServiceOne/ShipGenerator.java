package com.kulachkova.ServiceOne;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShipGenerator {

    public ShipGenerator (int number) {
        ships = new ArrayList<Ship>();
        for (int i = 0; i < number; i++) {
            ships.add(CreateShip());
        }
        Collections.sort(ships);
        System.out.println(ships);
    }

    public List<Ship> getShips () {
        return ships;
    }

    @Override
    public String toString () {
        StringBuilder string = new StringBuilder("Schedule of ships\n");
        for (int i = 0; i < ships.size(); i++) {
            string.append("Ship №").append(i + 1).append("\n").append(String.valueOf(ships.get(i))).append("\n\n");
        }
        return string.toString();
    }

    public Ship.Cargo CreateCargo () {
        String[] nameOfType = new String[]{"loose", "liquid", "container"};
        String name = "";
        switch ((int) (Math.random() * 3)) {
            case 0 -> {
                name = nameOfType[0];
            }
            case 1 -> {
                name = nameOfType[1];
            }
            case 2 -> {
                name = nameOfType[2];
            }
        }
        int number = (int) (Math.random() * 50) + 1;
        return new Ship.Cargo(name, number);
    }

    private Ship CreateShip () {
        String[] nameOfShips = new String[]{"Alexandria", "Blue", "Aurora", "Gotha", "Grossadler", "Hawksub",
                "Sea Queen", "Vulkan", "Empress", "Titanic", "Lenin", "Varyag", "Bismark", "Bebop", "Colossan", "Inferno",
                "St. George", "Valhalla", "Stand. Victoria", "Calypso", "Demetor"};
        long rangebegin = Timestamp.valueOf("2021-04-01 00:00:00").getTime();
        long rangeend = Timestamp.valueOf("2021-04-30 00:59:00").getTime();
        Timestamp timeBegin = new Timestamp(rangebegin + (long) (Math.random() * (rangeend - rangebegin + 1)));
        Random random = new Random();
        return new Ship(nameOfShips[random.nextInt(nameOfShips.length)], timeBegin, CreateCargo());
    }

    private List<Ship> ships;
}
