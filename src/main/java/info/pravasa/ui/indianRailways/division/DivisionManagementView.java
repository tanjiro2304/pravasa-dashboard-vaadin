package info.pravasa.ui.indianRailways.division;

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
import info.pravasa.dto.DivisionDto;
import info.pravasa.ui.indianRailways.division.components.AddEditDivisionDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/division")
@Menu(title = "Divisions", icon = "vaadin:sitemap")
public class DivisionManagementView extends VerticalLayout {

    private final DivisionManagementPresenter divisionManagementPresenter;

    private HorizontalLayout btnLayout;

    private Grid<DivisionDto> divisionDtoGrid;

    private final List<DivisionDto> divisions;

    public DivisionManagementView(DivisionManagementPresenter divisionManagementPresenter) {
        this.divisionManagementPresenter = divisionManagementPresenter;
        this.divisions = new ArrayList<>();
        initializeBtnLayout();
        initializeDivisionGrid();
        setSizeFull();
        add(btnLayout, divisionDtoGrid);
        loadDivisions();
    }

    private void loadDivisions() {
        divisions.clear();
        divisions.addAll(divisionManagementPresenter.fetchAllDivisions());
        divisionDtoGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addDivision = new Button("Add Division", event -> {
            AddEditDivisionDialog dialog = new AddEditDivisionDialog(new DivisionDto(), dto -> {
                DivisionDto saved = divisionManagementPresenter.save(dto);
                upsertDivision(saved);
            }, divisionManagementPresenter.fetchAllZones());
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addDivision);
    }

    private void initializeDivisionGrid() {
        divisionDtoGrid = new Grid<>();
        divisionDtoGrid.addColumn(DivisionDto::getDivisionName).setHeader("Division Name");
        divisionDtoGrid.addColumn(DivisionDto::getDivisionCode).setHeader("Division Code");
        divisionDtoGrid.addColumn(DivisionDto::getAddress).setHeader("Address");
        divisionDtoGrid.addColumn(DivisionDto::getContactNumber).setHeader("Contact Number");
        divisionDtoGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddEditDivisionDialog dialog = new AddEditDivisionDialog(clickedItem, dto -> {
                DivisionDto saved = divisionManagementPresenter.save(dto);
                upsertDivision(saved);
            }, divisionManagementPresenter.fetchAllZones());
            dialog.open();
        }));
        divisionDtoGrid.addColumn(new NativeButtonRenderer<>("Delete", clickedItem -> {
            divisionManagementPresenter.delete(clickedItem.getId());
            divisions.removeIf(division -> Objects.equals(division.getId(), clickedItem.getId()));
            divisionDtoGrid.getDataProvider().refreshAll();
            Notification.show("Division deleted", 3000, Notification.Position.TOP_CENTER);
        }));
        divisionDtoGrid.setItems(divisions);
        divisionDtoGrid.setHeightFull();
    }

    private void upsertDivision(DivisionDto divisionDto) {
        divisions.removeIf(division -> Objects.equals(division.getId(), divisionDto.getId()));
        divisions.add(divisionDto);
        divisionDtoGrid.getDataProvider().refreshAll();
    }
}
