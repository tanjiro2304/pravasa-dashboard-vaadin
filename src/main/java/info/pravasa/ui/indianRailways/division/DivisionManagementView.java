package info.pravasa.ui.indianRailways.division;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.DivisionDto;

@UIScope
@SpringComponent
@Route("/division")
@Menu(title = "Divisions", icon = "vaadin:sitemap")
public class DivisionManagementView extends VerticalLayout {

    private final DivisionManagementPresenter divisionManagementPresenter;

    private Grid<DivisionDto> divisionDtoGrid;

    public DivisionManagementView(DivisionManagementPresenter divisionManagementPresenter) {
        this.divisionManagementPresenter = divisionManagementPresenter;
        initializeDivisionGrid();
        setSizeFull();
        add(divisionDtoGrid);
    }

    private void initializeDivisionGrid() {
        divisionDtoGrid = new Grid<>();
        divisionDtoGrid.addColumn(DivisionDto::getDivisionName).setHeader("Division Name");
        divisionDtoGrid.addColumn(DivisionDto::getDivisionCode).setHeader("Division Code");
        divisionDtoGrid.addColumn(DivisionDto::getAddress).setHeader("Address");
        divisionDtoGrid.addColumn(DivisionDto::getContactNumber).setHeader("Contact Number");
        divisionDtoGrid.setHeightFull();
    }
}
