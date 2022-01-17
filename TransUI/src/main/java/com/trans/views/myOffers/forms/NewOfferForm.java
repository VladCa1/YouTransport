package com.trans.views.myOffers.forms;

import com.trans.views.widgets.LocationSelect;
import com.trans.views.widgets.PayTypeSelect;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;
import lombok.Setter;

public class NewOfferForm extends VerticalLayout {

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
	protected NumberField payValueNumberField;

	@Getter
	@Setter
	protected TextField descriptionField;
	
	protected HorizontalLayout datePickerWrapper;

	@Getter
	@Setter
	protected DatePicker fromDatePicker;

	@Getter
	@Setter
	protected DatePicker toDatePicker;

	public NewOfferForm() {
		initMainComponents();
	}

	private void initMainComponents() {
		fromLocationSelect = new LocationSelect();
		payTypeSelect = new PayTypeSelect();
		payValueNumberField = new NumberField("Value:");
		descriptionField = new TextField();
		fromDatePicker = new DatePicker("From date:");
		toDatePicker = new DatePicker("To date:");
		payWrapper = new HorizontalLayout();
		datePickerWrapper = new HorizontalLayout();

	}

}
