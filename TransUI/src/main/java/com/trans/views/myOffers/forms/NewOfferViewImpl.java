package com.trans.views.myOffers.forms;

import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.utils.SecurityUtils;
import com.trans.views.MainLayout;
import com.trans.views.login.LoginView;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterListener;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "/myoffers/newoffer", layout = MainLayout.class)
@PageTitle("New Offer")
public class NewOfferViewImpl extends VerticalLayout implements BeforeEnterListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = -892956473037497848L;

	private NewGoodsOfferForm goodsOfferForm;
	
	private NewTransportOfferForm transportOfferForm;
	
	private final MyOffersPresenter presenter;
	
	@Autowired
	public NewOfferViewImpl(MyOffersPresenter presenter) {
		this.presenter = presenter;
		initForms();
		bind();
	}

	private void bind() {	
		if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_GOODS)) {
			GoodsOfferFormBinder goodsBinder = new GoodsOfferFormBinder(goodsOfferForm, presenter);
			try {
				goodsBinder.bind();
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
			this.add(goodsOfferForm);
		}else if(SecurityUtils.getUserRoles().contains(SecurityUtils.ROLE_TRANSPORT)) {
			TransportOfferFormBinder transportBinder = new TransportOfferFormBinder(goodsOfferForm, presenter);
			transportBinder.bind();
			this.add(transportOfferForm);
		}else {
			UI.getCurrent().navigate(MainLayout.class);
		}
		
	}

	private void initForms() {
		goodsOfferForm = new NewGoodsOfferForm();	
		transportOfferForm = new NewTransportOfferForm();	
		
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		if(!SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(LoginView.class);
		}
		
	}
	
}
