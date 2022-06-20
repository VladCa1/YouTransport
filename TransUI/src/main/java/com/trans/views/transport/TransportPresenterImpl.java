package com.trans.views.transport;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.utils.DialogUtils;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@SpringComponent
@UIScope
public class TransportPresenterImpl implements TransportView.TransportPresenter {

    @Setter
    private TransportView view;

    @Autowired
    private OfferService offerService;

    @Autowired
    private LocationService locationService;

    @Getter
    private boolean isGridPopulated;

    private void populateGrid() {
        List<TransportFormResultDTO> result = offerService.getAllTransportOffers();
        view.getGrid().setDataProvider(new ListDataProvider<TransportFormResultDTO>(result));
        isGridPopulated = true;

    }

    @Override
    public void refreshGrid() {
        populateGrid();

    }

    @Override
    public void init() {
        populateGrid();
    }

    @Override
    public void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException {
        select.populateCountrySelect(locationService.findAllCountries());
        select.getCountrySelect().addValueChangeListener(event ->{
            select.getCitySelect().setEnabled(true);
            if(select.getCountrySelect().getValue().isEmpty()){
               select.getCitySelect().setEnabled(false);
            }
            try {
                select.populateCitySelect(locationService.findAllCitiesForCountry(select.getCountrySelect().getValue()));
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect toLocationSelect) {

    }

    @Override
    public void applyFilters() {
        ListDataProvider<TransportFormResultDTO> dataProvider = (ListDataProvider) view.getGrid().getDataProvider();
        AtomicBoolean alert = new AtomicBoolean(false);
        dataProvider.addFilter(offer -> {
            if(view.getToCountryValue() != null && ( view.getToCityValue() == null || view.getToCityValue().isEmpty() ) && !view.getToCountryValue().isEmpty()){
                alert.set(true);
                return false;
            }
            if(view.getGoodsTypesValue() != null &&
                    !view.getGoodsTypesValue().isEmpty()  && !offer.getGoodsTypes().containsAll(view.getGoodsTypesValue())){
                return false;
            }
            if(view.getPaySelectFilterValue() != null &&
                    !view.getPaySelectFilterValue().isEmpty() && !offer.getPayType().equals(view.getPaySelectFilterValue())){
                return false;
            }
            if(offer.getMaxAcceptedDistanceFromSource() != null){
                try {
                    Double fromOriginToOfferOriginDistance = locationService.getDistance(view.getFromCityValue(), view.getFromCountryValue(), offer.getFromLocationCity(), offer.getFromLocationCountry());
                    if(view.getToCountryValue().isEmpty()){
                        if(fromOriginToOfferOriginDistance > offer.getMaxAcceptedDistanceFromSource()){
                            return false;
                        }
                    }else{
                        Double fromOfferOriginToOfferDestinationDistance = locationService.getDistance(offer.getFromLocationCity(), offer.getFromLocationCountry(), view.getToCityValue(), view.getToCountryValue());
                        if(fromOfferOriginToOfferDestinationDistance + fromOriginToOfferOriginDistance > offer.getMaxAcceptedDistanceFromSource()){
                            return false;
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else {
                if(view.getFromCityValue() != null &&
                        !view.getFromCityValue().isEmpty() && !offer.getFromLocationCity().equals(view.getFromCityValue())){
                    return false;
                }
                if(view.getFromCountryValue() != null &&
                        !view.getFromCountryValue().isEmpty() && !offer.getFromLocationCountry().equals(view.getFromCountryValue())){
                    return false;
                }
                if(view.getToCountryValue() != null &&
                        !view.getToCountryValue().isEmpty() && !offer.getToLocationCountry().equals(view.getToCountryValue())){
                    return false;
                }
                if(view.getToCityValue() != null &&
                        !view.getToCityValue().isEmpty() && !offer.getToLocationCity().equals(view.getToCityValue())){
                    return false;
                }
            }

            if(view.getPayCurrencyValue() != null &&
                    !view.getPayCurrencyValue().isEmpty() && !offer.getPayCurrency().equals(view.getPayCurrencyValue())){
                return false;
            }
            if(view.getPayMinValue() != null && offer.getPayValue() < view.getPayMinValue()){
                return false;
            }
            if(view.getFromDate() != null && offer.getFromDate().isAfter(view.getFromDate())){
                return false;
            }
            return true;
        });
        if (alert.get()){
            DialogUtils.alert("Alert", "Please complete the TO LOCATION filter correctly");
        }
    }

}


