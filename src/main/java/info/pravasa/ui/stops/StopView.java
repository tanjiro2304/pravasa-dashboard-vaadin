package info.pravasa.ui.stops;

import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import info.pravasa.dto.Company;
import info.pravasa.dto.StopDto;

@UIScope
@SpringComponent
@Route("/stop")
@Menu(title="Stops", icon="vaadin:flag-o")
public class StopView extends VerticalLayout {

    private StopPresenter stopPresenter;
    private Grid<StopDto> stopDtoGrid;
    private ComboBox<Company> companyComboBox;
    private VerticalLayout mainLayout;

    public StopView(StopPresenter stopPresenter) {
        this.stopPresenter = stopPresenter;
        initializeTopComponents();
        initializeGrid();
    }

    private void initializeGrid() {
        companyComboBox = new ComboBox<>("Select A Company");

    }

    private void initializeTopComponents() {
    }
}
