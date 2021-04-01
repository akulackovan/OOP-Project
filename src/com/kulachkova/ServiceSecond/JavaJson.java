package com.kulachkova.ServiceSecond;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.ShipGenerator;
import org.json.*;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class JavaJson {

    private List<Ship> ships;

    public List<Ship> runServiceOne () {
        ShipGenerator ship = new ShipGenerator(100);
        ships = ship.getShips();
        write();
        return ships;
    }

    public void write () {
        while (true)
        {
            System.out.println("Add ship? Y/N\n");
            Scanner in = new Scanner(System.in);
            String string = in.next();
            if (!string.equals("Y")) {
                return;
            }
            System.out.print("Name: ");
            string = in.next();
            System.out.print("Cargo type(loose, liquid, container): ");
            String type = in.next();
            System.out.print("Cargo number: ");
            int number = in.nextInt();
            System.out.print("Day of begin: ");
            int day = in.nextInt();
            System.out.print("Hour of begin: ");
            int hour = in.nextInt();
            System.out.print("Minute of begin: ");
            int minute = in.nextInt();
            String time = "2021-04-" + day + " " + hour + ":" + minute + ":" + "00.000";
            Timestamp timestamp = Timestamp.valueOf(time);
            Ship ship = new Ship(string, timestamp, new Ship.Cargo(type, number));
            ships.add(ship);
        }
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
        try (FileWriter file = new FileWriter("src\\resource\\arrivalOfShips.JSON")) {
            file.write(shipJSON.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


}
