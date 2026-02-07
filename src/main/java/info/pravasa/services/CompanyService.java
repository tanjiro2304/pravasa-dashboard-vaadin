package info.pravasa.services;

import info.pravasa.dto.Company;
import jakarta.annotation.Resource;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
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
    private static final String URL = "http://localhost:8080/company";
    public List<Company> fetchAllCompanies(){
        String url = URL +"/findAll";
        ResponseEntity<List<Company>> reponse = restTemplate.exchange(url, HttpMethod.POST, null, new ParameterizedTypeReference<List<Company>>() {});
        return reponse.getBody();
    }

//    private Mono<Company> saveCompany(Company company){
//        if(Objects.nonNull(company)){
//
//        }
//    }
}
