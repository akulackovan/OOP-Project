package com.kulachkova.ServiceSecond;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.ShipGenerator;
import org.json.*;

import java.io.FileWriter;
import java.util.List;

public class JavaJson {

    public void runServiceOne () {
        ShipGenerator ship = new ShipGenerator(10);
        List<Ship> ships = ship.getShips();
        writeServiceOne(ships);
    }

    private void writeServiceOne (List<Ship> ships) {
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
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void getServiceThree (List<Ship> ships) {
        writeServiceThree(ships);
    }

    private void writeServiceThree (List<Ship> ships) {
        JSONArray shipJSON = new JSONArray();
        for (int i = 0; i < ships.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name ship", ships.get(i).getName_());
            jsonObject.put("Arrival date", ships.get(i).getTimeBegin_());
            jsonObject.put("Departure date", ships.get(i).getTimeEnd_());
            jsonObject.put("Type of cargo", ships.get(i).getNameType());
            jsonObject.put("Number of cargo", ships.get(i).getNumberOfCargo());
            jsonObject.put("Real arrival date", ships.get(i).getRealTimeArrival_());
            jsonObject.put("Real time begin unloading", ships.get(i).getRealTimeBegin_());
            jsonObject.put("Real time end unloading", ships.get(i).getRealTimeEnd_());
            jsonObject.put("Wait time", ships.get(i).getWaitTime_());
            jsonObject.put("Fine", ships.get(i).getFine_());
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
