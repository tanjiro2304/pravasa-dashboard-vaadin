package info.pravasa.ui.company;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import jakarta.annotation.Resource;

import java.util.List;

@UIScope
@SpringComponent
@Route("/company")
@Menu(title="Company", icon="vaadin:building")
public class CompanyView extends VerticalLayout {

    @Resource
    private CompanyPresenter companyPresenter;
    private Grid<Company> companyGrid;

    public CompanyView() {
        initializeGrid();
        add(companyGrid);
    }

    private void initializeGrid() {
        companyGrid = new Grid<>();
        companyGrid.addColumn(Company::getCompanyName).setHeader("Company Name");
        companyGrid.addColumn(Company::getCityName).setHeader("Cities");
        companyGrid.addColumn(Company::getAddress).setHeader("Address");
        companyGrid.addColumn(Company::getEmail).setHeader("Email");
        companyGrid.addColumn(Company::getCities).setHeader("Cities");

    }
}
