package info.pravasa.ui.stops;

import com.vaadin.copilot.shaded.javaparser.quality.NotNull;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.StopDto;
import info.pravasa.services.CompanyService;

import java.util.List;

@SpringComponent
@UIScope
public class StopPresenter {

    private CompanyService companyService;

    public StopPresenter(CompanyService companyService) {
        this.companyService = companyService;
    }

    public List<Company> fetchAllStopsByCompany(@NotNull Long companyId){
        return this.companyService.fetchAllCompanies();
    }


}
