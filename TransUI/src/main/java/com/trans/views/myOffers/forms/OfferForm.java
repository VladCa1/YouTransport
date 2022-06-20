package com.trans.views.myOffers.forms;

import com.trans.services.LocationService;
import com.trans.views.widgets.LocationSelect;
import com.trans.views.widgets.PayCurrencySelect;
import com.trans.views.widgets.PayTypeSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;

import lombok.Getter;
import lombok.Setter;

@CssImport(value = "/styles/text-field.css",
		themeFor = "vaadin-text-field")
public class OfferForm extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2450292097595445184L;

	@Getter
	protected LocationSelect fromLocationSelect;
	
	protected HorizontalLayout payWrapper;

	@Getter
	@Setter
	protected PayTypeSelect payTypeSelect;

	@Getter
	@Setter
	protected PayCurrencySelect payCurrencySelect;

	@Getter
	@Setter
	protected NumberField payValueNumberField;

	@Getter
	@Setter
	protected TextArea descriptionField;
	
	protected HorizontalLayout datePickerWrapper;

	@Getter
	@Setter
	protected DatePicker fromDatePicker;

	@Getter
	@Setter
	protected DatePicker toDatePicker;

	@Getter
	protected Button saveButton;


	public OfferForm() {
		initMainComponents();
	}

	private void initMainComponents() {
		fromLocationSelect = new LocationSelect();
		payTypeSelect = new PayTypeSelect();
		payCurrencySelect = new PayCurrencySelect();
		payValueNumberField = new NumberField("Value:");
		descriptionField = new TextArea("Description:");
		descriptionField.setMinWidth("400px");
		descriptionField.setMinHeight("150px");
		fromDatePicker = new DatePicker("From date:");
		toDatePicker = new DatePicker("To date:");
		payWrapper = new HorizontalLayout();
		datePickerWrapper = new HorizontalLayout();

	}

}
