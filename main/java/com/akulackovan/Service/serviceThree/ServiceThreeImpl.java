package com.akulackovan.Service.serviceThree;

import com.akulackovan.Service.entity.Ship;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.FileReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceThreeImpl implements ServiceThree {
    @Override
    public List<Ship> getList () throws ParseException {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceTwo/get/arrivalOfShips";
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(responseEntity.getBody());
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
        System.out.println("Service 1 was read");
        return ships;
    }
}
