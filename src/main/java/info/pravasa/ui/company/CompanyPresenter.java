package info.pravasa.ui.company;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import info.pravasa.services.CityService;
import info.pravasa.services.CompanyService;

import java.util.List;

@UIScope
@SpringComponent
public class CompanyPresenter {


    private final CompanyService companyService;
    private final CityService cityService;

    public CompanyPresenter(CompanyService companyService, CityService cityService) {
        this.companyService = companyService;
        this.cityService = cityService;
    }

    public List<Company> fetchAllCompanies(){
        return this.companyService.fetchAllCompanies();
    }

    public void saveCompany(Company company){
        this.companyService.save(company);
    }

    public List<City> fetchAllCities() {
        return cityService.fetchAllCompanies();
    }
}
