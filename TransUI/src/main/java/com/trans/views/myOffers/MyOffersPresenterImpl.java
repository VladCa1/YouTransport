package com.trans.views.myOffers;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.utils.DialogUtils;
import com.trans.utils.SecurityUtils;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.trans.views.myOffers.forms.NewOfferViewImpl;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.Result;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import com.vaadin.flow.theme.lumo.Lumo;

import lombok.Getter;
import lombok.Setter;

@SpringComponent
@UIScope
public class MyOffersPresenterImpl implements MyOffersPresenter{

	@Getter
	@Setter
	private MyOffersView view;
	
	private final LocationService locationService;
	
	private final OfferService offerService;
	
	@Autowired
	public MyOffersPresenterImpl(LocationService locationService, OfferService offerService) {
		this.locationService = locationService;
		this.offerService = offerService;
	}
	
	@Override
	public void init() {
		addCreateNewOfferHandler();
		populateGrids();
		
	}

	private void populateGrids() {
		List<GoodsFormResultEntryDTO> results = new ArrayList<>();
		results = offerService.getGoodsOfferForUser();
		if(results != null) {
			view.getGoodsOfferGrid().setDataProvider(new ListDataProvider<>(results));
			view.getGoodsOfferGrid().getDataProvider().refreshAll();
		}
		
		
	}

	private void addCreateNewOfferHandler() {
		view.getCreateNewOfferButton().addClickListener(event ->{
			UI.getCurrent().navigate(NewOfferViewImpl.class);
		});
		
	}


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
	public boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult) {
		boolean result = offerService.saveUpdate(goodsFormResult);
		if(result) {
			((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).getItems().add(goodsFormResult);
			((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).refreshAll();
		}	
		return result;
	}

	@Override
	public ComponentEventListener<ClickEvent<Button>> getDeleteOfferClickHandler(GoodsFormResultEntryDTO offer) {
		// TODO Auto-generated method stub
		return event ->{
			DialogUtils.confirm("Delete offer", "Are you sure you want to delete the offer?").addClickListener(confirmEvent ->{
				if(offerService.deleteOffer(offer)) {
					((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).getItems().remove(offer);
					((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).refreshAll();
					Notification success = Notification.show("Offer succesfully deleted");		
					success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				}else {
					Notification failure = Notification.show("Offer failed to delete");		
					failure.addThemeVariants(NotificationVariant.LUMO_ERROR);
				}
			});
		};
	}
	
}
