package info.pravasa.ui.roadTransport.home;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@UIScope
@SpringComponent
@Route("/")
@Menu(title = "Home", icon = "vaadin:home")
public class HomeView extends VerticalLayout {

    public HomeView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        getStyle()
                .set("background-image", "url('images/home-background.png')")
                .set("background-size", "cover")
                .set("background-position", "top center")
                .set("background-repeat", "no-repeat");

        add(createWelcomeCard());
    }

    private VerticalLayout createWelcomeCard() {
        var title = new H1("Welcome to Pravasa");
        title.getStyle().set("margin", "0").set("color", "white");

        var tagline = new Paragraph("Your unified dashboard for Road Transport and Indian Railways operations");
        tagline.getStyle().set("color", "white").set("margin", "0.5rem 0 1.5rem 0");

        var roadTransportButton = new Button("Road Transport", new Icon("vaadin:bus"),
                e -> UI.getCurrent().navigate("company"));
        roadTransportButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        var indianRailwaysButton = new Button("Indian Railways", new Icon("vaadin:train"),
                e -> UI.getCurrent().navigate("division"));
        indianRailwaysButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY, ButtonVariant.LUMO_LARGE);

        var buttons = new HorizontalLayout(roadTransportButton, indianRailwaysButton);
        buttons.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        var card = new VerticalLayout(title, tagline, buttons);
        card.setAlignItems(FlexComponent.Alignment.CENTER);
        card.getStyle()
                .set("background-color", "rgba(0, 0, 0, 0.55)")
                .set("border-radius", "var(--lumo-border-radius-l)")
                .set("padding", "3rem")
                .set("text-align", "center");
        return card;
    }
}
