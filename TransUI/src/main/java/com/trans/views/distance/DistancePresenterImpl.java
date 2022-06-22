package com.trans.views.distance;


import com.trans.serviceInterface.models.DistanceMatrixResponse;
import com.trans.services.LocationService;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;


@SpringComponent
@UIScope
public class DistancePresenterImpl implements DistanceView.DistancePresenter {

    @Setter
    private DistanceView view;

    @Autowired
    private LocationService locationService;

    @Override
    public void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException {
        select.populateCountrySelect(locationService.findAllCountries());
        select.getCountrySelect().addValueChangeListener(event ->{
            select.getCitySelect().setEnabled(true);
            try {
                select.populateCitySelect(locationService.findAllCitiesForCountry(select.getCountrySelect().getValue()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void init() throws URISyntaxException {
        addChangeBehaviourForLocationSelect(view.getToLocation());
        addChangeBehaviourForLocationSelect(view.getFromLocation());
        addChangeBehaviourForCalculateButton(view.getCalculateDistanceButton());
    }

    public void addChangeBehaviourForCalculateButton(Button button){
        button.addClickListener((ComponentEventListener<ClickEvent<Button>>) event -> {
            try {
                DistanceMatrixResponse distanceMatrixResponse = locationService.getDirectionInfo(view.getFromLocation().getCitySelect().getValue() + " " + view.getFromLocation().getCountrySelect().getValue(), view.getToLocation().getCitySelect().getValue() + " " + view.getToLocation().getCountrySelect().getValue());
                view.getDistanceTextField().setValue(distanceMatrixResponse.getRows().get(0).getElements().get(0).getDistance().getText());
                view.getDistanceDurationTextField().setValue(distanceMatrixResponse.getRows().get(0).getElements().get(0).getDuration().getText());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
        });
    }
}
