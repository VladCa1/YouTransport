package com.trans.views.myOffers;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.utils.SecurityUtils;
import com.trans.views.myOffers.forms.OfferForm;
import com.trans.views.myOffers.forms.TransportOfferForm;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.QueryParameters;
import org.springframework.beans.factory.annotation.Autowired;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.LocationService;
import com.trans.services.OfferService;
import com.trans.utils.DialogUtils;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.trans.views.myOffers.forms.NewOfferViewImpl;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

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
		List<GoodsFormResultEntryDTO> goodsResults = new ArrayList<>();
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)){goodsResults = offerService.getGoodsOfferForUser();}
		if(goodsResults != null) {
			view.getGoodsOfferGrid().setDataProvider(new ListDataProvider<>(goodsResults));
			view.getGoodsOfferGrid().getDataProvider().refreshAll();
		}
		List<TransportFormResultDTO>  transportResults = new ArrayList<>();
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)){transportResults = offerService.getTransportOfferForUser();}
		if(transportResults != null) {
			view.getTransportOfferGrid().setDataProvider(new ListDataProvider<>(transportResults));
			view.getTransportOfferGrid().getDataProvider().refreshAll();
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
	public void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect select, IntegerField maxDistance) throws URISyntaxException {

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
	public boolean saveUpdate(GoodsFormResultEntryDTO goodsFormResult) {
		boolean result = offerService.saveUpdateGoods(goodsFormResult);
		if(result) {
			((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).getItems().add(goodsFormResult);
			((ListDataProvider<GoodsFormResultEntryDTO>)view.getGoodsOfferGrid().getDataProvider()).refreshAll();
		}	
		return result;
	}

	@Override
	public boolean saveUpdate(TransportFormResultDTO transportFormResultDTO) {
		boolean result = offerService.saveUpdateTransport(transportFormResultDTO);
		if(result) {
			((ListDataProvider<TransportFormResultDTO>)view.getTransportOfferGrid().getDataProvider()).getItems().add(transportFormResultDTO);
			((ListDataProvider<TransportFormResultDTO>)view.getTransportOfferGrid().getDataProvider()).refreshAll();
		}
		return result;
	}



	@Override
	public ComponentEventListener<ClickEvent<Button>> getDeleteGoodsOfferClickHandler(GoodsFormResultEntryDTO offer) {
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

	@Override
	public ComponentEventListener<ClickEvent<Button>> getDeleteTransportOfferClickHandler(TransportFormResultDTO offer) {
		return event ->{
			DialogUtils.confirm("Delete offer", "Are you sure you want to delete the offer?").addClickListener(confirmEvent ->{
				if(offerService.deleteTransportOffer(offer)) {
					((ListDataProvider<TransportFormResultDTO>)view.getTransportOfferGrid().getDataProvider()).getItems().remove(offer);
					((ListDataProvider<TransportFormResultDTO>)view.getTransportOfferGrid().getDataProvider()).refreshAll();
					Notification success = Notification.show("Offer succesfully deleted");
					success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
				}else {
					Notification failure = Notification.show("Offer failed to delete");
					failure.addThemeVariants(NotificationVariant.LUMO_ERROR);
				}
			});
		};
	}

	@Override
	public ComponentEventListener<ClickEvent<Button>> getViewGoodsOfferClickHandler(GoodsFormResultEntryDTO offer) {
		return event ->{
			UI.getCurrent().navigate("/my-offers/offer/" + offer.getId(), new QueryParameters(new HashMap<String, List<String>>(){{
				put("type", new ArrayList<String>(){{
					add("goods");
				}});
			}}));
		};
	}

	@Override
	public ComponentEventListener<ClickEvent<Button>> getViewTransportOfferClickHandler(TransportFormResultDTO offer) {
		return event ->{
			UI.getCurrent().navigate("/my-offers/offer/" + offer.getId(), new QueryParameters(new HashMap<String, List<String>>(){{
				put("type", new ArrayList<String>(){{
					add("transport");
				}});
			}}));
		};
	}

	@Override
	public void addUpdateSpecificGoodsButtons(OfferForm form, MyOfferViewImpl myOfferView) {
		Button updateButton = new Button("Update");
		Button cancelButton = new Button("Cancel");

		form.add(updateButton);
		form.add(cancelButton);

		form.getSaveButton().setVisible(false);
		form.getSaveButton().setEnabled(false);

		updateButton.addClickListener(event -> {
			GoodsFormResultEntryDTO bean = myOfferView.getGoodsFormResultEntryDTO();
			try {
				GoodsFormResultEntryDTO updatedBean = myOfferView.getGoodsBinder().writeBean();
				updatedBean.setId(bean.getId());

				if(offerService.saveUpdateGoods(updatedBean)){
					myOfferView.getGoodsBinder().showUpdateSuccess();
					myOfferView.getGoodsBinder().clearForm();

				}else{
					myOfferView.getGoodsBinder().showError();
				}

			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ValidationException e) {
				e.printStackTrace();
			}
		});

		cancelButton.addClickListener(event -> {
			UI.getCurrent().navigate(MyOffersViewImpl.class);
		});

	}

	@Override
	public void addUpdateSpecificTransportButtons(OfferForm form, MyOfferViewImpl myOfferView) {
		Button updateButton = new Button("Update");
		Button cancelButton = new Button("Cancel");
		((TransportOfferForm)form).getClearForm().setEnabled(false);
		((TransportOfferForm)form).getClearForm().setVisible(false);
		form.add(updateButton);
		form.add(cancelButton);

		form.getSaveButton().setVisible(false);
		form.getSaveButton().setEnabled(false);

		updateButton.addClickListener(event -> {
			TransportFormResultDTO bean = myOfferView.getTransportFormResultEntryDTO();
			try {
				TransportFormResultDTO updatedBean = myOfferView.getTransportBinder().writeBean();
				updatedBean.setId(bean.getId());
				if(((Set)((TransportOfferForm)form).getGoodsTypeSelect().getValue()).size() > 0){
					updatedBean.setGoodsTypes((Set<String>) ((TransportOfferForm)form).getGoodsTypeSelect().getValue());
				}else {
					myOfferView.getTransportBinder().showError();
					throw new Exception("Save error");
				}
				if(offerService.saveUpdateTransport(updatedBean)){
					myOfferView.getTransportBinder().showUpdateSuccess();
					myOfferView.getTransportBinder().clearForm();

				}else{
					myOfferView.getTransportBinder().showError();
				}

			} catch (URISyntaxException e) {
				e.printStackTrace();
			} catch (ValidationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});

		cancelButton.addClickListener(event -> {
			UI.getCurrent().navigate(MyOffersViewImpl.class);
		});
	}


}
