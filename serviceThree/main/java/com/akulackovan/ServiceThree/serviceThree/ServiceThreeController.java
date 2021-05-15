package com.akulackovan.ServiceThree.serviceThree;

import com.akulackovan.ServiceThree.entity.Ship;


import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/serviceThree")
public class ServiceThreeController {

    private ServiceThree serviceThree;

    @Autowired
    public void setServiceThree(ServiceThree serviceThree) {
        this.serviceThree = serviceThree;
    }

    @GetMapping("/report")
    public String getReport() throws InterruptedException, ParseException {
        Unloading unloading = new Unloading(serviceThree.getList());
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
        return finalOutput.toString();
    }
}
