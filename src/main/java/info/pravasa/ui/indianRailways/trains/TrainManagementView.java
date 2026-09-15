package info.pravasa.ui.indianRailways.trains;

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
import info.pravasa.dto.TrainDto;
import info.pravasa.ui.indianRailways.trains.components.AddEditTrainDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/trains")
@Menu(title = "Trains", icon = "vaadin:train")
public class TrainManagementView extends VerticalLayout {

    private final TrainManagementPresenter trainManagementPresenter;

    private HorizontalLayout btnLayout;

    private Grid<TrainDto> trainDtoGrid;

    private final List<TrainDto> trains;

    public TrainManagementView(TrainManagementPresenter trainManagementPresenter) {
        this.trainManagementPresenter = trainManagementPresenter;
        this.trains = new ArrayList<>();
        initializeBtnLayout();
        initializeTrainGrid();
        setSizeFull();
        add(btnLayout, trainDtoGrid);
        loadTrains();
    }

    private void loadTrains() {
        trains.clear();
        trains.addAll(trainManagementPresenter.fetchAllTrains());
        trainDtoGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addTrain = new Button("Add Train", event -> {
            AddEditTrainDialog dialog = new AddEditTrainDialog(new TrainDto(), dto -> {
                TrainDto saved = trainManagementPresenter.save(dto);
                upsertTrain(saved);
            });
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addTrain);
    }

    private void initializeTrainGrid() {
        trainDtoGrid = new Grid<>();
        trainDtoGrid.addColumn(TrainDto::getTrainServiceNo).setHeader("Train Service No");
        trainDtoGrid.addColumn(TrainDto::getTrainServiceName).setHeader("Train Service Name");
        trainDtoGrid.addColumn(TrainDto::getTrainServiceType).setHeader("Train Service Type");
        trainDtoGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddEditTrainDialog dialog = new AddEditTrainDialog(clickedItem, dto -> {
                TrainDto saved = trainManagementPresenter.save(dto);
                upsertTrain(saved);
            });
            dialog.open();
        }));
        trainDtoGrid.addColumn(new NativeButtonRenderer<>("Delete", clickedItem -> {
            trainManagementPresenter.delete(clickedItem.getId());
            trains.removeIf(train -> Objects.equals(train.getId(), clickedItem.getId()));
            trainDtoGrid.getDataProvider().refreshAll();
            Notification.show("Train deleted", 3000, Notification.Position.TOP_CENTER);
        }));
        trainDtoGrid.setItems(trains);
        trainDtoGrid.setHeightFull();
    }

    private void upsertTrain(TrainDto trainDto) {
        trains.removeIf(train -> Objects.equals(train.getId(), trainDto.getId()));
        trains.add(trainDto);
        trainDtoGrid.getDataProvider().refreshAll();
    }
}
