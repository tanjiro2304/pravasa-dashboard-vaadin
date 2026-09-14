package info.pravasa.ui.layout;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.server.menu.MenuConfiguration;
import com.vaadin.flow.server.menu.MenuEntry;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Layout("/")
public class MainLayout extends AppLayout {

    private static final String ROAD_TRANSPORT_PACKAGE = "info.pravasa.ui.roadTransport";
    private static final String INDIAN_RAILWAYS_PACKAGE = "info.pravasa.ui.indianRailways";
    private static final String ROAD_TRANSPORT_GROUP = "Road Transport";
    private static final String INDIAN_RAILWAYS_GROUP = "Indian Railways";

    MainLayout() {
        setPrimarySection(Section.NAVBAR);
        addToNavbar(createMenuBar());
    }

    private MenuBar createMenuBar() {
        var menuBar = new MenuBar();
        menuBar.setWidthFull();

        MenuConfiguration.getMenuEntries().stream()
                .filter(entry -> "/".equals(entry.path()))
                .forEach(entry -> addFlatMenuItem(menuBar, entry));

        Map<String, List<MenuEntry>> groupedEntries = MenuConfiguration.getMenuEntries().stream()
                .filter(entry -> !"/".equals(entry.path()))
                .collect(Collectors.groupingBy(this::resolveGroup, LinkedHashMap::new, Collectors.toList()));

        groupedEntries.forEach((group, entries) -> {
            MenuItem groupItem = menuBar.addItem(group);
            entries.forEach(entry -> groupItem.getSubMenu()
                    .addItem(createMenuItemContent(entry), e -> UI.getCurrent().navigate(entry.path())));
        });

        return menuBar;
    }

    private void addFlatMenuItem(MenuBar menuBar, MenuEntry entry) {
        menuBar.addItem(createMenuItemContent(entry), e -> UI.getCurrent().navigate(entry.path()));
    }

    private Component createMenuItemContent(MenuEntry entry) {
        var label = new Span(entry.title());
        if (entry.icon() == null) {
            return label;
        }
        var content = new HorizontalLayout(new Icon(entry.icon()), label);
        content.setAlignItems(FlexComponent.Alignment.CENTER);
        content.setSpacing(true);
        return content;
    }

    private String resolveGroup(MenuEntry entry) {
        if (entry.menuClass() != null) {
            var packageName = entry.menuClass().getPackageName();
            if (packageName.startsWith(ROAD_TRANSPORT_PACKAGE)) {
                return ROAD_TRANSPORT_GROUP;
            }
            if (packageName.startsWith(INDIAN_RAILWAYS_PACKAGE)) {
                return INDIAN_RAILWAYS_GROUP;
            }
        }
        return "Other";
    }
}
