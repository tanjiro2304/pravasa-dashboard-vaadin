package info.pravasa.ui.route;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.DepotDto;
import info.pravasa.dto.RouteDto;
import info.pravasa.ui.route.component.NewRouteDialog;
import info.pravasa.ui.route.component.RouteDetailDialog;

import java.util.List;
import java.util.Objects;

@UIScope
@SpringComponent
@Route("/route")
@Menu(title="Route", icon="vaadin:bus")
public class RouteView extends VerticalLayout {

    private ComboBox<Company> companyComboBox;

    private ComboBox<DepotDto> depotDtoComboBox;

    private Button addNewRoute;

    private HorizontalLayout topLayout;

    private RoutePresenter routePresenter;

    private Grid<RouteDto> routeDtoGrid;

    private VerticalLayout verticalLayout;

    public RouteView(RoutePresenter routePresenter){
        this.routePresenter = routePresenter;
        initializeTopComponents();
        initializeGridLayout();
        setHeightFull();
        add(topLayout,verticalLayout);
    }

    private void initializeTopComponents() {
        companyComboBox = new ComboBox<>("Select a company");
        companyComboBox.addValueChangeListener(event -> {
            if(Objects.nonNull(event.getValue())){
                depotDtoComboBox.setEnabled(true);
                List<DepotDto> depots = routePresenter.fetchDepotsByCompany(event.getValue().getId());
                depotDtoComboBox.setItems(depots);
            }
        });

        companyComboBox.setItemLabelGenerator(Company::getCompanyName);
        companyComboBox.setItems(routePresenter.fetchAllCompanies());

        depotDtoComboBox = new ComboBox<>("Select a depot");
        depotDtoComboBox.setItemLabelGenerator(DepotDto::getDepotName);
        depotDtoComboBox.setEnabled(false);

        depotDtoComboBox.addValueChangeListener(event ->{
            if(Objects.nonNull(event.getValue())){
                List<RouteDto> routeDtos = routePresenter.fetchRouteByDepot(event.getValue().getId());
                routeDtoGrid.setItems(routeDtos);
            }
        });

        addNewRoute = new Button("Add New Route");
        addNewRoute.getElement().getStyle().set("margin-top","2.3rem");

        setButtonListener();
        topLayout = new HorizontalLayout(companyComboBox, depotDtoComboBox, addNewRoute);
    }

    private void setButtonListener(){
        addNewRoute.addClickListener(event ->{
            if(Objects.isNull(companyComboBox.getValue())){
                Notification.show("Please select Transport Company");
            }
            else if(Objects.isNull(depotDtoComboBox.getValue())){
                Notification.show("Please select Depot");
            }
            else{
                NewRouteDialog dialog = new NewRouteDialog(depotDtoComboBox.getValue(), routeDto -> routePresenter.saveRoute(routeDto));
                dialog.open();
            }
        });
    }

    private void initializeGridLayout(){
        routeDtoGrid = new Grid<>();
        routeDtoGrid.addColumn(RouteDto::getRouteNo).setHeader("Route No");
        routeDtoGrid.addColumn(RouteDto::getSource).setHeader("Source");
        routeDtoGrid.addColumn(RouteDto::getDestination).setHeader("Destination");
        routeDtoGrid.addColumn(RouteDto::getRouteDescription).setHeader("Description");
        routeDtoGrid.addColumn(dto -> Objects.isNull(dto.getRouteType()) ? "" : dto.getRouteType().getDisplayName()).setHeader("Route Type");
        routeDtoGrid.addColumn(dto -> Objects.isNull(dto.getServiceType()) ? "" : dto.getServiceType().getDisplayName()).setHeader("Service Type");

        routeDtoGrid.addItemClickListener(event -> {
            RouteDetailDialog dialog = new RouteDetailDialog(event.getItem());
            dialog.open();
        });

        verticalLayout = new VerticalLayout(routeDtoGrid);
        verticalLayout.setHeight("100%");
    }
}
