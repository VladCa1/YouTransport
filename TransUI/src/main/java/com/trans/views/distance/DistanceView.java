package com.trans.views.distance;

import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;

public interface DistanceView {

    LocationSelect getToLocation();

    LocationSelect getFromLocation();

    Button getCalculateDistanceButton();

    TextField getDistanceTextField();

    TextField getDistanceDurationTextField();

    public interface DistancePresenter {
        
        void setView(DistanceView view);

        void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException;

        void init() throws URISyntaxException;
    }
    
    
}
