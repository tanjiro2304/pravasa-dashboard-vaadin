package info.pravasa.services.indianRailServices;

import info.pravasa.dto.IrHaltDto;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TrainHaltService {

    private final RestTemplate restTemplate;

    private static final String URL = "http://localhost:8081/api/indian-railway/ir-halt";

    public TrainHaltService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<IrHaltDto> fetchAllHalts(Long trainId) {
        String url = URL + "/findAll";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Long> entity = new HttpEntity<>(trainId, httpHeaders);
        ResponseEntity<List<IrHaltDto>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<List<IrHaltDto>>() {});
        return response.getBody();
    }

    public IrHaltDto save(IrHaltDto irHaltDto) {
        String url = URL + "/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<IrHaltDto> entity = new HttpEntity<>(irHaltDto, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, IrHaltDto.class).getBody();
    }

    public void delete(Long id) {
        String url = URL + "/" + id;
        restTemplate.delete(url);
    }
}
