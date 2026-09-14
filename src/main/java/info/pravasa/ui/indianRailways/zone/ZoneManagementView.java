package info.pravasa.ui.indianRailways.zone;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.ZoneDto;

@UIScope
@SpringComponent
@Route("/zone")
@Menu(title = "Zones", icon = "vaadin:map-marker")
public class ZoneManagementView extends VerticalLayout {

    private final ZoneManagementPresenter zoneManagementPresenter;

    private Grid<ZoneDto> zoneDtoGrid;

    public ZoneManagementView(ZoneManagementPresenter zoneManagementPresenter) {
        this.zoneManagementPresenter = zoneManagementPresenter;
        initializeZoneGrid();
        setSizeFull();
        add(zoneDtoGrid);
    }

    private void initializeZoneGrid() {
        zoneDtoGrid = new Grid<>();
        zoneDtoGrid.addColumn(ZoneDto::getZoneName).setHeader("Zone Name");
        zoneDtoGrid.addColumn(ZoneDto::getZoneCode).setHeader("Zone Code");
        zoneDtoGrid.addColumn(ZoneDto::getAddress).setHeader("Address");
        zoneDtoGrid.addColumn(ZoneDto::getContactNumber).setHeader("Contact Number");
        zoneDtoGrid.setHeightFull();
    }
}
