package info.pravasa.ui.company;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.NativeButtonRenderer;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.City;
import info.pravasa.dto.Company;
import info.pravasa.ui.company.components.AddEditDialog;
import jakarta.annotation.Resource;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UIScope
@SpringComponent
@Route("/company")
@Menu(title="Company", icon="vaadin:building")
public class CompanyView extends VerticalLayout {

    private HorizontalLayout btnLayout;
    private final CompanyPresenter companyPresenter;
    private Grid<Company> companyGrid;
    private final List<Company> companies;

    public CompanyView(CompanyPresenter companyPresenter) {
        this.companyPresenter = companyPresenter;
        this.companies = new ArrayList<>();
        initializeBtnLayout();
        initializeGrid();
        setDataInGrid();
        add(btnLayout,companyGrid);
    }

    private void setDataInGrid() {
        companies.addAll(companyPresenter.fetchAllCompanies());
        companyGrid.getDataProvider().refreshAll();
    }

    private void initializeBtnLayout() {
        Button addCompany = new Button("Add Company", event -> {
            AddEditDialog dialog = new AddEditDialog(new Company(), dto -> {
                dto = companyPresenter.saveCompany(dto);
                refreshGrid(dto);
            }, companyPresenter::fetchAllCities);
            dialog.open();
        });
        btnLayout = new HorizontalLayout(addCompany);

    }

    private void initializeGrid() {
        companyGrid = new Grid<>();
        companyGrid.addColumn(Company::getCompanyName).setHeader("Company Name");
        companyGrid.addColumn(dto -> dto.getCompanyType().getName()).setHeader("Company Type");
        companyGrid.addColumn(Company::getAddress).setHeader("Address");
        companyGrid.addColumn(Company::getEmail).setHeader("Email");
        companyGrid.addColumn(dto -> {
            if(CollectionUtils.isEmpty(dto.getCities())){
                return "";
            }
            return dto.getCities().stream()
                    .map(City::getCityName)
                    .collect(Collectors.joining(","));
        }).setHeader("Cities");
        companyGrid.addColumn(new NativeButtonRenderer<>("Edit", clickedItem -> {
            AddEditDialog dialog= new AddEditDialog(clickedItem, dto -> {
                dto = companyPresenter.saveCompany(dto);
                refreshGrid(dto);
            }, companyPresenter::fetchAllCities);
            dialog.setCompany(clickedItem);
            dialog.open();
        }));
        companyGrid.setItems(companies);
    }

    public void refreshGrid(Company company){
        companies.add(company);
        companyGrid.getDataProvider().refreshAll();
    }
}
