package info.pravasa.ui.indianRailways.station;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.DivisionDto;
import info.pravasa.dto.StationDto;
import info.pravasa.services.indianRailServices.DivisionService;
import info.pravasa.services.indianRailServices.StationService;

import java.util.List;

@UIScope
@SpringComponent
public class StationManagementPresenter {

    private final StationService stationService;

    private final DivisionService divisionService;

    public StationManagementPresenter(StationService stationService, DivisionService divisionService) {
        this.stationService = stationService;
        this.divisionService = divisionService;
    }

    public List<StationDto> fetchAllStations() {
        return stationService.fetchAllStations(null);
    }

    public List<DivisionDto> fetchAllDivisions() {
        return divisionService.fetchAllDivisions(null);
    }

    public StationDto save(StationDto stationDto) {
        return stationService.save(stationDto);
    }

    public void delete(Long id) {
        stationService.delete(id);
    }
}
