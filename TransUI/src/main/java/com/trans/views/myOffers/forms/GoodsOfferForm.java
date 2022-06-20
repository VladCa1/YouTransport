package com.trans.views.myOffers.forms;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.services.DistanceMatrixResponse;
import com.trans.services.LocationService;
import com.trans.views.widgets.GoodsTypeSelect;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;

import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class GoodsOfferForm extends OfferForm {

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
	private TextField goodsCharacteristicsOneTextField;
	
	@Getter
	@Setter
	private TextField goodsCharacteristicsTwoTextField;
	
	private HorizontalLayout distanceWrapper;
	
	@Getter
	@Setter
	private TextField distanceTextField;

	@Getter
	@Setter
	private TextField distanceDurationTextField;

	@Getter
	protected Button calculateDistanceButton;

	@Setter
	private LocationService locationService;

	public GoodsOfferForm() {
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
		payWrapper.add(payCurrencySelect);
		
	}

	private void initLeftPart() {
		leftPart.add(goodsWrapper);
		leftPart.add(routeWrapper);
		leftPart.add(goodsCharacteristicsOneTextField);
		leftPart.add(goodsCharacteristicsTwoTextField);
		
	}

	private void initRightPart() {
		rightPart.add(payWrapper);
		rightPart.add(distanceWrapper);
		rightPart.add(datePickerWrapper);
		rightPart.add(descriptionField);
		
		
	}

	private void initDistanceWrapper() {
		distanceWrapper.add(calculateDistanceButton);
		distanceWrapper.add(distanceTextField);
		distanceWrapper.add(distanceDurationTextField);
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
		routeWrapper.add(new Span("From:"));
		routeWrapper.add(fromLocationSelect);
		routeWrapper.add(new Span("To:"));
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
		goodsCharacteristicsOneTextField = new TextField("Characteristics one:");
		goodsCharacteristicsTwoTextField = new TextField("Characteristics two:");
		distanceTextField = new TextField("Distance:");
		distanceDurationTextField = new TextField("Duration:");
		distanceTextField.setReadOnly(true);
		distanceDurationTextField.setReadOnly(true);
		distanceWrapper = new HorizontalLayout();
		calculateDistanceButton = new Button("Calculate");
		calculateDistanceButton.getStyle().set("margin-top", "35px");
		calculateDistanceButton.addClickListener(getCalculateDistanceButtonListener());
		saveButton = new Button("Create");
		saveButton.setIcon(new Icon(VaadinIcon.PLUS));
		saveButton.getStyle().set("margin-left", "auto");
	}

	private ComponentEventListener<ClickEvent<Button>> getCalculateDistanceButtonListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
				try {
					DistanceMatrixResponse distanceMatrixResponse = locationService.getDirectionInfo(fromLocationSelect.getCitySelect().getValue() + " " + fromLocationSelect.getCountrySelect().getValue(), toLocationSelect.getCitySelect().getValue() + " " + toLocationSelect.getCountrySelect().getValue());
					distanceTextField.setValue(distanceMatrixResponse.getRows().get(0).getElements().get(0).getDistance().getText());
					distanceDurationTextField.setValue(distanceMatrixResponse.getRows().get(0).getElements().get(0).getDuration().getText());
				} catch (IOException e) {
					e.printStackTrace();
				} catch (NoSuchAlgorithmException e) {
					e.printStackTrace();
				} catch (KeyManagementException e) {
					e.printStackTrace();
				}
			}
		};
	}

	public void completeForm(GoodsFormResultEntryDTO goodsFormResultEntryDTO){
		doLocationCompleteForm(goodsFormResultEntryDTO);

		this.payTypeSelect.setValue(goodsFormResultEntryDTO.getPayType());
		this.payCurrencySelect.setValue(goodsFormResultEntryDTO.getPayCurrency());
		this.payValueNumberField.setValue(goodsFormResultEntryDTO.getPayValue());
		this.descriptionField.setValue(goodsFormResultEntryDTO.getDescription());
		this.toDatePicker.setValue(goodsFormResultEntryDTO.getToDate());
		this.fromDatePicker.setValue(goodsFormResultEntryDTO.getFromDate());
		this.distanceTextField.setValue(goodsFormResultEntryDTO.getDistance());
		this.distanceDurationTextField.setValue(goodsFormResultEntryDTO.getDuration());
		this.goodsCharacteristicsOneTextField.setValue(goodsFormResultEntryDTO.getCharacteristicOne());
		if(goodsFormResultEntryDTO.getCharacteristicTwo() != null){
			this.goodsCharacteristicsTwoTextField.setValue(goodsFormResultEntryDTO.getCharacteristicTwo());
		}
		this.goodsSizeNumberField.setValue(goodsFormResultEntryDTO.getGoodsSize());
		this.goodsTypeSelect.setValue(goodsFormResultEntryDTO.getGoodsType());
		this.goodsPalletSizeNumberField.setValue(goodsFormResultEntryDTO.getGoodsPalletSize());
	}

	public void doLocationCompleteForm(GoodsFormResultEntryDTO goodsFormResultEntryDTO) {
		this.fromLocationSelect.getCountrySelect().setValue(goodsFormResultEntryDTO.getFromLocationCountry());
		this.fromLocationSelect.getCitySelect().setValue(goodsFormResultEntryDTO.getFromLocationCity());
		this.toLocationSelect.getCountrySelect().setValue(goodsFormResultEntryDTO.getToLocationCountry());
		this.toLocationSelect.getCitySelect().setValue(goodsFormResultEntryDTO.getToLocationCity());
	}


}
