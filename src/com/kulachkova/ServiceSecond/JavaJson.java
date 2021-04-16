package com.kulachkova.ServiceSecond;

import com.kulachkova.ServiceOne.Ship;
import com.kulachkova.ServiceOne.ShipGenerator;
import com.kulachkova.ServiceThree.Unloading;
import org.json.*;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class JavaJson {

    private List<Ship> ships;

    public List<Ship> runServiceOne (int number) {
        ShipGenerator ship = new ShipGenerator(number);
        ships = ship.getShips();
        write();
        return ships;
    }

    public void write () {
        while (true) {
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
            if (number < 1)
            {
                System.out.println("Number>0");
                continue;
            }
            System.out.print("Day of begin: ");
            int day = in.nextInt();
            if (day > 30 || day < 1)
            {
                System.out.println("Day [1; 30]");
                continue;
            }
            System.out.print("Hour of begin: ");
            int hour = in.nextInt();
            if (hour > 23 || hour < 0)
            {
                System.out.println("Day [0; 23]");
                continue;
            }
            System.out.print("Minute of begin: ");
            int minute = in.nextInt();
            if (minute > 60 || minute < 0)
            {
                System.out.println("Minute [0; 59]");
                continue;
            }
            String time = "2021-04-" + day + " " + hour + ":" + minute + ":" + "00.000";
            Timestamp timestamp = Timestamp.valueOf(time);
            Ship ship = new Ship(string, timestamp, new Ship.Cargo(type, number));
            ships.add(ship);
        }
    }

    public void write (Unloading unloading) {
        List<Ship> ships = unloading.getList();
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
        JSONObject report = new JSONObject();
        report.put("Fine", unloading.getFine());
        report.put("All Delay", unloading.getAllDelay());
        report.put("Max Delay", unloading.getMaxDelay());
        report.put("Number Of Crane Container", unloading.getNumberOfCraneContainer());
        report.put("Number Of Crane Liquid", unloading.getNumberOfCraneLiquid());
        report.put("Number Of Crane Loose", unloading.getNumberOfCraneLoose());
        report.put("Number Of Ships", unloading.getNumberOfShips());
        report.put("Time Wait", unloading.getTimeWait());
        shipJSON.put(report);
        try (FileWriter file = new FileWriter("src\\resource\\arrivalOfShips.JSON")) {
            file.write(shipJSON.toString());
            System.out.println("Successfully Copied JSON Object to File...");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }


}
