package info.pravasa.ui.depotManagement.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
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

    private NumberField fleetCount;
    private NumberField electricFleet;
    private NumberField cngFleet;
    private NumberField dieselFleet;

    private Binder<DepotDto> binder;

    private VerticalLayout mainLayout;

    private Button submit;

    private Consumer<DepotDto> depotConsumer;

    private DepotDto depotDto;

    private Checkbox electricChargingStation;

    private Checkbox cngStation;

    private Checkbox dieselStation;

    private Grid<FleetInformationDto> fleetInformationDtoGrid;

    private VerticalLayout fleetInfoLayout;
    public AddDepotDialog(Company selectedCompany, DepotDto depotDto, Consumer<DepotDto> depotConsumer) {
        this.selectedCompany = selectedCompany;
        this.depotDto = depotDto;
        this.depotConsumer = depotConsumer;
        initializeFields();
        initializeFleetLayout();
        initializeBinder();
        initializeMainLayout();

        add(mainLayout, fleetInfoLayout);
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
        routeCount = new NumberField("Fleet Count");
        fleetCount = new NumberField("Fleet Count");

        electricFleet = new NumberField("Electric Fleet");
        cngFleet = new NumberField("CNG Fleet");
        dieselFleet = new NumberField("Diesel Fleet");

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

    private void initializeFleetLayout(){
        fleetInformationDtoGrid = new Grid<>();
        fleetInformationDtoGrid.addColumn(FleetInformationDto::getContractorName).setHeader("Fleet Type");
        fleetInformationDtoGrid.addColumn(FleetInformationDto::getCngFleet).setHeader("Fleet Count");
        fleetInformationDtoGrid.addColumn(FleetInformationDto::getElectricFleet).setHeader("Fleet Count");
        fleetInformationDtoGrid.addColumn(FleetInformationDto::getDieselFleet).setHeader("Fleet Count");

        fleetInfoLayout = new VerticalLayout(new HorizontalLayout(electricFleet, cngFleet, dieselFleet),
                new HorizontalLayout(electricChargingStation, cngStation, dieselStation), fleetInformationDtoGrid);
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
        setWidth("50rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
