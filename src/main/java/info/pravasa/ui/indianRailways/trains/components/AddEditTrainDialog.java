package info.pravasa.ui.indianRailways.trains.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.TrainDto;
import info.pravasa.dto.enums.TrainServiceType;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.function.Consumer;

@Slf4j
public class AddEditTrainDialog extends Dialog {

    private NumberField trainServiceNo;
    private TextField trainServiceName;
    private ComboBox<TrainServiceType> trainServiceTypeComboBox;
    private VerticalLayout mainLayout;
    private Binder<TrainDto> binder;
    private Button submit;

    private final TrainDto trainDto;
    private final Consumer<TrainDto> trainConsumer;

    public AddEditTrainDialog(TrainDto trainDto, Consumer<TrainDto> trainConsumer) {
        this.trainDto = trainDto;
        this.trainConsumer = trainConsumer;
        initializeFields();
        initializeBinder();
        initializeMainLayout();
        add(mainLayout);
    }

    private void initializeFields() {
        trainServiceNo = new NumberField("Train Service No");
        trainServiceNo.setWidthFull();
        trainServiceName = new TextField("Train Service Name");
        trainServiceName.setWidthFull();

        trainServiceTypeComboBox = new ComboBox<>("Train Service Type");
        trainServiceTypeComboBox.setWidthFull();
        trainServiceTypeComboBox.setItemLabelGenerator(TrainServiceType::getDisplayName);
        trainServiceTypeComboBox.setItems(TrainServiceType.values());

        submit = new Button("Submit", event -> {
            try {
                binder.writeBean(trainDto);
                trainConsumer.accept(trainDto);
                close();
            } catch (ValidationException e) {
                log.error("Error while writing bean: {}", e.getMessage());
            }
        });
        submit.setWidthFull();
    }

    private void initializeBinder() {
        binder = new Binder<>();
        binder.forField(trainServiceNo).bind(
                dto -> Objects.nonNull(dto.getTrainServiceNo()) ? dto.getTrainServiceNo().doubleValue() : null,
                (dto, no) -> dto.setTrainServiceNo(Objects.nonNull(no) ? no.longValue() : null));
        binder.forField(trainServiceName).asRequired("Train service name is required")
                .bind(TrainDto::getTrainServiceName, TrainDto::setTrainServiceName);
        binder.forField(trainServiceTypeComboBox).bind(TrainDto::getTrainServiceType, TrainDto::setTrainServiceType);
        if (trainDto.getId() != null) {
            binder.readBean(trainDto);
        }
    }

    private void initializeMainLayout() {
        mainLayout = new VerticalLayout(new HorizontalLayout(trainServiceNo, trainServiceName),
                trainServiceTypeComboBox, submit);
        mainLayout.setWidthFull();

        setHeight("22rem");
        setWidth("30rem");
        setCloseOnOutsideClick(true);
        setCloseOnEsc(true);
        setDraggable(true);
    }
}
