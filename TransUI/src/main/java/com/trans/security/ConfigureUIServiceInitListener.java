package com.trans.security;

import com.trans.views.myOffers.MyOffersViewImpl;
import org.springframework.stereotype.Component;

import com.trans.utils.SecurityUtils;
import com.trans.views.MainLayout;
import com.trans.views.login.LoginView;
import com.trans.views.login.RegisterView;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.InternalServerError;
import com.vaadin.flow.server.ServiceInitEvent;
import com.vaadin.flow.server.VaadinServiceInitListener;

@Component 
public class ConfigureUIServiceInitListener implements VaadinServiceInitListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5219379302743235419L;

	@Override
	public void serviceInit(ServiceInitEvent event) { 
		event.getSource().addUIInitListener(uiEvent -> {
			final UI ui = uiEvent.getUI();
			ui.addBeforeEnterListener(this::authenticateNavigation);
		});
	}

	private void authenticateNavigation(BeforeEnterEvent event) {
		
//		if (!LoginView.class.equals(event.getNavigationTarget())
//		    && !SecurityUtils.isUserLoggedIn() && !RegisterView.class.equals(event.getNavigationTarget())  && !InternalServerError.class.equals(event.getNavigationTarget())) {
//			event.rerouteTo(LoginView.class);
//		}
		
		if(LoginView.class.equals(event.getNavigationTarget()) &&
				SecurityUtils.isUserLoggedIn() && RegisterView.class.equals(event.getNavigationTarget())) {
			event.rerouteTo(MainLayout.class);
		}
		if(MyOffersViewImpl.class.equals(event.getNavigationTarget()) && !SecurityUtils.isUserLoggedIn()) {
			event.rerouteTo(MainLayout.class);
		}
		
	}
}