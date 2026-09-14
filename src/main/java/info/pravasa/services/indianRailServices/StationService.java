package info.pravasa.services.indianRailServices;

import info.pravasa.dto.StationDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class StationService {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/api/indian-railway/station";

    public StationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<StationDto> fetchAllStations(Long divisionId) {
        String url = URL + "/findAll";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> entity = new HttpEntity<>(divisionId, httpHeaders);
        ResponseEntity<List<StationDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<StationDto>>() {});
        return response.getBody();
    }

    public void save(StationDto stationDto) {
        String url = URL + "/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<StationDto> entity = new HttpEntity<>(stationDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
}
