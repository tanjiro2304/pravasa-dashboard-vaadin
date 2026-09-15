package info.pravasa.ui.indianRailways.zone.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.ZoneDto;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Consumer;

@Slf4j
public class AddZoneDialog extends Dialog {

    private TextField zoneName;
    private TextField zoneCode;
    private TextField address;
    private TextField contactNumber;
    private VerticalLayout mainLayout;
    private Binder<ZoneDto> binder;
    private Button submit;

    private final ZoneDto zoneDto;
    private final Consumer<ZoneDto> zoneConsumer;

    public AddZoneDialog(ZoneDto zoneDto, Consumer<ZoneDto> zoneConsumer) {
        this.zoneDto = zoneDto;
        this.zoneConsumer = zoneConsumer;
        initializeFields();
        initializeBinder();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeFields() {
        zoneName = new TextField("Zone Name");
        zoneName.setWidthFull();
        zoneCode = new TextField("Zone Code");
        zoneCode.setWidthFull();
        address = new TextField("Address");
        address.setWidthFull();
        contactNumber = new TextField("Contact Number");
        contactNumber.setWidthFull();

        submit = new Button("Submit", event -> {
            try {
                binder.writeBean(zoneDto);
                zoneConsumer.accept(zoneDto);
                close();
            } catch (ValidationException e) {
                log.error("Error while writing bean: {}", e.getMessage());
            }
        });
        submit.setWidthFull();
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(zoneName).asRequired("Zone name is required").bind(ZoneDto::getZoneName, ZoneDto::setZoneName);
        binder.forField(zoneCode).bind(ZoneDto::getZoneCode, ZoneDto::setZoneCode);
        binder.forField(address).bind(ZoneDto::getAddress, ZoneDto::setAddress);
        binder.forField(contactNumber).bind(ZoneDto::getContactNumber, ZoneDto::setContactNumber);
        if (zoneDto.getZoneId() != null) {
            binder.readBean(zoneDto);
        }
    }

    private void initializeMainLayout() {
        mainLayout = new VerticalLayout(new HorizontalLayout(zoneName, zoneCode),
                new HorizontalLayout(address, contactNumber), submit);
        mainLayout.setWidthFull();

        setHeight("20rem");
        setWidth("30rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
