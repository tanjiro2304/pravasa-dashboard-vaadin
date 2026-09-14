package info.pravasa.services.indianRailServices;

import info.pravasa.dto.ZoneDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ZoneService {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/api/indian-railway/zone";

    public ZoneService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<ZoneDto> fetchAllZones() {
        String url = URL + "/findAll";
        ResponseEntity<List<ZoneDto>> response = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<ZoneDto>>() {});
        return response.getBody();
    }

    public void save(ZoneDto zoneDto) {
        String url = URL + "/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ZoneDto> entity = new HttpEntity<>(zoneDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
}
