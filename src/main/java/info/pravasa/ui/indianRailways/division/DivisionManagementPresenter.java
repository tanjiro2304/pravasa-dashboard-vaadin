package info.pravasa.ui.indianRailways.division;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.DivisionDto;
import info.pravasa.dto.ZoneDto;
import info.pravasa.services.indianRailServices.DivisionService;
import info.pravasa.services.indianRailServices.ZoneService;

import java.util.List;

@UIScope
@SpringComponent
public class DivisionManagementPresenter {

    private final DivisionService divisionService;

    private final ZoneService zoneService;

    public DivisionManagementPresenter(DivisionService divisionService, ZoneService zoneService) {
        this.divisionService = divisionService;
        this.zoneService = zoneService;
    }

    public List<DivisionDto> fetchAllDivisions() {
        return divisionService.fetchAllDivisions(null);
    }

    public List<ZoneDto> fetchAllZones() {
        return zoneService.fetchAllZones();
    }

    public DivisionDto save(DivisionDto divisionDto) {
        return divisionService.save(divisionDto);
    }

    public void delete(Long id) {
        divisionService.delete(id);
    }
}
