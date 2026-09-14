package info.pravasa.ui.roadTransport.depotManagement;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.services.commonServices.CompanyService;
import info.pravasa.services.roadTransportServices.DepotService;
import info.pravasa.services.roadTransportServices.RouteService;

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


    public void save(DepotDto dto) {
        depotService.save(dto);
    }
}
