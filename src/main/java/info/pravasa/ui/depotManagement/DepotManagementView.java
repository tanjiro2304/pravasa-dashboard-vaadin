package info.pravasa.ui.depotManagement;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.ui.depotManagement.component.AddDepotDialog;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
@Route("/depot")
@Menu(title="Depot", icon="vaadin:building")
public class DepotManagementView extends VerticalLayout {
    private ComboBox<Company> companyComboBox;

    private Button addDepot;

    private DepotManagementPresenter depotManagementPresenter;

    private Grid<DepotDto> depotDtoGrid;

    public DepotManagementView(DepotManagementPresenter depotManagementPresenter) {
        this.depotManagementPresenter = depotManagementPresenter;
        initializeMainFields();
        initializeDepotGrid();
        setSizeFull();
        add(new HorizontalLayout(companyComboBox, addDepot), depotDtoGrid);
    }

    private void initializeMainFields() {
        companyComboBox = new ComboBox<>("Company");
        companyComboBox.setItemLabelGenerator(Company::getCompanyName);
        companyComboBox.setItems(depotManagementPresenter.fetchAllCompanies().stream().filter(dto -> dto.getCompanyType().getId() != 1).toList());
        companyComboBox.addValueChangeListener(event -> {
            if(Objects.nonNull(event.getValue())){
                List<DepotDto> dtoList =depotManagementPresenter.fetchAllDepotByCompany(event.getValue().getId());
                depotDtoGrid.setItems(dtoList);
            }
        });

        addDepot = new Button("Add Depot");
        addDepot.addClickListener(event -> {
            if(Objects.nonNull(companyComboBox.getValue())){
                AddDepotDialog addDepotDialog = new AddDepotDialog(companyComboBox.getValue(), new DepotDto(),
                        dto -> depotManagementPresenter.save(dto), depotManagementPresenter.fetchAllCompanies().stream().collect(Collectors.toMap(Company::getId, company -> company)));
                addDepotDialog.open();
            }else{
                Notification.show("Select a Company", 3000, Notification.Position.TOP_CENTER);
            }

        });
        addDepot.getElement().getStyle().set("margin-top","2.2rem");
    }

    private void initializeDepotGrid(){
        depotDtoGrid = new Grid<>();
        depotDtoGrid.addColumn(DepotDto::getDepotName).setHeader("Depot Name");
        depotDtoGrid.addColumn(DepotDto::getDepotCode).setHeader("Depot Code");
        depotDtoGrid.addColumn(DepotDto::getAddress).setHeader("Address");
        depotDtoGrid.addColumn(DepotDto::getContact).setHeader("Contact");
        depotDtoGrid.addColumn(DepotDto::getEmail).setHeader("Email");
        depotDtoGrid.setHeightFull();
    }
}
