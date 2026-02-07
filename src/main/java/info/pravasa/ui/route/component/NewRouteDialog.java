package info.pravasa.ui.route.component;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;
import info.pravasa.dto.enums.RouteType;
import info.pravasa.dto.enums.ServiceType;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.function.Consumer;

@Slf4j
public class NewRouteDialog extends Dialog {

    private TextField source;
    private TextField destination;
    private TextField routeNo;
    private ComboBox<RouteType> routeTypes;
    private ComboBox<ServiceType> serviceTypeComboBox;
    private TextArea description;
    private Binder<RouteDto> binder;
    private Consumer<RouteDto> routeDtoSupplier;
    private DepotDto selectedDepotDto;
    private Button submit;

    public NewRouteDialog(DepotDto depotDto, Consumer<RouteDto> supplier) {
        this.selectedDepotDto = depotDto;
        this.routeDtoSupplier = supplier;
        initializeMainComponents();
        initializeBinder();
        initializeMainLayout();
    }

    private void initializeMainComponents(){
        source= new TextField("Source");
        destination = new TextField("Destination");
        routeNo = new TextField("Route No");
        routeTypes = new ComboBox<>("Route Type");
        serviceTypeComboBox = new ComboBox<>("Service Type");
        submit = new Button("Submit");
        description = new TextArea("Description");
        submit.addClickListener(event -> {
            RouteDto routeDto = new RouteDto();
            try {
                binder.writeBean(routeDto);
                routeDto.setDepotDto(selectedDepotDto);
                routeDto.setHalts(new ArrayList<>());
                routeDtoSupplier.accept(routeDto);
                Notification.show("Data Added Successfully", 5000, Notification.Position.TOP_CENTER);
                close();
            } catch (ValidationException e) {
                log.error("Exception occurred: {}", e.getMessage());
            }
        });
        routeTypes.setItems(RouteType.values());
        serviceTypeComboBox.setItems(ServiceType.values());

        routeTypes.setItemLabelGenerator(RouteType::getDisplayName);
        serviceTypeComboBox.setItemLabelGenerator(ServiceType::getDisplayName);

        VerticalLayout verticalLayout = new VerticalLayout(routeNo,source,destination,description,routeTypes,serviceTypeComboBox, submit);
        verticalLayout.setSizeFull();
        verticalLayout.setAlignItems(FlexComponent.Alignment.CENTER);
        add(verticalLayout);
    }
    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(routeNo).bind(RouteDto::getRouteNo,RouteDto::setRouteNo);
        binder.forField(source).bind(RouteDto::getSource, RouteDto::setSource);
        binder.forField(destination).bind(RouteDto::getDestination, RouteDto::setDestination);
        binder.forField(routeTypes).bind(RouteDto::getRouteType, RouteDto::setRouteType);
        binder.forField(serviceTypeComboBox).bind(RouteDto::getServiceType, RouteDto::setServiceType);
        binder.forField(description).bind(RouteDto::getRouteDescription, RouteDto::setRouteDescription);

    }

    private void initializeMainLayout() {
        setHeight("35rem");
        setWidth("15rem");

        setCloseOnOutsideClick(true);
        setDraggable(true);
        setCloseOnEsc(true);

    }
}
