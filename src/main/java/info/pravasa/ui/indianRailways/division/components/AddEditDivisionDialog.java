package info.pravasa.ui.indianRailways.division.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.DivisionDto;
import info.pravasa.dto.ZoneDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class AddEditDivisionDialog extends Dialog {

    private TextField divisionName;
    private TextField divisionCode;
    private TextField address;
    private TextField contactNumber;
    private ComboBox<ZoneDto> zoneComboBox;
    private VerticalLayout mainLayout;
    private Binder<DivisionDto> binder;
    private Button submit;

    private final DivisionDto divisionDto;
    private final Consumer<DivisionDto> divisionConsumer;
    private final List<ZoneDto> zones;

    public AddEditDivisionDialog(DivisionDto divisionDto, Consumer<DivisionDto> divisionConsumer, List<ZoneDto> zones) {
        this.divisionDto = divisionDto;
        this.divisionConsumer = divisionConsumer;
        this.zones = zones;
        initializeFields();
        initializeBinder();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeFields() {
        divisionName = new TextField("Division Name");
        divisionName.setWidthFull();
        divisionCode = new TextField("Division Code");
        divisionCode.setWidthFull();
        address = new TextField("Address");
        address.setWidthFull();
        contactNumber = new TextField("Contact Number");
        contactNumber.setWidthFull();

        zoneComboBox = new ComboBox<>("Zone");
        zoneComboBox.setWidthFull();
        zoneComboBox.setItemLabelGenerator(ZoneDto::getZoneName);
        zoneComboBox.setItems(zones);

        submit = new Button("Submit", event -> {
            try {
                binder.writeBean(divisionDto);
                divisionConsumer.accept(divisionDto);
                close();
            } catch (ValidationException e) {
                log.error("Error while writing bean: {}", e.getMessage());
            }
        });
        submit.setWidthFull();
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(divisionName).asRequired("Division name is required").bind(DivisionDto::getDivisionName, DivisionDto::setDivisionName);
        binder.forField(divisionCode).bind(DivisionDto::getDivisionCode, DivisionDto::setDivisionCode);
        binder.forField(address).bind(DivisionDto::getAddress, DivisionDto::setAddress);
        binder.forField(contactNumber).bind(DivisionDto::getContactNumber, DivisionDto::setContactNumber);
        binder.forField(zoneComboBox).bind(
                dto -> zones.stream().filter(zone -> zone.getZoneId().equals(dto.getZoneId())).findFirst().orElse(null),
                (dto, zone) -> dto.setZoneId(Objects.nonNull(zone) ? zone.getZoneId() : null));
        if (divisionDto.getId() != null) {
            binder.readBean(divisionDto);
        }
    }

    private void initializeMainLayout() {
        mainLayout = new VerticalLayout(new HorizontalLayout(divisionName, divisionCode),
                new HorizontalLayout(address, contactNumber), zoneComboBox, submit);
        mainLayout.setWidthFull();

        setHeight("26rem");
        setWidth("30rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
