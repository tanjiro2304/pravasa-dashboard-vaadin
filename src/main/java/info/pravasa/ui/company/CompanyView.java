package info.pravasa.ui.company;

import com.vaadin.flow.component.grid.Grid;
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


    private final CompanyPresenter companyPresenter;
    private Grid<Company> companyGrid;

    public CompanyView(CompanyPresenter companyPresenter) {
        this.companyPresenter = companyPresenter;
        initializeGrid();
        add(companyGrid);
    }

    private void initializeGrid() {
        companyGrid = new Grid<>();
        companyGrid.addColumn(Company::getCompanyName).setHeader("Company Name");
        companyGrid.addColumn(Company::getCityName).setHeader("Cities");
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
            AddEditDialog dialog= new AddEditDialog(clickedItem, companyPresenter::saveCompany, companyPresenter::fetchAllCities);
            dialog.setCompany(clickedItem);
            dialog.open();
        }));
        companyGrid.setItems(companyPresenter.fetchAllCompanies());
    }
}
