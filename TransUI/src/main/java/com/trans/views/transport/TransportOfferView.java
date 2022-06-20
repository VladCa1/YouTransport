package com.trans.views.transport;

import com.trans.services.DriverService;
import com.trans.services.LocationService;
import com.trans.views.myOffers.forms.TransportOfferForm;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextField;

import java.net.URISyntaxException;

public interface TransportOfferView {
    Long getParameter();

    TransportOfferForm getForm();

    void setImage(Image image);

    TextField getPhoneField();

    TextField getEmailField();

    TextField getCompanyField();

    TextField getNameField();

    public interface TransportOfferPresenter {

        void makeFormReadOnly(TransportOfferForm form) throws URISyntaxException;

        void populateForm() throws Exception;

        void populatePersonLayout() throws Exception;

        void setView(TransportOfferView view);

        void init();

        LocationService getLocationService();

        DriverService getDriverService();

        void populateDriverButton() throws Exception;
    }
}
