package info.pravasa.services.commonServices;

import info.pravasa.dto.Company;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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
