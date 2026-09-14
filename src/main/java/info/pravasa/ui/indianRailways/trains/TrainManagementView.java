package info.pravasa.ui.indianRailways.trains;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.TrainDto;

@UIScope
@SpringComponent
@Route("/trains")
@Menu(title = "Trains", icon = "vaadin:train")
public class TrainManagementView extends VerticalLayout {

    private final TrainManagementPresenter trainManagementPresenter;

    private Grid<TrainDto> trainDtoGrid;

    public TrainManagementView(TrainManagementPresenter trainManagementPresenter) {
        this.trainManagementPresenter = trainManagementPresenter;
        initializeTrainGrid();
        setSizeFull();
        add(trainDtoGrid);
    }

    private void initializeTrainGrid() {
        trainDtoGrid = new Grid<>();
        trainDtoGrid.addColumn(TrainDto::getTrainServiceNo).setHeader("Train Service No");
        trainDtoGrid.addColumn(TrainDto::getTrainServiceName).setHeader("Train Service Name");
        trainDtoGrid.addColumn(TrainDto::getTrainServiceType).setHeader("Train Service Type");
        trainDtoGrid.setHeightFull();
    }
}
