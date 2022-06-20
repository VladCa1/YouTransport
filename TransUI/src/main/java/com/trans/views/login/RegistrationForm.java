package com.trans.views.login;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.stream.Stream;

import com.vaadin.flow.component.HasValueAndElement;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;

import lombok.Getter;

public class RegistrationForm extends FormLayout{
	
	/**
	 *
	 */
	private static final long serialVersionUID = 6950479338575241774L;

	private HorizontalLayout titleLayout;

	@Getter
	private TextField firstName;
	@Getter
	private TextField lastName;
	@Getter
	private TextField userName;

	@Getter
	private EmailField email;
	@Getter
	private PasswordField password;
	@Getter
	private PasswordField passwordConfirm;

	@Getter
	private ComboBox<String> userType;

	@Getter
	private TextField companyInfo;

	@Getter
	private TextField phoneNumber;

	@Getter
	private Span errorMessageField;

	@Getter
	private Button submitButton;

	@Getter
	private Upload imageUpload;

	@Getter
	private InputStream fileData;



	public RegistrationForm() {
		this.getStyle().set("padding-top", "50px");
		titleLayout = new HorizontalLayout();
		titleLayout.setWidthFull();
		H1 title = new H1("Register");
		title.getStyle().set("margin-left", "auto");
		title.getStyle().set("margin-right", "auto");
		titleLayout.add(title);
		firstName = new TextField("First name");
		lastName = new TextField("Last name");
		email = new EmailField("Email");
		phoneNumber = new TextField("Phone number");
		phoneNumber.getStyle().set("margin-bottom", "10px");

		userType = new ComboBox<String>("Type of user:");
		userType.setDataProvider(new ListDataProvider<String>(new ArrayList<String>() {/**
			 *
			 */
			private static final long serialVersionUID = 1L;

		{
			add("Goods provider");
			add("Transport provider");
		}}));
		userType.getStyle().set("margin-bottom", "10px");
		MemoryBuffer memoryBuffer = new MemoryBuffer();
		imageUpload = new Upload();
		imageUpload.setReceiver(memoryBuffer);
		imageUpload.setDropLabel(new Label("Select Profile Picture"));
		imageUpload.setAcceptedFileTypes("image/png");
		imageUpload.addSucceededListener(event ->{
			fileData = memoryBuffer.getInputStream();
		});

		userType.setValue("Goods provider");

		userName = new TextField("User name");
		password = new PasswordField("Password");
		passwordConfirm = new PasswordField("Confirm password");
		companyInfo = new TextField("Company Information");
		companyInfo.getStyle().set("margin-bottom", "10px");
		setRequiredIndicatorVisible(firstName, lastName, email, userName, password,
				passwordConfirm);

		errorMessageField = new Span();

		submitButton = new Button("Join the community");
		submitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

		add(titleLayout, firstName, lastName, email, userName, password,
				passwordConfirm, userType, imageUpload, companyInfo, phoneNumber, errorMessageField,
				submitButton);

		// Max width of the Form
		setMaxWidth("500px");
		getStyle().set("margin", "0 auto");


		// Allow the form layout to be responsive.
		// On device widths 0-490px we have one column.
		// Otherwise, we have two columns.
		setResponsiveSteps(
				new ResponsiveStep("0", 1, ResponsiveStep.LabelsPosition.TOP),
				new ResponsiveStep("490px", 2, ResponsiveStep.LabelsPosition.TOP));

		// These components always take full width
		setColspan(titleLayout, 2);
		setColspan(email, 2);
		setColspan(userName, 2);
		setColspan(companyInfo, 2);
		setColspan(phoneNumber, 2);
		setColspan(imageUpload, 2);
		setColspan(errorMessageField, 2);
		setColspan(submitButton, 2);

	}

	private void setRequiredIndicatorVisible(HasValueAndElement<?, ?>... components) {
		Stream.of(components).forEach(comp -> comp.setRequiredIndicatorVisible(true));
	}


}
