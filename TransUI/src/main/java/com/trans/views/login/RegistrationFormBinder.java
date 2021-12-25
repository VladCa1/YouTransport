package com.trans.views.login;

import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.trans.security.data.User;
import com.trans.services.RegistrationService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class RegistrationFormBinder {

	private RegistrationForm registrationForm;
	
	
	private RegistrationService registrationService;

	/**
	 * Flag for disabling first run for password validation
	 */
	private boolean enablePasswordValidation;
	
	private boolean enableEmailValidation;

	private boolean enableUsernameValidation;

	private boolean existValidation;

	public RegistrationFormBinder(RegistrationForm registrationForm, RegistrationService registrationService) {
		this.registrationForm = registrationForm;
		this.registrationService = registrationService;
	}

	/**
	 * Method to add the data binding and validation logics to the registration form
	 */
	public void addBindingAndValidation() {
		BeanValidationBinder<User> binder = new BeanValidationBinder<>(User.class);
		binder.bindInstanceFields(registrationForm);
		
		// A custom validator for password fields
		binder.forField(registrationForm.getPassword()).withValidator(this::passwordValidator).bind("password");
		
		binder.forField(registrationForm.getEmail()).withValidator(this::emailValidator).bind("email");
		
		binder.forField(registrationForm.getUserName()).withValidator(this::usernameValidator).bind("username");
		
		binder.forField(registrationForm.getFirstName()).withValidator(this::existValidator).bind("firstName");
		
		binder.forField(registrationForm.getLastName()).withValidator(this::existValidator).bind("lastName");
		

		// The second password field is not connected to the Binder, but we
		// want the binder to re-check the password validator when the field
		// value changes. The easiest way is just to do that manually.
		registrationForm.getPasswordConfirm().addValueChangeListener(e -> {
			// The user has modified the second field, now we can validate and show errors.
			// See passwordValidator() for how this flag is used.
			enablePasswordValidation = true;

			binder.validate();
		});
		
		registrationForm.getEmail().addValueChangeListener(e ->{
			
			enableEmailValidation = true;
			binder.validate();
			
		});
		
		registrationForm.getUserName().addValueChangeListener(e ->{
			
			enableUsernameValidation = true;
			binder.validate();
			
		});
		
		registrationForm.getFirstName().addValueChangeListener(e ->{
			
			enableUsernameValidation = true;
			binder.validate();
			
		});
		
		registrationForm.getFirstName().addValueChangeListener(e ->{
			
			enableUsernameValidation = true;
			binder.validate();
			
		});

		// Set the label where bean-level error messages go
		binder.setStatusLabel(registrationForm.getErrorMessageField());

		// And finally the submit button
		registrationForm.getSubmitButton().addClickListener(event -> {
			try {
				// Create empty bean to store the details into
				User userBean = new User();

				// Run validators and write the values to the bean
				binder.writeBean(userBean);
				
				userBean.setUserType(registrationForm.getUserType().getValue().split(" ")[0]);

				// Typically, you would here call backend to store the bean

				// Show success message if everything went well
				
				if(registrationService.registerUser(userBean)) {
					showSuccess(userBean);
				}else {
					throw new Exception("Registration error");
				}
				
			} catch (ValidationException exception) {
				// validation errors are already visible for each field,
				// and bean-level errors are shown in the status label.
				// We could show additional messages here if we want, do logging, etc.
			} catch (Exception registerException) {
				// TODO Auto-generated catch block
				registerException.printStackTrace();
			}
		});
	}

	/**
	 * Method to validate that:
	 * <p>
	 * 1) Password is at least 8 characters long
	 * <p>
	 * 2) Values in both fields match each other
	 */
	private ValidationResult passwordValidator(String pass1, ValueContext ctx) {
		/*
		 * Just a simple length check. A real version should check for password
		 * complexity as well!
		 */

		if (!StringUtils.hasLength(pass1)) {
			return ValidationResult.error("Password cannot be empty");
		}
		
		if (!enablePasswordValidation) {
			// user hasn't visited the field yet, so don't validate just yet, but next time.
			enablePasswordValidation = true;
			return ValidationResult.ok();
		}
		
		if(pass1.length() < 8) {
			return ValidationResult.error("Password should be at least 8 characters long");
		}
		

		String pass2 = registrationForm.getPasswordConfirm().getValue();

		if (pass1 != null && pass1.equals(pass2)) {
			return ValidationResult.ok();
		}

		return ValidationResult.error("Passwords do not match");
	}
	
	/**
	 * Method to validate that:
	 * <p>
	 * 1) Email has valid regex
	 */
	private ValidationResult emailValidator(String email, ValueContext ctx) {
		/*
		 * 
		 * 
		 */

		if(!StringUtils.hasLength(email)) {
			return ValidationResult.error("Email cannot be empty");
		}
		
		if(!enableEmailValidation) {
			enableEmailValidation = true;
			return ValidationResult.ok();
			
		}
		
		String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
		        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
		if(!patternMatches(email, regexPattern)) {
			return ValidationResult.error("Enter a valid email address");
		}
		if(registrationService.findAllEmails().contains(email)) {
			return ValidationResult.error("Email Address already used");
		}

		return ValidationResult.ok();
	}
	
	private ValidationResult usernameValidator(String username, ValueContext ctx) {
		/*
		 * 
		 * 
		 */

		if(!StringUtils.hasLength(username)) {
			return ValidationResult.error("Username cannot be empty");
		}
		
		if(!enableUsernameValidation) {
			enableUsernameValidation = true;
			return ValidationResult.ok();
			
		}
		String regexPatern = "[^\\p{L}\\p{Nd}]+\\s";
		if(patternMatches(regexPatern, username)) {
			return ValidationResult.error("Enter a valid username, no special chars or spaces");
		}
		if(username.length() < 5) {
			return ValidationResult.error("Username has to have at least 5 chars");
		}
		if(registrationService.findAllUserNames().contains(username)) {
			return ValidationResult.error("Username already used");
		}

		return ValidationResult.ok();
	}
	
	private ValidationResult existValidator(String string, ValueContext ctx) {
		/*
		 * 
		 * 
		 */

		if(!StringUtils.hasLength(string)) {
			return ValidationResult.error("Value cannot be empty");
		}
		
		if(!existValidation) {
			existValidation = true;
			return ValidationResult.ok();
			
		}

		return ValidationResult.ok();
	}
	
	
	public static boolean patternMatches(String emailAddress, String regexPattern) {
	    return Pattern.compile(regexPattern)
	      .matcher(emailAddress)
	      .matches();
	}

	/**
	 * We call this method when form submission has succeeded
	 */
	private void showSuccess(User userBean) {
		Notification notification = Notification.show("Data saved, welcome " + userBean.getUsername());
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

		// Here you'd typically redirect the user to another view
	}

}
