package info.pravasa.ui.depotManagement;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.services.CompanyService;
import info.pravasa.services.DepotService;
import info.pravasa.services.RouteService;

import java.util.List;

@UIScope
@SpringComponent
public class DepotManagementPresenter {

    private final RouteService routeService;

    private final CompanyService companyService;

    private final DepotService depotService;

    public DepotManagementPresenter(RouteService routeService, CompanyService companyService, DepotService depotService) {
        this.routeService = routeService;
        this.companyService = companyService;
        this.depotService = depotService;
    }

    public List<Company> fetchAllCompanies(){
        return companyService.fetchAllCompanies();
    }

    public List<DepotDto> fetchAllDepotByCompany(Long companyId){
        return depotService.fetchAllDepot(companyId);
    }


}
