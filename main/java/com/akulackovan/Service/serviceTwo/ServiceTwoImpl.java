package com.akulackovan.Service.serviceTwo;

import com.akulackovan.Service.entity.Ship;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.StringReader;
import java.util.List;

@Service
public class ServiceTwoImpl implements ServiceTwo {
    @Override
    public List<Ship> getShips () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceOne/generate";
        ResponseEntity<List<Ship>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ship>>(){});
        return responseEntity.getBody();
    }
}
