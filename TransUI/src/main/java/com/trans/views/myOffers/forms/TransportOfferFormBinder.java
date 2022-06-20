package com.trans.views.myOffers.forms;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.trans.views.myOffers.MyOffersViewImpl;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.util.StringUtils;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class TransportOfferFormBinder {

	private final MyOffersPresenter presenter;

	private final TransportOfferForm transportOfferForm;

	BeanValidationBinder<TransportFormResultDTO> binder;

	public TransportOfferFormBinder(TransportOfferForm transportOfferForm, MyOffersPresenter presenter) {
		this.transportOfferForm = transportOfferForm;
		this.presenter = presenter;
		binder = new BeanValidationBinder<>(TransportFormResultDTO.class);
	}

	public void bind() {

		this.binder.forField(transportOfferForm.getDescriptionField()).withValidator(this::notEmptyValidatorString).bind("description");
		this.binder.forField(transportOfferForm.getToLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorStringDate).bind("toLocationCountry");
		this.binder.forField(transportOfferForm.getToLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorStringDate).bind("toLocationCity");
		this.binder.forField(transportOfferForm.getFromLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCountry");
		this.binder.forField(transportOfferForm.getFromLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCity");
		this.binder.forField(transportOfferForm.getPayTypeSelect()).withValidator(this::notEmptyValidatorString).bind("payType");
		this.binder.forField(transportOfferForm.getPayValueNumberField()).withValidator(this::notEmptyValidatorNumber).bind("payValue");
		this.binder.forField(transportOfferForm.getPayCurrencySelect()).withValidator(this::notEmptyValidatorString).bind("payCurrency");
		this.binder.forField(transportOfferForm.getFromDatePicker()).withValidator(this::notEmptyValidatorDate).bind("fromDate");
		this.binder.forField(transportOfferForm.getVehicleTypeSelect()).withValidator(this::notEmptyValidatorString).bind("vehicleType");
		this.binder.forField(transportOfferForm.getMake()).withValidator(this::notEmptyValidatorString).bind("make");
		this.binder.forField(transportOfferForm.getType()).withValidator(this::notEmptyValidatorString).bind("type");
		this.binder.forField(transportOfferForm.getTonnage()).withValidator(this::notEmptyValidatorNumber).bind("tonnage");
		this.binder.forField(transportOfferForm.getHorsePower()).withValidator(this::notEmptyValidatorNumber).bind("horsePower");
		this.binder.forField(transportOfferForm.getDriverSelect()).withValidator(this::notEmptyDriver).bind("driver");
		this.binder.forField(transportOfferForm.getFabricationYear()).withValidator(this::notEmptyValidatorDate).bind("fabricationYear");
		this.binder.forField(transportOfferForm.getMaxDistance()).withValidator(this::notEmptyMaxDistanceValidator).bind("maxAcceptedDistanceFromSource");
		this.binder.forField(transportOfferForm.getHazardous()).bind("hazardousMaterialsAccepted");

		this.binder.forField(transportOfferForm.getDistanceTextField()).withValidator(this::notEmptyValidatorStringVariable).bind("distance");
		this.binder.forField(transportOfferForm.getDistanceDurationTextField()).withValidator(this::notEmptyValidatorStringVariable).bind("duration");

		transportOfferForm.getSaveButton().addClickListener(event -> {
			try {
				// Create empty bean to store the details into
				TransportFormResultDTO formResultDTO = new TransportFormResultDTO();

				// Run validators and write the values to the bean
				this.binder.writeBean(formResultDTO);
				if(((Set)transportOfferForm.getGoodsTypeSelect().getValue()).size() > 0){
					formResultDTO.setGoodsTypes((Set<String>) transportOfferForm.getGoodsTypeSelect().getValue());
				}else {
					showError();
					throw new Exception("Save error");
				}

				// Typically, you would here call backend to store the bean

				// Show success message if everything went well

				if(presenter.saveUpdate(formResultDTO)) {
					showSuccess();
					this.binder.getFields().forEach(HasValue::clear);
				}else {
					showError();
					throw new Exception("Save error");
				}

			} catch (ValidationException exception) {
				// validation errors are already visible for each field,
				// and bean-level errors are shown in the status label.
				// We could show additional messages here if we want, do logging, etc.
				exception.printStackTrace();
				showError();
			} catch (Exception registerException) {
				// TODO Auto-generated catch block
				registerException.printStackTrace();
			}
		});

	}

	private ValidationResult moreThanOne(Object values, ValueContext valueContext) {
		if(values != null){
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}
	}

	private ValidationResult notEmptyValidatorStringDate(String value, ValueContext ctx) {
		if(transportOfferForm.getMaxDistance().getValue() == null){
			if (value == null) {
				return ValidationResult.error("Value cannot be empty");
			}else {
				return ValidationResult.ok();
			}
		}else {
			return ValidationResult.ok();
		}
	}

	private ValidationResult notEmptyMaxDistanceValidator(Integer value, ValueContext ctx) {

		if(!StringUtils.hasLength(transportOfferForm.getToLocationSelect().getCountrySelect().getValue())){
			if (value == null) {
				return ValidationResult.error("Value cannot be empty");
			}else {
				return ValidationResult.ok();
			}
		}else {
			return ValidationResult.ok();
		}
	}

	private ValidationResult notEmptyDriver(DriverResultEntryDTO value, ValueContext ctx) {
		if (value == null) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}
	}

	private ValidationResult notEmptyValidatorStringVariable(String value, ValueContext ctx) {

		if(transportOfferForm.getMaxDistance().getValue() == null){
			if (!StringUtils.hasLength(value)) {
				return ValidationResult.error("Value cannot be empty");
			}else {
				return ValidationResult.ok();
			}
		}else {
			return ValidationResult.ok();
		}

	}

	private ValidationResult notEmptyValidatorString(String value, ValueContext ctx) {

		if (!StringUtils.hasLength(value)) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}

	private ValidationResult notEmptyValidatorDate(LocalDate value, ValueContext ctx) {

		if (value == null) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}

	public void addChangeListenerToLocation() throws URISyntaxException {
		presenter.addChangeBehaviourForLocationSelect(transportOfferForm.getFromLocationSelect());
		presenter.addChangeBehaviourForLocationSelectWithDistanceField(transportOfferForm.getToLocationSelect(), transportOfferForm.getMaxDistance());
	}

	private ValidationResult notEmptyValidatorNumber(Double value, ValueContext ctx) {

		if (value == null) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}

	private ValidationResult notEmptyValidatorNumber(Integer value, ValueContext ctx) {

		if (value == null) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}

	public void showSuccess() {
		Notification notification = Notification.show("The offer has been saved");
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);

		UI.getCurrent().navigate(MyOffersViewImpl.class);
	}

	public void showUpdateSuccess(){
		Notification notification = Notification.show("The offer has been updated");
		notification.addThemeVariants(NotificationVariant.LUMO_CONTRAST);

		UI.getCurrent().navigate(MyOffersViewImpl.class);
	}

	public void showError() {
		Notification notification = Notification.show("Error saving the offer");
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);

	}

	public TransportFormResultDTO writeBean() throws URISyntaxException, ValidationException {

		TransportFormResultDTO bean = new TransportFormResultDTO();
		this.binder.writeBean(bean);
		return bean;
	}

	public void clearForm() throws URISyntaxException, ValidationException {
		this.binder.getFields().forEach(HasValue::clear);
	}

}
