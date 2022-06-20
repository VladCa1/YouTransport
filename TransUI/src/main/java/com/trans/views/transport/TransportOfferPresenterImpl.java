package com.trans.views.transport;

import com.trans.security.data.UserDetails;
import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.services.DriverService;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.services.UserService;
import com.trans.utils.DialogUtils;
import com.trans.views.myOffers.forms.TransportOfferForm;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;

@SpringComponent
@UIScope
public class TransportOfferPresenterImpl implements TransportOfferView.TransportOfferPresenter {

    @Setter
    private TransportOfferView view;

    @Autowired
    @Getter
    private OfferService offerService;

    @Autowired
    @Getter
    private UserService userService;

    @Autowired
    @Getter
    private DriverService driverService;

    @Autowired
    @Getter
    private LocationService locationService;


    @Override
    public void makeFormReadOnly(TransportOfferForm form) throws URISyntaxException {
        addChangeBehaviourForLocationSelect(form.getToLocationSelect());
        addChangeBehaviourForLocationSelect(form.getFromLocationSelect());
        form.getClearForm().setVisible(false);
        form.getClearForm().setEnabled(false);
        form.getCalculateDistanceButton().setEnabled(false);
        form.getCalculateDistanceButton().setVisible(false);
        form.getGoodsTypeSelect().setReadOnly(true);
        form.getToLocationSelect().getCountrySelect().setReadOnly(true);
        form.getToLocationSelect().getCitySelect().setReadOnly(true);
        form.getDistanceDurationTextField().setReadOnly(true);
        form.getDistanceTextField().setReadOnly(true);
        form.getFromLocationSelect().getCitySelect().setReadOnly(true);
        form.getFromLocationSelect().getCountrySelect().setReadOnly(true);
        form.getSaveButton().setVisible(false);
        form.getDescriptionField().setReadOnly(true);
        form.getPayCurrencySelect().setReadOnly(true);
        form.getPayTypeSelect().setReadOnly(true);
        form.getPayValueNumberField().setReadOnly(true);
        form.getFromDatePicker().setReadOnly(true);
        form.getToDatePicker().setReadOnly(true);
        form.getGoodsTypeSelect().setReadOnly(true);
        form.getHazardous().setReadOnly(true);
        form.getHazardous().setEnabled(false);
        form.getMaxDistance().setReadOnly(true);
        form.getVehicleTypeSelect().setReadOnly(true);
        form.getMake().setReadOnly(true);
        form.getTonnage().setReadOnly(true);
        form.getType().setReadOnly(true);
        form.getFabricationYear().setReadOnly(true);
        form.getHorsePower().setReadOnly(true);
        form.getDriverSelect().setEnabled(false);
        form.getDriverSelect().setVisible(false);
    }

    @Override
    public void populateForm() throws Exception {
        TransportFormResultDTO transportFormResultDTO = offerService.getTransportOffer(view.getParameter());
        view.getForm().completeForm(transportFormResultDTO);
    }

    @Override
    public void init() {
    }

    @Override
    public void populateDriverButton() throws Exception {
        TransportFormResultDTO transportFormResultDTO = offerService.getTransportOffer(view.getParameter());
        DriverResultEntryDTO driver = transportFormResultDTO.getDriver();

        VerticalLayout driverLayout = new VerticalLayout();


        Image image;
        if(driver.getImage() != null){
            StreamResource resource = new StreamResource("image.png",
                    () -> new ByteArrayInputStream(driver.getImage()));
            image = new Image(resource, "profile");
        }else{
            image = new Image("images/default_profile.png","profile");

        }
        image.setHeight("80px");
        image.setWidth("80px");
        image.getStyle().set("margin", "auto");
        image.getStyle().set("margin-bottom", "15px");
        driverLayout.add(image);
        driverLayout.add(new Span("Name:  " + driver.getFirstName() + " " + driver.getLastName()));
        driverLayout.add(new Span("Nationality:  " + driver.getNationality()));
        driverLayout.add(new Span("Age:  " + driver.getAge() + " years"));
        driverLayout.add(new Span("Experience:  " + driver.getExperience() + " years"));
        driverLayout.setMinWidth("200px");

        DialogUtils.alert("Driver", driverLayout).open();

    }

    @Override
    public void populatePersonLayout() throws Exception {
        UserDetails userDetails = userService.getUserForTransportOfferId(view.getParameter());
        
        if(userDetails.getPhotoDetails() != null){
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(userDetails.getPhotoDetails(), "png", baos);
            byte[] bytes = baos.toByteArray();

            StreamResource resource = new StreamResource("image.png",
                    () -> new ByteArrayInputStream(bytes));
            Image image = new Image(resource, "profile");
            image.setHeight("400px");
            image.setWidth("400px");
            view.setImage(image);
        }else{
            Image image = new Image("images/default_profile.png","profile");
            image.setMaxHeight("400px");
            image.setMaxWidth("400px");
            view.setImage(image);
        }

        if(userDetails.getPhoneNumber() != null){
            view.getPhoneField().setValue(userDetails.getPhoneNumber());
        }else{
            view.getPhoneField().setValue("NOT DISCLOSED");
        }
        if(userDetails.getEmail() != null){
            view.getEmailField().setValue(userDetails.getEmail());
        }else{
            view.getEmailField().setValue("NOT DISCLOSED");
        }
        if(userDetails.getCompanyDetails() != null){
            view.getCompanyField().setValue(userDetails.getCompanyDetails());
        }else{
            view.getCompanyField().setValue("NOT DISCLOSED");
        }
        if(userDetails.getFirstName() != null && userDetails.getLastName() != null){
            view.getNameField().setValue(userDetails.getFirstName() + " " + userDetails.getLastName());
        }else{
            view.getNameField().setValue("NOT DISCLOSED");
        }
    }


    private void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException {
        select.populateCountrySelect(locationService.findAllCountries());
        select.getCountrySelect().addValueChangeListener(event -> {
            select.getCitySelect().setEnabled(true);
            try {
                select.populateCitySelect(locationService.findAllCitiesForCountry(select.getCountrySelect().getValue()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

}
