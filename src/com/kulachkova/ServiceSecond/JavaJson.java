package com.kulachkova.ServiceSecond;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.ShipGenerator;
import org.json.*;

import java.io.FileWriter;
import java.util.List;

public class JavaJson {

    public List<Ship> runServiceOne () {
        ShipGenerator ship = new ShipGenerator(10);
        return ship.getShips();
    }

    public void write (List<Ship> ships) {
        JSONArray shipJSON = new JSONArray();
        for (Ship ship : ships) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name ship", ship.getName_());
            jsonObject.put("Arrival date", ship.getTimeBegin_());
            jsonObject.put("Departure date", ship.getTimeEnd_());
            jsonObject.put("Type of cargo", ship.getNameType());
            jsonObject.put("Number of cargo", ship.getNumberOfCargo());
            jsonObject.put("Real arrival date", ship.getRealTimeArrival_());
            jsonObject.put("Real time begin unloading", ship.getRealTimeBegin_());
            jsonObject.put("Real time end unloading", ship.getRealTimeEnd_());
            jsonObject.put("Wait time", ship.getWaitTime_());
            jsonObject.put("Fine", ship.getFine_());
            shipJSON.put(jsonObject);
        }
        try (FileWriter file = new FileWriter("src\\resourse\\arrivalOfShips.JSON")) {
            file.write(shipJSON.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


}
