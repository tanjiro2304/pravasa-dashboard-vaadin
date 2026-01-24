package info.pravasa.ui.route.component;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import info.pravasa.dto.HaltDto;
import info.pravasa.dto.RouteDto;

public class RouteDetailDialog extends Dialog {

    private VerticalLayout mainLayout;

    private Grid<HaltDto> haltDtoGrid;

    private RouteDto selectedRoute;

    public RouteDetailDialog(RouteDto selectedRoute) {
        this.selectedRoute = selectedRoute;
        this.mainLayout = new VerticalLayout();
        initializeGrid();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeMainLayout(){
        setCloseOnOutsideClick(true);
        setDraggable(true);
        setCloseOnOutsideClick(true);
        setHeight("35rem");
        setWidth("40rem");
        mainLayout.setSizeFull();
    }

    private void initializeGrid() {
        haltDtoGrid = new Grid<>();
        haltDtoGrid.addColumn(HaltDto::getHaltPosition).setHeader("Sr No").setWidth("10rem");
        haltDtoGrid.addColumn(HaltDto::getStopName).setHeader("Stop").setWidth("20rem");
        haltDtoGrid.setHeightFull();
        haltDtoGrid.setItems(selectedRoute.getHalts());
        mainLayout.add(haltDtoGrid);
    }


}
