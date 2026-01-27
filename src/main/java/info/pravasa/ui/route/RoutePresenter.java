package info.pravasa.ui.route;

import com.vaadin.copilot.shaded.javaparser.quality.NotNull;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;
import info.pravasa.services.CompanyService;
import info.pravasa.services.DepotService;
import info.pravasa.services.RouteService;
import jakarta.annotation.Resource;

import java.util.List;

@SpringComponent
@UIScope
public class RoutePresenter {

    @Resource
    private RouteService routeService;

    @Resource
    private CompanyService companyService;

    @Resource
    private DepotService depotService;


    public List<Company> fetchAllCompanies(){
        return companyService.fetchAllCompanies();
    }

    public List<DepotDto> fetchDepotsByCompany(@NotNull Long companyId){
        return depotService.fetchAllDepot(companyId);
    }

    public List<RouteDto> fetchRouteByDepot(@NotNull Long depotId){
        return routeService.fetchAllRoutes(depotId);
    }

    public void saveRoute(RouteDto routeDto){
        routeService.save(routeDto);
    }
}
