package com.akulackovan.Service.serviceThree;

import com.akulackovan.Service.entity.Ship;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.List;

@Service
public class ServiceThreeImpl implements ServiceThree {
    @Override
    public List<Ship> getList () {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8083/serviceTwo/get";
        ResponseEntity<List<Ship>> responseEntity = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<Ship>>(){});
        return responseEntity.getBody();
    }
}
