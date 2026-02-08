package info.pravasa.services;


import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class CityService {
    @Resource
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8081/city";
    public List<City> fetchAllCompanies(){
        String url = URL +"/findAll";
        ResponseEntity<List<City>> reponse = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<City>>() {});
        return reponse.getBody();
    }
}
