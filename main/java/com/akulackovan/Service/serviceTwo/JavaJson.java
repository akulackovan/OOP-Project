package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.entity.Ship;
import com.akulackovan.Service.exeption.ServiceFileNotFoundException;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JavaJson {

    private List<Ship> ships;

    public List<Ship> readServiceOne (String string) throws ServiceFileNotFoundException {
        ships = new ArrayList<>();
        JSONParser jsonParser = new JSONParser();
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(string);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Ships");
            for (Object o : jsonArray) {
                JSONObject ship = (JSONObject) o;
                String name = (String) ship.get("Name ship");
                String time = (String) ship.get("Arrival date");
                Timestamp arrivalDate = Timestamp.valueOf(time);
                String type = (String) ship.get("Type of cargo");
                int number = Integer.parseInt(String.valueOf(ship.get("Number of cargo")));
                ships.add(new Ship(name, arrivalDate, new Ship.Cargo(type, number)));
            }
            System.out.println("Service 1 was read");
        }
        catch (ParseException e) {
            throw new ServiceFileNotFoundException("File not found");
        }
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
            if (number < 1) {
                System.out.println("Number>0");
                continue;
            }
            System.out.print("Day of begin: ");
            int day = in.nextInt();
            if (day > 30 || day < 1) {
                System.out.println("Day [1; 30]");
                continue;
            }
            System.out.print("Hour of begin: ");
            int hour = in.nextInt();
            if (hour > 23 || hour < 0) {
                System.out.println("Day [0; 23]");
                continue;
            }
            System.out.print("Minute of begin: ");
            int minute = in.nextInt();
            if (minute > 60 || minute < 0) {
                System.out.println("Minute [0; 59]");
                continue;
            }
            String time = "2021-04-" + day + " " + hour + ":" + minute + ":" + "00.000";
            Timestamp timestamp = Timestamp.valueOf(time);
            Ship ship = new Ship(string, timestamp, new Ship.Cargo(type, number));
            ships.add(ship);
        }
    }

    /*public void writeServiceThree (Unloading unloading) {
        List<Ship> ships = unloading.getList();
        JSONObject report = new JSONObject();
        report.put("Fine", unloading.getFine());
        report.put("All Delay", unloading.getAllDelay());
        report.put("Max Delay", unloading.getMaxDelay());
        report.put("Number Of Crane Container", unloading.getNumberOfCraneContainer());
        report.put("Number Of Crane Liquid", unloading.getNumberOfCraneLiquid());
        report.put("Number Of Crane Loose", unloading.getNumberOfCraneLoose());
        report.put("Number Of Ships", unloading.getNumberOfShips());
        report.put("Time Wait", unloading.getTimeWait());
        JSONArray shipJSON = new JSONArray();
        for (Ship ship : ships) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name ship", ship.getName_());
            jsonObject.put("Arrival date", ship.getTimeBegin_().toString());
            jsonObject.put("Departure date", ship.getTimeEnd_().toString());
            jsonObject.put("Type of cargo", ship.getNameType());
            jsonObject.put("Number of cargo", ship.getNumberOfCargo());
            jsonObject.put("Real arrival date", ship.getRealTimeArrival_().toString());
            jsonObject.put("Real time begin unloading", ship.getRealTimeBegin_().toString());
            jsonObject.put("Real time end unloading", ship.getRealTimeEnd_().toString());
            jsonObject.put("Wait time", ship.getWaitString());
            jsonObject.put("Fine", ship.getFine_());
            shipJSON.add(jsonObject);
        }
        JSONObject finalOutput = new JSONObject();
        finalOutput.put("Report", report);
        finalOutput.put("Ships", shipJSON);
        try (FileWriter file = new FileWriter("src\\resource\\report.JSON")) {
            file.write(finalOutput.toString());
            System.out.println("Service 3 was written in JSON");
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }*/
}
