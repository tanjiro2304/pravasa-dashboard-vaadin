package info.pravasa.ui.indianRailways.trainHalts.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.IrHaltDto;
import info.pravasa.dto.StationDto;
import info.pravasa.dto.TrainDto;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class AddEditTrainHaltDialog extends Dialog {

    private ComboBox<TrainDto> trainComboBox;
    private ComboBox<StationDto> stationComboBox;
    private NumberField platformNo;
    private TextField arrivalTime;
    private TextField departureTime;
    private VerticalLayout mainLayout;
    private Binder<IrHaltDto> binder;
    private Button submit;

    private final IrHaltDto irHaltDto;
    private final Consumer<IrHaltDto> irHaltConsumer;
    private final List<TrainDto> trains;
    private final List<StationDto> stations;

    public AddEditTrainHaltDialog(IrHaltDto irHaltDto, Consumer<IrHaltDto> irHaltConsumer, List<TrainDto> trains, List<StationDto> stations) {
        this.irHaltDto = irHaltDto;
        this.irHaltConsumer = irHaltConsumer;
        this.trains = trains;
        this.stations = stations;
        initializeFields();
        initializeBinder();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeFields() {
        trainComboBox = new ComboBox<>("Train");
        trainComboBox.setWidthFull();
        trainComboBox.setItemLabelGenerator(TrainDto::getTrainServiceName);
        trainComboBox.setItems(trains);

        stationComboBox = new ComboBox<>("Station");
        stationComboBox.setWidthFull();
        stationComboBox.setItemLabelGenerator(StationDto::getStationName);
        stationComboBox.setItems(stations);

        platformNo = new NumberField("Platform No");
        platformNo.setWidthFull();
        arrivalTime = new TextField("Arrival Time");
        arrivalTime.setPlaceholder("HH:mm");
        arrivalTime.setWidthFull();
        departureTime = new TextField("Departure Time");
        departureTime.setPlaceholder("HH:mm");
        departureTime.setWidthFull();

        submit = new Button("Submit", event -> {
            try {
                binder.writeBean(irHaltDto);
                irHaltConsumer.accept(irHaltDto);
                close();
            } catch (ValidationException e) {
                log.error("Error while writing bean: {}", e.getMessage());
            }
        });
        submit.setWidthFull();
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(trainComboBox).asRequired("Train is required").bind(
                dto -> trains.stream().filter(train -> train.getId().equals(dto.getTrainId())).findFirst().orElse(null),
                (dto, train) -> dto.setTrainId(Objects.nonNull(train) ? train.getId() : null));
        binder.forField(stationComboBox).asRequired("Station is required").bind(
                dto -> stations.stream().filter(station -> station.getStationId().equals(dto.getStationId())).findFirst().orElse(null),
                (dto, station) -> dto.setStationId(Objects.nonNull(station) ? station.getStationId() : null));
        binder.forField(platformNo).bind(
                dto -> Objects.nonNull(dto.getPlatformNo()) ? dto.getPlatformNo().doubleValue() : null,
                (dto, no) -> dto.setPlatformNo(Objects.nonNull(no) ? no.intValue() : null));
        binder.forField(arrivalTime).bind(IrHaltDto::getArrivalTime, IrHaltDto::setArrivalTime);
        binder.forField(departureTime).bind(IrHaltDto::getDepartureTime, IrHaltDto::setDepartureTime);
        if (irHaltDto.getId() != null) {
            binder.readBean(irHaltDto);
        }
    }

    private void initializeMainLayout() {
        mainLayout = new VerticalLayout(new HorizontalLayout(trainComboBox, stationComboBox),
                new HorizontalLayout(platformNo), new HorizontalLayout(arrivalTime, departureTime), submit);
        mainLayout.setWidthFull();

        setHeight("28rem");
        setWidth("32rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
