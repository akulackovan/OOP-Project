package com.akulackovan.ServiceThree.serviceThree;

import com.akulackovan.ServiceThree.entity.Ship;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceThreeImpl implements ServiceThree {
    @Override
    public List<Ship> getList ()   {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8082/serviceTwo/get/arrivalOfShips";
        String responseEntity = restTemplate.getForObject(url, String.class);
        JSONParser jsonParser = new JSONParser();
        String str = responseEntity;
        try {
            JSONObject jsonObject = (JSONObject) jsonParser.parse(str);
            JSONArray jsonArray = (JSONArray) jsonObject.get("Ships");
            List<Ship> ships = new ArrayList<>();
            for (Object o : jsonArray) {
                JSONObject ship = (JSONObject) o;
                String name = (String) ship.get("Name ship");
                String time = (String) ship.get("Arrival date");
                Timestamp arrivalDate = Timestamp.valueOf(time);
                String type = (String) ship.get("Type of cargo");
                int number = Integer.parseInt(String.valueOf(ship.get("Number of cargo")));
                ships.add(new Ship(name, arrivalDate, new Ship.Cargo(type, number)));
            }
            return ships;
        }
        catch(Exception e) {
            System.out.println("Parse error" + e);
        }
        return null;
    }
}
