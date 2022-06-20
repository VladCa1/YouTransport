package com.trans.views.myOffers.forms;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.services.DistanceMatrixResponse;
import com.trans.services.DriverService;
import com.trans.services.LocationService;
import com.trans.views.widgets.*;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.ItemLabelGenerator;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.MultiSelectListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class TransportOfferForm extends OfferForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8750970247892703834L;

	@Getter
	private VerticalLayout vehicleLayout;

	private H3 vehicleTitle = new H3("Vehicle");

	private HorizontalLayout formWrapper;

	private HorizontalLayout distanceWrapper;

	private VerticalLayout innerFormWrapper;

	private VerticalLayout checkBoxesLayout;

	private VerticalLayout descriptionLayout;

	private HorizontalLayout distanceCalculationWrapper;

	@Getter
	@Setter
	private LocationSelect toLocationSelect;

	@Getter
	@Setter
	private VehicleTypeSelect vehicleTypeSelect;

	@Getter
	@Setter
	private Checkbox hazardous;

	@Getter
	@Setter
	private Checkbox liquid;

	@Getter
	@Setter
	private IntegerField horsePower;

	@Getter
	@Setter
	private TextField make;

	@Getter
	@Setter
	private TextField type;

	@Getter
	@Setter
	private NumberField tonnage;

	@Getter
	@Setter
	private DatePicker fabricationYear;

	@Getter
	@Setter
	private IntegerField maxDistance;

	@Getter
	@Setter
	private Select<DriverResultEntryDTO> driverSelect;

	@Getter
	@Setter
	private CheckboxGroup<String> goodsTypeSelect;

	@Getter
	@Setter
	private TextField distanceTextField;

	@Getter
	@Setter
	private TextField distanceDurationTextField;

	@Getter
	protected Button calculateDistanceButton;

	@Getter
	private Button clearForm;

	private HorizontalLayout buttonLayout;

	private final LocationService locationService;

	private final DriverService driverSerivce;

	private boolean canPopulate;


	public TransportOfferForm(LocationService locationService, DriverService driverSerivce, boolean canPopulate){
		this.locationService = locationService;
		this.driverSerivce = driverSerivce;
		this.canPopulate = canPopulate;
		initComponents();
		bind();

	}

	private void bind() {
		initDistanceCalculationWrapper();
		try {
			initDistanceWrapper();
		} catch (Exception e) {
			e.printStackTrace();
		}
		initPayWrapper();
		initCheckBoxes();
		initDescriptonLayout();
		initButtonLayout();
		innerFormWrapper.add(distanceWrapper, payWrapper, checkBoxesLayout, descriptionLayout);
		formWrapper = new HorizontalLayout(vehicleLayout, innerFormWrapper);
		this.add(formWrapper, buttonLayout);
	}

	private void initButtonLayout() {
		buttonLayout = new HorizontalLayout();
		buttonLayout.add(saveButton, clearForm);
	}

	private void initDescriptonLayout() {
		descriptionLayout = new VerticalLayout();
		descriptionLayout.add(descriptionField);
	}

	private void initCheckBoxes() {
		VerticalLayout typesLayout = new VerticalLayout();
		typesLayout.getStyle().set("border", "1px solid lightgray");
		hazardous = new Checkbox("Hazardous transport accepted");
		goodsTypeSelect = new CheckboxGroup<String>();
		List<String> types = new ArrayList<String>() {/**
		 *
		 */
		private static final long serialVersionUID = 3979746780930797698L;

			{
				add("Free flow");
				add("Pallet goods");
				add("Liquid goods");
			}
		};
		goodsTypeSelect.setLabel("Goods type accepted:");
		goodsTypeSelect.setDataProvider(new ListDataProvider<String>(types));
		typesLayout.add(goodsTypeSelect);
		checkBoxesLayout = new VerticalLayout(hazardous, typesLayout);
		checkBoxesLayout.getStyle().set("border", "1px solid lightgray");
	}

	private void initDistanceCalculationWrapper() {
		distanceCalculationWrapper.add(calculateDistanceButton);
		distanceCalculationWrapper.add(distanceTextField);
		distanceCalculationWrapper.add(distanceDurationTextField);
	}


	private void initComponents() {
		saveButton = new Button("Create");
		saveButton.setIcon(new Icon(VaadinIcon.PLUS));
		clearForm = new Button("Clear");
		clearForm.setIcon(new Icon(VaadinIcon.TRASH));
		clearForm.addClickListener(getClearFormClickListener());
		innerFormWrapper = new VerticalLayout();
		payTypeSelect = new PayTypeSelect();
		payCurrencySelect = new PayCurrencySelect();
		payValueNumberField = new NumberField("Value:");
		toLocationSelect = new LocationSelect();
		vehicleLayout = new VerticalLayout();
		vehicleTypeSelect = new VehicleTypeSelect();
		hazardous = new Checkbox();
		liquid = new Checkbox();
		make = new TextField("Make:");
		type = new TextField("Type:");
		horsePower = new IntegerField("Horse Power:");
		tonnage = new NumberField("Tonnage");
		fabricationYear = new DatePicker("Fabrication Date:");
		fromDatePicker = new DatePicker("Available from:");
		fromDatePicker.getStyle().set("margin-top", "150px");

		driverSelect = new Select<DriverResultEntryDTO>();
		driverSelect.setLabel("Driver:");
		if(canPopulate){
			driverSelect.setItems(driverSerivce.getAllDriversForUser());
			driverSelect.setItemLabelGenerator((ItemLabelGenerator<DriverResultEntryDTO>) item -> item.getFirstName() + " " + item.getLastName());
		}

		vehicleLayout.add(vehicleTitle, vehicleTypeSelect, tonnage, make, type, horsePower, fabricationYear, driverSelect, fromDatePicker);

		distanceTextField = new TextField("Distance:");
		distanceDurationTextField = new TextField("Duration:");
		distanceTextField.setReadOnly(true);
		distanceDurationTextField.setReadOnly(true);
		distanceWrapper = new HorizontalLayout();
		distanceCalculationWrapper = new HorizontalLayout();
		calculateDistanceButton = new Button("Calculate");
		calculateDistanceButton.getStyle().set("margin-top", "35px");
		calculateDistanceButton.addClickListener(getCalculateDistanceButtonListener());

		setMaxWidth("900px");
		getStyle().set("margin", "0 auto");
		getStyle().set("margin-top", "40px");
		getStyle().set("border", "1px solid lightgray");

	}

	private ComponentEventListener<ClickEvent<Button>> getClearFormClickListener() {
		return new ComponentEventListener<ClickEvent<Button>>() {
			@Override
			public void onComponentEvent(ClickEvent<Button> event) {
//				make.setValue("");
//				type.setValue("");
//				horsePower.setValue(null);
//				vehicleTypeSelect.setValue(null);
//				hazardous.setValue(false);
//				liquid.setValue(false);
//				toLocationSelect.getCountrySelect().setValue("");
//				toLocationSelect.getCitySelect().setValue("");
//				fromLocationSelect.getCountrySelect().setValue("");
//				fromLocationSelect.getCitySelect().setValue("");
//				maxDistance.setValue(null);
//				driverSelect.setValue(null);
//				tonnage.setValue(null);
//				descriptionField.setValue("");
				UI.getCurrent().getPage().reload();
			}
		};
	}

	public void completeForm(TransportFormResultDTO result) {
		this.make.setValue(result.getMake());
		this.vehicleTypeSelect.setValue(result.getVehicleType());
		this.tonnage.setValue(result.getTonnage());
		this.type.setValue(result.getType());
		this.fabricationYear.setValue(result.getFabricationYear());
		this.horsePower.setValue(result.getHorsePower());
		this.driverSelect.setValue(result.getDriver());
		this.hazardous.setValue(result.getHazardousMaterialsAccepted());
		this.goodsTypeSelect.setValue(result.getGoodsTypes());
		this.payTypeSelect.setValue(result.getPayType());
		this.payCurrencySelect.setValue(result.getPayCurrency());
		this.payValueNumberField.setValue(result.getPayValue());
		this.descriptionField.setValue(result.getDescription());
		this.fromDatePicker.setValue(result.getFromDate());
		if(result.getMaxAcceptedDistanceFromSource() != null){
			this.distanceTextField.setValue("");
			this.distanceDurationTextField.setValue("");
		}else{
			this.distanceTextField.setValue(result.getDistance());
			this.distanceDurationTextField.setValue(result.getDuration());
		}
		doLocationCompleteForm(result);

    }
	public void doLocationCompleteForm(TransportFormResultDTO result) {
		this.fromLocationSelect.getCountrySelect().setValue(result.getFromLocationCountry());
		this.fromLocationSelect.getCitySelect().setValue(result.getFromLocationCity());
		if(result.getToLocationCountry() != null){
			this.toLocationSelect.getCountrySelect().setValue(result.getToLocationCountry());
			this.toLocationSelect.getCitySelect().setValue(result.getToLocationCity());
		}else{
			this.getMaxDistance().setValue(result.getMaxAcceptedDistanceFromSource());
		}
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

	private void initDistanceWrapper() throws URISyntaxException {

		distanceWrapper = new HorizontalLayout();

		toLocationSelect = new LocationSelect();
		fromLocationSelect = new LocationSelect();
		maxDistance = new IntegerField("Max Distance(km):");
		maxDistance.addValueChangeListener( event ->{
			if(maxDistance.getValue() != null){
				calculateDistanceButton.setEnabled(false);
				distanceTextField.setValue("");
				distanceDurationTextField.setValue("");
				toLocationSelect.getCountrySelect().setEnabled(false);
				toLocationSelect.getCitySelect().setEnabled(false);
			}else {
				calculateDistanceButton.setEnabled(true);
				toLocationSelect.getCountrySelect().setEnabled(true);
			}
		});

		VerticalLayout locationsWrapper = new VerticalLayout(new Span("From:"), fromLocationSelect, new Span("To:"), toLocationSelect, maxDistance, distanceCalculationWrapper);

		distanceWrapper.add(locationsWrapper);

	}

	private void initPayWrapper() {
		payWrapper.add(payTypeSelect);
		payWrapper.add(payValueNumberField);
		payWrapper.add(payCurrencySelect);

	}
}
