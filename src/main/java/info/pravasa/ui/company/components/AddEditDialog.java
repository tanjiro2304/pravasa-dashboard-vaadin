package info.pravasa.ui.company.components;


import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import info.pravasa.dto.enums.ModeOfTransport;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
public class AddEditDialog extends Dialog {

    private TextField companyName;
    private TextField address;
    private EmailField emailField;
    private MultiSelectComboBox<City> cityMultiSelectComboBox;
    private ComboBox<ModeOfTransport> modeOfTransportComboBox;
    private TextField contactInfo;
    private VerticalLayout mainlayout;
    private Binder<Company> binder;
    private final Consumer<Company> companyConsumer;
    private final Supplier<List<City>> citySupplier;
    private Button submit;

    @Setter
    private Company company;
    public AddEditDialog(Company company, Consumer<Company> companyConsumer, Supplier<List<City>> citySupplier) {
        this.companyConsumer = companyConsumer;
        this.citySupplier = citySupplier;
        this.company = company;
        initializeFields();
        initializeBinder();
        setMainLayout();
    }

    private void initializeFields() {
        companyName = new TextField("Company Name");
        companyName.setWidthFull();
        address = new TextField("Address");
        address.setWidthFull();
        emailField = new EmailField("Email Field");
        emailField.setWidthFull();
        cityMultiSelectComboBox = new MultiSelectComboBox<>("Select Cities");
        cityMultiSelectComboBox.setWidthFull();
        contactInfo = new TextField("Contact Info");
        contactInfo.setWidthFull();
        modeOfTransportComboBox = new ComboBox<>("Mode of Transport");
        modeOfTransportComboBox.setWidthFull();
        cityMultiSelectComboBox.setItemLabelGenerator(City::getCityName);
        cityMultiSelectComboBox.setWidthFull();
        cityMultiSelectComboBox.setItems(citySupplier.get());
        modeOfTransportComboBox.setItemLabelGenerator(ModeOfTransport::getDisplayName);
        submit = new Button("Submit", clickEvent -> {
            try {
                binder.writeBean(company);
                companyConsumer.accept(company);
                close();
            } catch (ValidationException e) {
                log.error(e.getMessage());
            }
        });
        submit.setWidthFull();
        modeOfTransportComboBox.setItems(ModeOfTransport.values());
    }

    private void initializeBinder(){
        binder = new Binder<>();
        binder.forField(companyName).bind(Company::getCompanyName, Company::setCompanyName);
        binder.forField(address).bind(Company::getAddress, Company::setAddress);
        binder.forField(emailField).bind(Company::getEmail, Company::setEmail);
        binder.forField(contactInfo).bind(Company::getContactNo, Company::setContactNo);
        binder.forField(address).bind(Company::getAddress, Company::setAddress);
        binder.forField(modeOfTransportComboBox).bind(Company::getModeOfTransport, Company::setModeOfTransport);
        binder.forField(cityMultiSelectComboBox).bind(Company::getCities, Company::setCities);

        if(company.getId() != null){
            binder.readBean(company);
        }
    }

    private void setMainLayout(){
        mainlayout = new VerticalLayout(new HorizontalLayout(companyName,address),new HorizontalLayout(emailField, contactInfo),
                new HorizontalLayout(cityMultiSelectComboBox,modeOfTransportComboBox),submit);
        mainlayout.setWidthFull();

        setHeight("28rem");
        setWidth("30rem");
        add(mainlayout);
    }
}
