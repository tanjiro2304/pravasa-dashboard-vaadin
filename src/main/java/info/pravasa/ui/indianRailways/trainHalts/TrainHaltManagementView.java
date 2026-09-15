package info.pravasa.ui.indianRailways.trainHalts;

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
import info.pravasa.dto.IrHaltDto;
import info.pravasa.ui.indianRailways.trainHalts.components.AddEditTrainHaltDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/train-halts")
@Menu(title = "Train Halts", icon = "vaadin:clock")
public class TrainHaltManagementView extends VerticalLayout {

    private final TrainHaltManagementPresenter trainHaltManagementPresenter;

    private HorizontalLayout btnLayout;

    private Grid<IrHaltDto> irHaltDtoGrid;

    private final List<IrHaltDto> halts;

    public TrainHaltManagementView(TrainHaltManagementPresenter trainHaltManagementPresenter) {
        this.trainHaltManagementPresenter = trainHaltManagementPresenter;
        this.halts = new ArrayList<>();
        initializeBtnLayout();
        initializeHaltGrid();
        setSizeFull();
        add(btnLayout, irHaltDtoGrid);
        loadHalts();
    }

    private void loadHalts() {
        halts.clear();
        halts.addAll(trainHaltManagementPresenter.fetchAllHalts());
        irHaltDtoGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addHalt = new Button("Add Train Halt", event -> {
            AddEditTrainHaltDialog dialog = new AddEditTrainHaltDialog(new IrHaltDto(), dto -> {
                IrHaltDto saved = trainHaltManagementPresenter.save(dto);
                upsertHalt(saved);
            }, trainHaltManagementPresenter.fetchAllTrains(), trainHaltManagementPresenter.fetchAllStations());
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addHalt);
    }

    private void initializeHaltGrid() {
        irHaltDtoGrid = new Grid<>();
        irHaltDtoGrid.addColumn(IrHaltDto::getPlatformNo).setHeader("Platform No");
        irHaltDtoGrid.addColumn(IrHaltDto::getArrivalTime).setHeader("Arrival Time");
        irHaltDtoGrid.addColumn(IrHaltDto::getDepartureTime).setHeader("Departure Time");
        irHaltDtoGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddEditTrainHaltDialog dialog = new AddEditTrainHaltDialog(clickedItem, dto -> {
                IrHaltDto saved = trainHaltManagementPresenter.save(dto);
                upsertHalt(saved);
            }, trainHaltManagementPresenter.fetchAllTrains(), trainHaltManagementPresenter.fetchAllStations());
            dialog.open();
        }));
        irHaltDtoGrid.addColumn(new NativeButtonRenderer<>("Delete", clickedItem -> {
            trainHaltManagementPresenter.delete(clickedItem.getId());
            halts.removeIf(halt -> Objects.equals(halt.getId(), clickedItem.getId()));
            irHaltDtoGrid.getDataProvider().refreshAll();
            Notification.show("Train halt deleted", 3000, Notification.Position.TOP_CENTER);
        }));
        irHaltDtoGrid.setItems(halts);
        irHaltDtoGrid.setHeightFull();
    }

    private void upsertHalt(IrHaltDto irHaltDto) {
        halts.removeIf(halt -> Objects.equals(halt.getId(), irHaltDto.getId()));
        halts.add(irHaltDto);
        irHaltDtoGrid.getDataProvider().refreshAll();
    }
}
