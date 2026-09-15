package info.pravasa.ui.indianRailways.trains;

import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.TrainDto;
import info.pravasa.services.indianRailServices.TrainService;

import java.util.List;

@UIScope
@SpringComponent
public class TrainManagementPresenter {

    private final TrainService trainService;

    public TrainManagementPresenter(TrainService trainService) {
        this.trainService = trainService;
    }

    public List<TrainDto> fetchAllTrains() {
        return trainService.fetchAllTrains();
    }

    public TrainDto save(TrainDto trainDto) {
        return trainService.save(trainDto);
    }

    public void delete(Long id) {
        trainService.delete(id);
    }
}
