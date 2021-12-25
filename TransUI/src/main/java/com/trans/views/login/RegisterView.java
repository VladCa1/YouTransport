package com.trans.views.login;

import org.springframework.beans.factory.annotation.Autowired;

import com.trans.services.RegistrationService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("register")
public class RegisterView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1344376676695835772L;
	
	
	@Autowired
	public RegisterView(RegistrationService registrationService) {
	       RegistrationForm registrationForm = new RegistrationForm();
	       // Center the RegistrationForm
	       setHorizontalComponentAlignment(Alignment.CENTER, registrationForm);

	       add(registrationForm);

	       RegistrationFormBinder registrationFormBinder = new RegistrationFormBinder(registrationForm, registrationService);
	       registrationFormBinder.addBindingAndValidation();
	   }
	
}