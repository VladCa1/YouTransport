package com.trans.views.myOffers.forms;

import java.net.URISyntaxException;

import com.trans.services.DriverService;
import com.trans.services.LocationService;
import com.vaadin.flow.router.*;
import org.springframework.beans.factory.annotation.Autowired;

import com.trans.utils.SecurityUtils;
import com.trans.views.MainLayout;
import com.trans.views.login.LoginView;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

@Route(value = "/my-offers/new-offer", layout = MainLayout.class)
@PageTitle("New Offer")
public class NewOfferViewImpl extends VerticalLayout implements BeforeEnterListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -892956473037497848L;

	private GoodsOfferForm goodsOfferForm;
	
	private TransportOfferForm transportOfferForm;
	
	private final MyOffersPresenter presenter;

	private final LocationService locationService;

	private final DriverService driverService;
	
	@Autowired
	public NewOfferViewImpl(MyOffersPresenter presenter, LocationService locationService, DriverService driverService) {
		this.presenter = presenter;
		this.locationService = locationService;
		this.driverService = driverService;
		initForms();
		bind();
	}

	private void bind() {	
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)) {
			GoodsOfferFormBinder goodsBinder = new GoodsOfferFormBinder(goodsOfferForm, presenter);
			try {
				goodsBinder.addChangeListenerToLocation();
				goodsBinder.bind();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			this.add(goodsOfferForm);
		}else if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)) {
			TransportOfferFormBinder transportBinder = new TransportOfferFormBinder(transportOfferForm, presenter);
			try{
				transportBinder.addChangeListenerToLocation();
				transportBinder.bind();
			}catch (Exception e){

			}
			this.add(transportOfferForm);
		}else {

		}
		
	}

	private void initForms() {
		goodsOfferForm = new GoodsOfferForm();
		this.goodsOfferForm.setLocationService(locationService);
		transportOfferForm = new TransportOfferForm(locationService, driverService, true);

		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(!SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginView.class);
		}
		
	}
	
}
