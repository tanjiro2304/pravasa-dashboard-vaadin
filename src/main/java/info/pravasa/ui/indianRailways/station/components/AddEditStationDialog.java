package info.pravasa.ui.indianRailways.station.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.DivisionDto;
import info.pravasa.dto.StationDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class AddEditStationDialog extends Dialog {

    private TextField stationName;
    private TextField stationCode;
    private NumberField platformCount;
    private NumberField latitude;
    private NumberField longitude;
    private Checkbox fastHalt;
    private ComboBox<DivisionDto> divisionComboBox;
    private VerticalLayout mainLayout;
    private Binder<StationDto> binder;
    private Button submit;

    private final StationDto stationDto;
    private final Consumer<StationDto> stationConsumer;
    private final List<DivisionDto> divisions;

    public AddEditStationDialog(StationDto stationDto, Consumer<StationDto> stationConsumer, List<DivisionDto> divisions) {
        this.stationDto = stationDto;
        this.stationConsumer = stationConsumer;
        this.divisions = divisions;
        initializeFields();
        initializeBinder();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeFields() {
        stationName = new TextField("Station Name");
        stationName.setWidthFull();
        stationCode = new TextField("Station Code");
        stationCode.setWidthFull();
        platformCount = new NumberField("Platform Count");
        platformCount.setWidthFull();
        latitude = new NumberField("Latitude");
        latitude.setWidthFull();
        longitude = new NumberField("Longitude");
        longitude.setWidthFull();
        fastHalt = new Checkbox("Fast Halt");

        divisionComboBox = new ComboBox<>("Division");
        divisionComboBox.setWidthFull();
        divisionComboBox.setItemLabelGenerator(DivisionDto::getDivisionName);
        divisionComboBox.setItems(divisions);

        submit = new Button("Submit", event -> {
            try {
                binder.writeBean(stationDto);
                stationConsumer.accept(stationDto);
                close();
            } catch (ValidationException e) {
                log.error("Error while writing bean: {}", e.getMessage());
            }
        });
        submit.setWidthFull();
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(stationName).asRequired("Station name is required").bind(StationDto::getStationName, StationDto::setStationName);
        binder.forField(stationCode).bind(StationDto::getStationCode, StationDto::setStationCode);
        binder.forField(platformCount).bind(
                dto -> Objects.nonNull(dto.getPlatformCount()) ? dto.getPlatformCount().doubleValue() : null,
                (dto, count) -> dto.setPlatformCount(Objects.nonNull(count) ? count.intValue() : null));
        binder.forField(latitude).bind(StationDto::getLatitude, StationDto::setLatitude);
        binder.forField(longitude).bind(StationDto::getLongitude, StationDto::setLongitude);
        binder.forField(fastHalt).bind(
                dto -> Objects.nonNull(dto.getFastHalt()) && dto.getFastHalt(),
                StationDto::setFastHalt);
        binder.forField(divisionComboBox).bind(
                dto -> divisions.stream().filter(division -> division.getId().equals(dto.getDivisionId())).findFirst().orElse(null),
                (dto, division) -> dto.setDivisionId(Objects.nonNull(division) ? division.getId() : null));
        if (stationDto.getStationId() != null) {
            binder.readBean(stationDto);
        }
    }

    private void initializeMainLayout() {
        mainLayout = new VerticalLayout(new HorizontalLayout(stationName, stationCode),
                new HorizontalLayout(platformCount, fastHalt),
                new HorizontalLayout(latitude, longitude), divisionComboBox, submit);
        mainLayout.setWidthFull();

        setHeight("32rem");
        setWidth("32rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
