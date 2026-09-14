package info.pravasa.ui.indianRailways.station;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.StationDto;

@UIScope
@SpringComponent
@Route("/station")
@Menu(title = "Stations", icon = "vaadin:building")
public class StationManagementView extends VerticalLayout {

    private final StationManagementPresenter stationManagementPresenter;

    private Grid<StationDto> stationDtoGrid;

    public StationManagementView(StationManagementPresenter stationManagementPresenter) {
        this.stationManagementPresenter = stationManagementPresenter;
        initializeStationGrid();
        setSizeFull();
        add(stationDtoGrid);
    }

    private void initializeStationGrid() {
        stationDtoGrid = new Grid<>();
        stationDtoGrid.addColumn(StationDto::getStationName).setHeader("Station Name");
        stationDtoGrid.addColumn(StationDto::getStationCode).setHeader("Station Code");
        stationDtoGrid.addColumn(StationDto::getPlatformCount).setHeader("Platform Count");
        stationDtoGrid.addColumn(StationDto::getFastHalt).setHeader("Fast Halt");
        stationDtoGrid.setHeightFull();
    }
}
