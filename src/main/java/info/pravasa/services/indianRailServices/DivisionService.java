package info.pravasa.services.indianRailServices;

import info.pravasa.dto.DivisionDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class DivisionService {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/api/indian-railway/division";

    public DivisionService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<DivisionDto> fetchAllDivisions(Long zoneId) {
        String url = URL + "/findAll";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> entity = new HttpEntity<>(zoneId, httpHeaders);
        ResponseEntity<List<DivisionDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<DivisionDto>>() {});
        return response.getBody();
    }

    public void save(DivisionDto divisionDto) {
        String url = URL + "/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<DivisionDto> entity = new HttpEntity<>(divisionDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
}
