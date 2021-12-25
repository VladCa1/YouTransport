package com.trans.views.login;

import com.trans.security.SecurityUtils;
import com.trans.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route("login")
@PageTitle("Transport You - Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

	/**
	 * 
	 * 
	 * 
	 */
	private static final long serialVersionUID = -5048899286886317838L;
	
	private final LoginForm login = new LoginForm(); 

	public LoginView(){
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);

		login.setAction("login");
		
		Button registerButton = new Button("Register");
		registerButton.setWidth("150px");
		registerButton.addClickListener(onClick ->{
			UI.getCurrent().navigate(RegisterView.class);
		});
		add(new H1("Transport You"), login, registerButton);
		
		

	}

	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		if (SecurityUtils.isUserLoggedIn()) {
			beforeEnterEvent.forwardTo("/");
		}		
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
            login.setError(true);
        }
		
	}
}