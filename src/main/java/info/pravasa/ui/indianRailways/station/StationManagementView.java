package info.pravasa.ui.indianRailways.station;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.StationDto;
import info.pravasa.ui.indianRailways.station.components.AddEditStationDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/station")
@Menu(title = "Stations", icon = "vaadin:building")
public class StationManagementView extends VerticalLayout {

    private final StationManagementPresenter stationManagementPresenter;

    private HorizontalLayout btnLayout;

    private Grid<StationDto> stationDtoGrid;

    private final List<StationDto> stations;

    public StationManagementView(StationManagementPresenter stationManagementPresenter) {
        this.stationManagementPresenter = stationManagementPresenter;
        this.stations = new ArrayList<>();
        initializeBtnLayout();
        initializeStationGrid();
        setSizeFull();
        add(btnLayout, stationDtoGrid);
        loadStations();
    }

    private void loadStations() {
        stations.clear();
        stations.addAll(stationManagementPresenter.fetchAllStations());
        stationDtoGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addStation = new Button("Add Station", event -> {
            AddEditStationDialog dialog = new AddEditStationDialog(new StationDto(), dto -> {
                StationDto saved = stationManagementPresenter.save(dto);
                upsertStation(saved);
            }, stationManagementPresenter.fetchAllDivisions());
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addStation);
    }

    private void initializeStationGrid() {
        stationDtoGrid = new Grid<>();
        stationDtoGrid.addColumn(StationDto::getStationName).setHeader("Station Name");
        stationDtoGrid.addColumn(StationDto::getStationCode).setHeader("Station Code");
        stationDtoGrid.addColumn(StationDto::getPlatformCount).setHeader("Platform Count");
        stationDtoGrid.addColumn(StationDto::getFastHalt).setHeader("Fast Halt");
        stationDtoGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddEditStationDialog dialog = new AddEditStationDialog(clickedItem, dto -> {
                StationDto saved = stationManagementPresenter.save(dto);
                upsertStation(saved);
            }, stationManagementPresenter.fetchAllDivisions());
            dialog.open();
        }));
        stationDtoGrid.addColumn(new NativeButtonRenderer<>("Delete", clickedItem -> {
            stationManagementPresenter.delete(clickedItem.getStationId());
            stations.removeIf(station -> Objects.equals(station.getStationId(), clickedItem.getStationId()));
            stationDtoGrid.getDataProvider().refreshAll();
            Notification.show("Station deleted", 3000, Notification.Position.TOP_CENTER);
        }));
        stationDtoGrid.setItems(stations);
        stationDtoGrid.setHeightFull();
    }

    private void upsertStation(StationDto stationDto) {
        stations.removeIf(station -> Objects.equals(station.getStationId(), stationDto.getStationId()));
        stations.add(stationDto);
        stationDtoGrid.getDataProvider().refreshAll();
    }
}
