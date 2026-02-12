package info.pravasa.services;

import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DepotService {
    private RestTemplate restTemplate;

    private final static String URL = "http://localhost:8081/depot";
    public DepotService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<DepotDto> fetchAllDepot(Long companyId){
        String url = URL +"/findAll";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Long> entity = new HttpEntity<>(companyId, httpHeaders);
        ResponseEntity<List<DepotDto>> reponse = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<DepotDto>>() {});
        return reponse.getBody();
    }

    public void save(DepotDto depotDto){
        String url = URL +"/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DepotDto> entity = new HttpEntity<>(depotDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

    }
}
