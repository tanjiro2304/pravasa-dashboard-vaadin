package info.pravasa.services;

import info.pravasa.dto.Company;
import info.pravasa.dto.RouteDto;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Objects;

@Service
public class CompanyService {

    @Resource
    private RestTemplate restTemplate;
    private static final String URL = "http://localhost:8081/company";
    public List<Company> fetchAllCompanies(){
        String url = URL +"/findAll";
        ResponseEntity<List<Company>> reponse = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<Company>>() {});
        return reponse.getBody();
    }

    public Company save(Company company) {
        String url = URL +"/save";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Company> entity = new HttpEntity<>(company, httpHeaders);
        return restTemplate.exchange(url, HttpMethod.POST, entity, Company.class).getBody();
    }

//    private Mono<Company> saveCompany(Company company){
//        if(Objects.nonNull(company)){
//
//        }
//    }
}
