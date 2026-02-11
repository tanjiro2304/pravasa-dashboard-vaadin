package info.pravasa.ui.depotManagement.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.editor.Editor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.FleetInformationDto;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class AddDepotDialog extends Dialog {
    private Company selectedCompany;

    private TextField depotName;

    private TextArea depotAddress;

    private EmailField emailField;

    private NumberField latitude;

    private  NumberField longitude;
    
    private TextField staffCount;
    
    private NumberField routeCount;

    private TextField depotCode;

    private TextField contactInfo;

    private Binder<DepotDto> binder;

    private VerticalLayout mainLayout;

    private Button submit;

    private Consumer<DepotDto> depotConsumer;

    private DepotDto depotDto;

    private Checkbox electricChargingStation;

    private Checkbox cngStation;

    private Checkbox dieselStation;

    private Button addFleetInfoButton;

    private List<FleetInformationDto> fleetInformationDtoList = new ArrayList<>();

    private Grid<FleetInformationDto> fleetInformationDtoGrid;

    private Map<Long, Company> companyMap;

    private VerticalLayout fleetInfoLayout;
    public AddDepotDialog(Company selectedCompany, DepotDto depotDto, Consumer<DepotDto> depotConsumer, Map<Long, Company> companyMap) {
        this.selectedCompany = selectedCompany;
        this.companyMap = companyMap;
        this.depotDto = depotDto;
        this.depotConsumer = depotConsumer;
        initializeFields();
        initializeFleetLayout();
        initializeBinder();
        initializeFleetBinder();
        initializeMainLayout();
        HorizontalLayout mainHorizontalLayout = new HorizontalLayout(mainLayout, fleetInfoLayout);
        mainHorizontalLayout.setSizeFull();
        add(mainHorizontalLayout);
    }

    private void initializeFields() {
        depotName= new TextField("Depot Name");
        depotAddress = new TextArea("Address");
        depotCode = new TextField("Depot Code");
        emailField = new EmailField("Email");
        contactInfo = new TextField("Contact Info");
        latitude = new NumberField("Latitude");
        longitude = new NumberField("Longitude");
        staffCount = new TextField("Staff Count");
        routeCount = new NumberField("Number of Routes");
        cngStation = new Checkbox("CNG Station");
        electricChargingStation = new Checkbox("Electric Charging Station");
        dieselStation = new Checkbox("Diesel Station");

        submit = new Button("Submit");
        submit.addClickListener(event -> {

            try {
                binder.writeBean(depotDto);
                depotConsumer.accept(depotDto);
            } catch (ValidationException e) {
                log.error("Error while writing bean:", e.getCause());
            }

            close();
        });
    }

    private void initializeFleetBinder(){

    }

    private void initializeFleetLayout(){
        fleetInformationDtoGrid = new Grid<>();
        Grid.Column<FleetInformationDto> fleetInformationDtoColumn = fleetInformationDtoGrid.addColumn(FleetInformationDto::getContractorName);
        Grid.Column<FleetInformationDto> cngFleet = fleetInformationDtoGrid.addColumn(FleetInformationDto::getCngFleet).setHeader("CNG Fleet");
        Grid.Column<FleetInformationDto> electricFleet = fleetInformationDtoGrid.addColumn(FleetInformationDto::getElectricFleet).setHeader("Electric Fleet");
        Grid.Column<FleetInformationDto> dieselFleet = fleetInformationDtoGrid.addColumn(FleetInformationDto::getDieselFleet).setHeader("Diesel Fleet");

        fleetInformationDtoGrid.setItems(fleetInformationDtoList);
        addFleetInfoButton = new Button("Add Fleet Info", event -> {
            FleetInformationDto fleetInformationDto = FleetInformationDto.builder()
                    .build();
               fleetInformationDtoList.add(fleetInformationDto);
               fleetInformationDtoGrid.getDataProvider().refreshAll();
        });


        Editor<FleetInformationDto> editor = fleetInformationDtoGrid.getEditor();
        fleetInformationDtoGrid.addItemClickListener(item -> {
            if(!editor.isOpen()){
                editor.editItem(item.getItem());
            }else{
                editor.save();
                editor.editItem(item.getItem());
            }
        });
        ComboBox<Company> companyComboBox = new ComboBox<>();
        companyComboBox.setItemLabelGenerator(Company::getCompanyName);
        companyComboBox.setItems(companyMap.values());
        NumberField cngFleetField = new NumberField();
        NumberField electricFleetField = new NumberField();
        NumberField dieselFleetField = new NumberField();
        Binder<FleetInformationDto>  fleetBinder = new Binder<>();

        fleetInformationDtoColumn.setEditorComponent(companyComboBox);
        cngFleet.setEditorComponent(cngFleetField);
        electricFleet.setEditorComponent(electricFleetField);
        dieselFleet.setEditorComponent(dieselFleetField);

        fleetBinder.forField(companyComboBox).bind(company -> {
            if(Objects.nonNull(company.getContractorId())){
                return companyMap.get(company.getContractorId());
            }
            return null;
        }, (fleetInformationDto, company) -> {
            if(Objects.nonNull(company)){
                fleetInformationDto.setContractorId(company.getId());
                fleetInformationDto.setContractorName(company.getCompanyName());
            }else{
                fleetInformationDto.setContractorId(null);
            }
        });


        fleetBinder.forField(cngFleetField).bind(dto -> (double) (Objects.nonNull(dto.getCngFleet()) ? dto.getCngFleet() :0),
                 (dto, cng) -> {
                     if(Objects.nonNull(cng)){
                         dto.setCngFleet(cng.intValue());
                     }else{
                         dto.setCngFleet(0);
                     }
                 });

        fleetBinder.forField(dieselFleetField).bind(dto -> (double) (Objects.nonNull(dto.getDieselFleet()) ? dto.getDieselFleet() :0),
                (dto, count) -> {
                    if(Objects.nonNull(count)){
                        dto.setDieselFleet(count.intValue());
                    }else{
                        dto.setCngFleet(0);
                    }
                });

        fleetBinder.forField(electricFleetField).bind(dto -> (double) (Objects.nonNull(dto.getElectricFleet()) ? dto.getElectricFleet() :0),
                (dto, count) -> {
                    if(Objects.nonNull(count)){
                        dto.setElectricFleet(count.intValue());
                    }else{
                        dto.setElectricFleet(0);
                    }
                });
        editor.setBinder(fleetBinder);

        fleetInfoLayout = new VerticalLayout(
                new HorizontalLayout(electricChargingStation, cngStation, dieselStation), addFleetInfoButton, fleetInformationDtoGrid);
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(depotName).bind(DepotDto::getDepotName, DepotDto::setDepotName);
        binder.forField(depotAddress).bind(DepotDto::getAddress, DepotDto::setAddress);
        binder.forField(depotCode).bind(DepotDto::getDepotCode, DepotDto::setDepotCode);
        binder.forField(latitude).bind(DepotDto::getLatitude, DepotDto::setLatitude);
        binder.forField(longitude).bind(DepotDto::getLongitude, DepotDto::setLongitude);
        binder.forField(contactInfo).bind(DepotDto::getContact, DepotDto::setContact);
        binder.forField(emailField).bind(DepotDto::getEmail, DepotDto::setEmail);
        binder.forField(staffCount).bind(dto -> Objects.nonNull(dto.getStaffCount()) ? dto.getStaffCount().toString() : "0",
                (dto, count) -> {
                    if(Objects.nonNull(count)){
                        dto.setStaffCount(Integer.valueOf(count));
                    }else{
                        dto.setStaffCount(0);
                    }
                });
        binder.forField(routeCount).bind(dto -> Objects.nonNull(dto.getRouteCount()) ?
                dto.getRouteCount() : 0d,
                (dto, count) -> {
                    if(Objects.nonNull(count)){
                        dto.setRouteCount(count.intValue());
                    }else{
                        dto.setRouteCount(0);
                    }
                });


    }

    private void initializeMainLayout(){
        mainLayout = new VerticalLayout(new HorizontalLayout(depotName, depotCode),
                new HorizontalLayout(longitude, latitude),
                new HorizontalLayout(emailField, staffCount),
                new HorizontalLayout(contactInfo,routeCount ),
                new HorizontalLayout(depotAddress), submit);
        mainLayout.setSizeFull();
        setHeight("35rem");
        setWidth("70rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
