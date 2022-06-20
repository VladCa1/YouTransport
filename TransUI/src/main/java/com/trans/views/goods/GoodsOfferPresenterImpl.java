package com.trans.views.goods;

import com.trans.security.data.UserDetails;
import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.services.UserService;
import com.trans.views.myOffers.forms.GoodsOfferForm;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.net.URISyntaxException;

@SpringComponent
@UIScope
public class GoodsOfferPresenterImpl implements GoodsOfferView.GoodsOfferViewPresenter {

    @Setter
    private GoodsOfferView view;

    @Autowired
    private OfferService offerService;

    @Autowired
    private UserService userService;

    @Autowired
    private LocationService locationService;

    @Override
    public void init() {
    }

    @Override
    public void populateForm() throws Exception {

        GoodsFormResultEntryDTO goodsFormResultEntryDTO = offerService.getGoodsOffer(view.getParameter());
        view.getForm().completeForm(goodsFormResultEntryDTO);
    }

    @Override
    public void populatePersonLayout() throws Exception {

        UserDetails userDetails = userService.getUserForGoodsOfferId(view.getParameter());


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

    @Override
    public void makeFormReadOnly(GoodsOfferForm form) throws URISyntaxException {
        addChangeBehaviourForLocationSelect(form.getToLocationSelect());
        addChangeBehaviourForLocationSelect(form.getFromLocationSelect());
        form.getCalculateDistanceButton().setEnabled(false);
        form.getCalculateDistanceButton().setVisible(false);
        form.getGoodsTypeSelect().setReadOnly(true);
        form.getToLocationSelect().getCountrySelect().setReadOnly(true);
        form.getToLocationSelect().getCitySelect().setReadOnly(true);
        form.getDistanceDurationTextField().setReadOnly(true);
        form.getDistanceTextField().setReadOnly(true);
        form.getFromLocationSelect().getCitySelect().setReadOnly(true);
        form.getFromLocationSelect().getCountrySelect().setReadOnly(true);
        form.getGoodsCharacteristicsOneTextField().setReadOnly(true);
        form.getGoodsSizeNumberField().setReadOnly(true);
        form.getGoodsPalletSizeNumberField().setReadOnly(true);
        form.getGoodsCharacteristicsTwoTextField().setReadOnly(true);
        form.getSaveButton().setVisible(false);
        form.getDescriptionField().setReadOnly(true);
        form.getPayCurrencySelect().setReadOnly(true);
        form.getPayTypeSelect().setReadOnly(true);
        form.getPayValueNumberField().setReadOnly(true);
        form.getFromDatePicker().setReadOnly(true);
        form.getToDatePicker().setReadOnly(true);
    }

    @Override
    public Image getImage() {
        return null;
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
