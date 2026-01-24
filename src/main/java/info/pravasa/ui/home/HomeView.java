package info.pravasa.ui.home;

import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Route("/")
@Menu(title="Home", icon="vaadin:home")
public class HomeView extends VerticalLayout {

    public HorizontalLayout horizontalLayout;

    private MenuBar menuBar;

    public HomeView(){
        initializeMenuBar();
        intializeLayout();
    }

    private void initializeMenuBar(){
        menuBar = new MenuBar();
        menuBar.addItem("Company");
        menuBar.addItem("Route");
        menuBar.addItem("Depots");

    }

    private void intializeLayout() {
        horizontalLayout = new HorizontalLayout();
        TextField textField = new TextField();
        horizontalLayout.add(textField);
        add(menuBar,horizontalLayout);
    }
}
