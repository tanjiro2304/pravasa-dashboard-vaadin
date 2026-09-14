package info.pravasa.ui.indianRailways.trainHalts;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.IrHaltDto;

@UIScope
@SpringComponent
@Route("/train-halts")
@Menu(title = "Train Halts", icon = "vaadin:clock")
public class TrainHaltManagementView extends VerticalLayout {

    private final TrainHaltManagementPresenter trainHaltManagementPresenter;

    private Grid<IrHaltDto> irHaltDtoGrid;

    public TrainHaltManagementView(TrainHaltManagementPresenter trainHaltManagementPresenter) {
        this.trainHaltManagementPresenter = trainHaltManagementPresenter;
        initializeHaltGrid();
        setSizeFull();
        add(irHaltDtoGrid);
    }

    private void initializeHaltGrid() {
        irHaltDtoGrid = new Grid<>();
        irHaltDtoGrid.addColumn(IrHaltDto::getPlatformNo).setHeader("Platform No");
        irHaltDtoGrid.addColumn(IrHaltDto::getArrivalTime).setHeader("Arrival Time");
        irHaltDtoGrid.addColumn(IrHaltDto::getDepartureTime).setHeader("Departure Time");
        irHaltDtoGrid.setHeightFull();
    }
}
