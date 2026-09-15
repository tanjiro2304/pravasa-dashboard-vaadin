package info.pravasa.ui.indianRailways.zone;

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
import info.pravasa.dto.ZoneDto;
import info.pravasa.ui.indianRailways.zone.components.AddZoneDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/zone")
@Menu(title = "Zones", icon = "vaadin:map-marker")
public class ZoneManagementView extends VerticalLayout {

    private final ZoneManagementPresenter zoneManagementPresenter;

    private HorizontalLayout btnLayout;

    private Grid<ZoneDto> zoneDtoGrid;

    private final List<ZoneDto> zones;

    public ZoneManagementView(ZoneManagementPresenter zoneManagementPresenter) {
        this.zoneManagementPresenter = zoneManagementPresenter;
        this.zones = new ArrayList<>();
        initializeBtnLayout();
        initializeZoneGrid();
        setSizeFull();
        add(btnLayout, zoneDtoGrid);
        loadZones();
    }

    private void loadZones() {
        zones.clear();
        zones.addAll(zoneManagementPresenter.fetchAllZones());
        zoneDtoGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addZone = new Button("Add Zone", event -> {
            AddZoneDialog dialog = new AddZoneDialog(new ZoneDto(), dto -> {
                ZoneDto saved = zoneManagementPresenter.save(dto);
                upsertZone(saved);
            });
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addZone);
    }

    private void initializeZoneGrid() {
        zoneDtoGrid = new Grid<>();
        zoneDtoGrid.addColumn(ZoneDto::getZoneName).setHeader("Zone Name");
        zoneDtoGrid.addColumn(ZoneDto::getZoneCode).setHeader("Zone Code");
        zoneDtoGrid.addColumn(ZoneDto::getAddress).setHeader("Address");
        zoneDtoGrid.addColumn(ZoneDto::getContactNumber).setHeader("Contact Number");
        zoneDtoGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddZoneDialog dialog = new AddZoneDialog(clickedItem, dto -> {
                ZoneDto saved = zoneManagementPresenter.save(dto);
                upsertZone(saved);
            });
            dialog.open();
        }));
        zoneDtoGrid.addColumn(new NativeButtonRenderer<>("Delete", clickedItem -> {
            zoneManagementPresenter.delete(clickedItem.getZoneId());
            zones.removeIf(zone -> Objects.equals(zone.getZoneId(), clickedItem.getZoneId()));
            zoneDtoGrid.getDataProvider().refreshAll();
            Notification.show("Zone deleted", 3000, Notification.Position.TOP_CENTER);
        }));
        zoneDtoGrid.setItems(zones);
        zoneDtoGrid.setHeightFull();
    }

    private void upsertZone(ZoneDto zoneDto) {
        zones.removeIf(zone -> Objects.equals(zone.getZoneId(), zoneDto.getZoneId()));
        zones.add(zoneDto);
        zoneDtoGrid.getDataProvider().refreshAll();
    }
}
