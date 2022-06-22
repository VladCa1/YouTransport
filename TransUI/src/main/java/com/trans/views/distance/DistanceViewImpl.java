package com.trans.views.distance;

import com.trans.views.MainLayout;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;

@PageTitle("Transport You - Calculate Distance")
@Route(value = "/calculate-distance", layout = MainLayout.class)
public class DistanceViewImpl extends VerticalLayout implements DistanceView {

    private VerticalLayout distanceWrapper;

    @Getter
    private Button calculateDistanceButton;

    @Getter
    private TextField distanceTextField;

    @Getter
    private TextField distanceDurationTextField;

    @Getter
    @Setter
    private LocationSelect toLocation;

    @Getter
    @Setter
    private LocationSelect fromLocation;

    private final DistancePresenter presenter;

    @Autowired
    public DistanceViewImpl(DistancePresenter presenter) throws URISyntaxException {
        this.presenter = presenter;
        this.presenter.setView(this);
        init();
        this.presenter.init();
    }

    private void init() {
        initElements();
        initDistanceWrapper();
        this.add(distanceWrapper);
    }

    private void initElements() {
        fromLocation = new LocationSelect();
        toLocation = new LocationSelect();
        calculateDistanceButton = new Button("Calculate");
        distanceTextField = new TextField("Distance");
        distanceDurationTextField = new TextField("Duration");
        distanceTextField.setReadOnly(true);
        distanceDurationTextField.setReadOnly(true);
    }

    private void initDistanceWrapper() {
        distanceWrapper = new VerticalLayout();
        distanceWrapper.add(new Span("From"),fromLocation, new Span("To"),toLocation);
        calculateDistanceButton.getStyle().set("margin-top", "20px");
        distanceWrapper.add(new HorizontalLayout(calculateDistanceButton, distanceTextField, distanceDurationTextField));
        distanceWrapper.getStyle().set("margin", "auto");
        distanceWrapper.getStyle().set("border", "1px solid lightgray");
        distanceWrapper.getStyle().set("margin-top", "100px");
        distanceWrapper.setMaxWidth("600px");
    }

}
