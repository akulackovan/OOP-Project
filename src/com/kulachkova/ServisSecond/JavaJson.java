package com.kulachkova.ServisSecond;

import com.kulachkova.ServisOne.Ship;
import com.kulachkova.ServisOne.ShipGenerator;
import org.json.*;

import java.io.FileWriter;
import java.util.List;

public class JavaJson {

    public void runServisOne() {
        ShipGenerator ship = new ShipGenerator(10);
        List<Ship> ships = ship.getShips();
        System.out.println(ship);
        JSONArray shipJSON = new JSONArray();
        for (int i = 0; i < ships.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name ship", ships.get(i).getName_());
            jsonObject.put("Arrival date", ships.get(i).getTimeBegin_());
            jsonObject.put("Departure date", ships.get(i).getTimeEnd_());
            jsonObject.put("Type of cargo", ships.get(i).getNameType());
            jsonObject.put("Number of cargo", ships.get(i).getNumberOfCargo());
            shipJSON.put(jsonObject);
        }
        try (FileWriter file = new FileWriter("src\\resourse\\ship.JSON")) {
            file.write(shipJSON.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
