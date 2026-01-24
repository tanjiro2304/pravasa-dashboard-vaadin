package info.pravasa.services;

import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;
import info.pravasa.dto.filters.RouteFilter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.ls.LSInput;

import java.util.List;

@Service
public class RouteService {

    private RestTemplate  restTemplate;

    private final static String URL = "http://localhost:8080/route";

    public RouteService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<RouteDto> fetchAllRoutes(Long depotId){
        String url = URL +"/findAllRouteByDepot";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> entity = new HttpEntity<>(depotId, httpHeaders);
        ResponseEntity<List<RouteDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<RouteDto>>() {});
        return response.getBody();
    }
}
