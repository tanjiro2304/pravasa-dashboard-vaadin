package info.pravasa.services.indianRailServices;

import info.pravasa.dto.TrainDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrainService {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/api/indian-railway/trains";

    public TrainService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<TrainDto> fetchAllTrains() {
        String url = URL + "/findAll";
        ResponseEntity<List<TrainDto>> response = restTemplate.exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<List<TrainDto>>() {});
        return response.getBody();
    }

    public void save(TrainDto trainDto) {
        String url = URL + "/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<TrainDto> entity = new HttpEntity<>(trainDto, httpHeaders);
        restTemplate.exchange(url, HttpMethod.POST, entity, Void.class);
    }
}
