package info.pravasa.services;

import info.pravasa.dto.Company;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.List;

@Service
public class CompanyService {

    @Resource
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8080/company";
    public List<Company> fetchAllCompanies(){
        String url = URL +"/findAll";
        ResponseEntity<List<Company>> reponse = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<Company>>() {});
        return reponse.getBody();
    }
}
