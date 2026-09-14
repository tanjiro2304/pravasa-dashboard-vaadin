package info.pravasa.services.roadTransportServices;

import info.pravasa.dto.RouteDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RouteService {

    private RestTemplate  restTemplate;

    private final static String URL = "http://localhost:8081/route";

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

    public void save(RouteDto routeDto){
        String url = URL +"/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<RouteDto> entity = new HttpEntity<>(routeDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);

    }



}
