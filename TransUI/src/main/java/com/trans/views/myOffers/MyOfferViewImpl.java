package com.trans.views.myOffers;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.services.DriverService;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.utils.SecurityUtils;
import com.trans.views.MainLayout;
import com.trans.views.myOffers.forms.*;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

@PageTitle("Transport You - My Offers")
@Route(value = "/my-offers/offer", layout = MainLayout.class)
public class MyOfferViewImpl extends VerticalLayout implements MyOfferView, HasUrlParameter<String>{

    @Autowired
    private OfferService offerService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private DriverService driverService;

    private final MyOffersView.MyOffersPresenter presenter;

    @Getter
    private GoodsFormResultEntryDTO goodsFormResultEntryDTO;

    @Getter
    private TransportFormResultDTO transportFormResultEntryDTO;

    @Getter
    private GoodsOfferFormBinder goodsBinder;

    @Getter
    private TransportOfferFormBinder transportBinder;

    private OfferForm form;

    @Autowired
    public MyOfferViewImpl(MyOffersView.MyOffersPresenter presenter){
        this.presenter = presenter;

    }

    @Override
    public void setParameter(BeforeEvent event, String parameter) {
        Location location = event.getLocation();
        QueryParameters queryParameters = location
                .getQueryParameters();
        Map<String, List<String>> map = queryParameters.getParameters();
        try {
            if(offerService.checkUserCanSeeOffer(parameter, queryParameters)){
                if(queryParameters.getParameters().get("type").get(0).equals("goods")){
                    goodsFormResultEntryDTO = offerService.getGoodsOffer(Long.valueOf(parameter));
                    form = new GoodsOfferForm();
                    presenter.addUpdateSpecificGoodsButtons(form, this);
                }else if(queryParameters.getParameters().get("type").get(0).equals("transport")){
                    transportFormResultEntryDTO = offerService.getTransportOffer(Long.valueOf(parameter));
                    form = new TransportOfferForm(locationService, driverService, true);
                    presenter.addUpdateSpecificTransportButtons(form, this);
                }
                bind();
                if(form instanceof GoodsOfferForm){
                    ((GoodsOfferForm)form).completeForm(goodsFormResultEntryDTO);
                } else if (form instanceof TransportOfferForm){
                    ((TransportOfferForm)form).completeForm(transportFormResultEntryDTO);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void bind() {
        if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)) {
            goodsBinder = new GoodsOfferFormBinder((GoodsOfferForm) form, presenter);
            try {
                goodsBinder.addChangeListenerToLocation();
                goodsBinder.bind();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            this.add(form);
        }else if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)) {
            transportBinder = new TransportOfferFormBinder((TransportOfferForm) form, presenter);
            try {
                transportBinder.addChangeListenerToLocation();
                transportBinder.bind();
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
            this.add(form);
        }else {

        }

    }

}
