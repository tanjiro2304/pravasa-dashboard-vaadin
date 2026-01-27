package info.pravasa.ui.depotManagement.component;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;

import java.awt.*;
import java.util.Objects;

public class AddDepotDialog extends Dialog {
    private Company selectedCompany;

    private TextField depotName;

    private TextArea depotAddress;

    private EmailField emailField;

    private NumberField latitude;

    private  NumberField longitude;
    
    private TextField staffCount;
    
    private TextField routeCount;

    private Binder<DepotDto> binder;

    private VerticalLayout mainLayout;

    private Button submit;

    public AddDepotDialog(Company selectedCompany) {
        this.selectedCompany = selectedCompany;
        initalizeFields();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initalizeFields() {
        depotName= new TextField("Depot Name");
        depotAddress = new TextArea("Address");
        emailField = new EmailField("Email");
        latitude = new NumberField("Latitude");
        longitude = new NumberField("Longitude");
        staffCount = new TextField("Staff Count");
        routeCount = new TextField("Fleet Count");
        submit = new Button("Submit");
        submit.addClickListener(event -> close());
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(depotName).bind(DepotDto::getDepotName, DepotDto::setDepotName);
        binder.forField(depotAddress).bind(DepotDto::getAddress, DepotDto::setAddress);
        binder.forField(routeCount).bind(dto -> Objects.nonNull(dto.getRouteCount()) ? dto.getRouteCount().toString() : "0",
                (dto, count) -> {
                    if(Objects.nonNull(count)){
                        dto.setRouteCount(Integer.valueOf(count));
                    }else{
                        dto.setRouteCount(0);
                    }
                });
    }

    private void initializeMainLayout(){
        mainLayout = new VerticalLayout(new HorizontalLayout(depotName, depotAddress),
                new HorizontalLayout(emailField, latitude),
                new HorizontalLayout(longitude, staffCount),
                new HorizontalLayout(routeCount, submit));
        mainLayout.setSizeFull();
        setHeight("45rem");
        setWidth("35rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
