package com.trans.views.myOffers.forms;

import com.trans.views.widgets.GoodsTypeSelect;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;
import lombok.Setter;

public class NewGoodsOfferForm extends NewOfferForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -835023056629437677L;
	
	private HorizontalLayout formPart;
	
	private VerticalLayout leftPart;
	
	private VerticalLayout rightPart;
	
	@Getter
	@Setter
	private LocationSelect toLocationSelect;
	
	private VerticalLayout routeWrapper;
	
	private Span banner;
	
	@Getter
	@Setter
	private GoodsTypeSelect goodsTypeSelect;
	
	@Getter
	@Setter
	private NumberField goodsSizeNumberField;
	
	@Getter
	@Setter
	private NumberField goodsPalletSizeNumberField;
	
	private HorizontalLayout goodsWrapper;
	
	@Getter
	@Setter
	private TextField goodsCharactersticsOneTextField;
	
	@Getter
	@Setter
	private TextField goodsCharactersiticsTwoTextField;
	
	private HorizontalLayout distanceWrapper;
	
	@Getter
	@Setter
	private NumberField distanceNumberField;
	
	@Getter
	private Button calculateDistanceButton;
	
	@Getter
	private Button saveButton;
	
	public NewGoodsOfferForm() {
		super();
		this.initComponents();
		bind();
	}
	
	private void bind() {
		initRouteWrapper();
		initGoodsWrapper();
		initDistanceWrapper();
		initPayWrapper();
		initDatePickerWrapper();
		initRightPart();
		initLeftPart();
		initFormPart();
		add(banner, formPart, saveButton);
	}
	
	private void initFormPart() {
		formPart.add(leftPart, rightPart);
		
	}

	private void initDatePickerWrapper() {
		datePickerWrapper.add(fromDatePicker);
		datePickerWrapper.add(toDatePicker);
		
	}

	private void initPayWrapper() {
		payWrapper.add(payTypeSelect);
		payWrapper.add(payValueNumberField);
		
	}

	private void initLeftPart() {
		leftPart.add(goodsWrapper);
		leftPart.add(routeWrapper);
		leftPart.add(goodsCharactersticsOneTextField);
		leftPart.add(goodsCharactersiticsTwoTextField);
		
	}

	private void initRightPart() {
		rightPart.add(payWrapper);
		rightPart.add(distanceWrapper);
		rightPart.add(datePickerWrapper);
		rightPart.add(descriptionField);
		
		
	}

	private void initDistanceWrapper() {
		distanceWrapper.add(calculateDistanceButton);
		distanceWrapper.add(distanceNumberField);
	}

	private void initGoodsWrapper() {
		goodsWrapper.add(goodsTypeSelect);
		goodsWrapper.add(goodsSizeNumberField);
		goodsWrapper.add(goodsPalletSizeNumberField);
		goodsTypeSelect.addValueChangeListener(event ->{
			if(goodsTypeSelect.getValue() != null && goodsTypeSelect.getValue().equalsIgnoreCase(GoodsTypeSelect.PALLET_GOODS)) {
				goodsPalletSizeNumberField.setVisible(true);
			}else {
				goodsPalletSizeNumberField.setVisible(false);
			}
		});
		
	}

	private void initRouteWrapper() {
		routeWrapper.add(new Span("From"));
		routeWrapper.add(fromLocationSelect);
		routeWrapper.add(new Span("To"));
		routeWrapper.add(toLocationSelect);
		
	}

	private void initComponents() {
		rightPart = new VerticalLayout();
		leftPart = new VerticalLayout();
		formPart = new HorizontalLayout();
		formPart.getStyle().set("border", "1px solid lightgray");
		
		toLocationSelect = new LocationSelect();
		routeWrapper = new VerticalLayout();
		banner = new Span("Goods Offer");
		goodsWrapper = new HorizontalLayout();
		goodsTypeSelect = new GoodsTypeSelect();
		goodsSizeNumberField = new NumberField("Size:");
		goodsPalletSizeNumberField = new NumberField("Pallet Size:");
		goodsPalletSizeNumberField.setVisible(false);
		goodsCharactersticsOneTextField = new TextField("Characteristics one:");
		goodsCharactersiticsTwoTextField = new TextField("Characteristics two:");
		distanceNumberField = new NumberField("Distance (km):");
		distanceWrapper = new HorizontalLayout();
		calculateDistanceButton = new Button("Calculate");
		calculateDistanceButton.getStyle().set("margin-top", "35px");
		saveButton = new Button("Save");
		saveButton.getStyle().set("margin-left", "auto");
	}

	
	
}
