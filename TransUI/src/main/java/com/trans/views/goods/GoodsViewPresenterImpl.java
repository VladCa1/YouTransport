package com.trans.views.goods;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.List;

import com.trans.services.LocationService;
import com.trans.views.widgets.GoodsTypeSelect;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.textfield.NumberField;
import org.springframework.beans.factory.annotation.Autowired;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.OfferService;
import com.trans.views.goods.GoodsView.GoodsViewPresenter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;

import lombok.Getter;
import lombok.Setter;

@SpringComponent
public class GoodsViewPresenterImpl implements GoodsViewPresenter{

	@Getter
	@Setter
	private GoodsView view;
	
	@Autowired
	private OfferService offerService;

	@Autowired
	private LocationService locationService;
	
	@Getter
	private boolean isGridPopulated;

	@Override
	public void init() {
		populateGrid();
		
	}

	private void populateGrid() {
		List<GoodsFormResultEntryDTO> result = offerService.getAllGoodsOffers();
		view.getGrid().setDataProvider(new ListDataProvider<GoodsFormResultEntryDTO>(result));
		isGridPopulated = true;
		
	}

	@Override
	public void addChangeBehaviourForLocationSelect(LocationSelect select) throws URISyntaxException {

		select.populateCountrySelect(locationService.findAllCountries());
		select.getCountrySelect().addValueChangeListener(event ->{
			if(select.getCountrySelect().getValue().isEmpty()){
				select.getCitySelect().setEnabled(false);
			}else {
				select.getCitySelect().setEnabled(true);
			}
			try {
				select.populateCitySelect(locationService.findAllCitiesForCountry(select.getCountrySelect().getValue()));
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		});

	}

	@Override
	public void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect select, NumberField maxDistance) throws URISyntaxException {

		select.populateCountrySelect(locationService.findAllCountries());
		select.getCountrySelect().addValueChangeListener(event ->{
			select.getCitySelect().setEnabled(true);
			if(!select.getCountrySelect().getValue().isEmpty()){
				maxDistance.setEnabled(false);
			}else{
				maxDistance.setEnabled(true);
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
	public void refreshGrid() {
		populateGrid();
		
	}

	@Override
	public void applyFilters() {
		ListDataProvider<GoodsFormResultEntryDTO> dataProvider = (ListDataProvider) view.getGrid().getDataProvider();
		dataProvider.addFilter(offer -> {
			if(view.getGoodsTypeValue() != null &&
					!view.getGoodsTypeValue().isEmpty()  && !offer.getGoodsType().equals(view.getGoodsTypeValue())){
				return false;
			}
			if(view.getGoodsTypeSizeValue() != null && offer.getGoodsSize() > view.getGoodsTypeSizeValue()){
				return false;
			}
			if(offer.getGoodsType().equals(GoodsTypeSelect.PALLET_GOODS)){
				if(view.getGoodsPalletSizeValue() != null && offer.getGoodsPalletSize() > view.getGoodsPalletSizeValue()){
					return false;
				}
			}
			if(view.getPaySelectFilterValue() != null &&
					!view.getPaySelectFilterValue().isEmpty() && !offer.getPayType().equals(view.getPaySelectFilterValue())){
				return false;
			}
			if(view.getPayCurrencyValue() != null &&
					!view.getPayCurrencyValue().isEmpty() && !offer.getPayCurrency().equals(view.getPayCurrencyValue())){
				return false;
			}
			if(view.getPayMinValue() != null && offer.getPayValue() < view.getPayMinValue()){
				return false;
			}
			if(view.getMaxDistanceValue() != null){

				try {
					Double fromOriginToOfferOriginDistance = locationService.getDistance(view.getFromCityValue(), view.getFromCountryValue(), offer.getFromLocationCity(), offer.getFromLocationCountry());
					Double fromOfferOriginToOfferDestinationDistance = locationService.getDistance(offer.getFromLocationCity(), offer.getFromLocationCountry(), offer.getToLocationCity(), offer.getToLocationCountry());
					if(fromOfferOriginToOfferDestinationDistance + fromOriginToOfferOriginDistance > view.getMaxDistanceValue()){
						return false;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}


			}else{
				if(view.getToCountryValue() != null &&
						!view.getToCountryValue().isEmpty() && !offer.getToLocationCountry().equals(view.getToCountryValue())){
					return false;
				}
				if(view.getToCityValue() != null &&
						!view.getToCityValue().isEmpty() && !offer.getToLocationCity().equals(view.getToCityValue())){
					return false;
				}
				if(view.getFromCityValue() != null &&
						!view.getFromCityValue().isEmpty() && !offer.getFromLocationCity().equals(view.getFromCityValue())){
					return false;
				}
				if(view.getFromCountryValue() != null &&
						!view.getFromCountryValue().isEmpty() && !offer.getFromLocationCountry().equals(view.getFromCountryValue())){
					return false;
				}
			}

			return true;
		});
	}

}
