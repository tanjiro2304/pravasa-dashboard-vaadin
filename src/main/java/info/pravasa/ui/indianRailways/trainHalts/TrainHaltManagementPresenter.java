package info.pravasa.ui.indianRailways.trainHalts;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.IrHaltDto;
import info.pravasa.dto.StationDto;
import info.pravasa.dto.TrainDto;
import info.pravasa.services.indianRailServices.StationService;
import info.pravasa.services.indianRailServices.TrainHaltService;
import info.pravasa.services.indianRailServices.TrainService;

import java.util.List;

@UIScope
@SpringComponent
public class TrainHaltManagementPresenter {

    private final TrainHaltService trainHaltService;

    private final TrainService trainService;

    private final StationService stationService;

    public TrainHaltManagementPresenter(TrainHaltService trainHaltService, TrainService trainService, StationService stationService) {
        this.trainHaltService = trainHaltService;
        this.trainService = trainService;
        this.stationService = stationService;
    }

    public List<IrHaltDto> fetchAllHalts() {
        return trainHaltService.fetchAllHalts(null);
    }

    public List<TrainDto> fetchAllTrains() {
        return trainService.fetchAllTrains();
    }

    public List<StationDto> fetchAllStations() {
        return stationService.fetchAllStations(null);
    }

    public IrHaltDto save(IrHaltDto irHaltDto) {
        return trainHaltService.save(irHaltDto);
    }

    public void delete(Long id) {
        trainHaltService.delete(id);
    }
}
