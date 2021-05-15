package com.akulackovan.ServiceOne.serviceOne;

import com.akulackovan.ServiceOne.entity.Ship;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/serviceOne")
public class ServiceOneController {

    private ServiceOne serviceOne;

    @Autowired
    public void setServiceOne(ServiceOne serviceOne) {
        this.serviceOne = serviceOne;
    }

    @GetMapping("/generate")
    public String list() {
        List<Ship> ships = serviceOne.getList();
        JSONArray shipJSON = new JSONArray();
        for (Ship ship : ships) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Name ship", ship.getName_());
            jsonObject.put("Arrival date", ship.getTimeBegin_().toString());
            jsonObject.put("Departure date", ship.getTimeEnd_().toString());
            jsonObject.put("Type of cargo", ship.getNameType());
            jsonObject.put("Number of cargo", ship.getNumberOfCargo());
            shipJSON.add(jsonObject);
        }
        JSONObject finalOutput = new JSONObject();
        finalOutput.put("Ships", shipJSON);
        return finalOutput.toString();
    }
}
