package com.trans.views.myOffers.forms;

import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.util.StringUtils;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.views.myOffers.MyOffersViewImpl;
import com.trans.views.myOffers.MyOffersView.MyOffersPresenter;
import com.trans.views.widgets.GoodsTypeSelect;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;

public class GoodsOfferFormBinder {
	
	private final MyOffersPresenter presenter;
	
	private final GoodsOfferForm goodsOfferForm;

	private BeanValidationBinder<GoodsFormResultEntryDTO> binder;
	
	public GoodsOfferFormBinder(GoodsOfferForm goodsOfferForm, MyOffersPresenter presenter) {
		this.presenter = presenter;
		this.goodsOfferForm = goodsOfferForm;
		this.binder = new BeanValidationBinder<>(GoodsFormResultEntryDTO.class);
	}

	public void bind() throws URISyntaxException {

		this.binder.forField(goodsOfferForm.getGoodsTypeSelect()).withValidator(this::notEmptyValidatorString).bind("goodsType");
		this.binder.forField(goodsOfferForm.getGoodsSizeNumberField()).withValidator(this::notEmptyValidatorNumber).bind("goodsSize");
		this.binder.forField(goodsOfferForm.getGoodsPalletSizeNumberField()).withValidator(this::notEmptyValidatorNumberPallet).bind("goodsPalletSize");
		this.binder.forField(goodsOfferForm.getGoodsCharacteristicsOneTextField()).withValidator(this::notEmptyValidatorString).bind("characteristicOne");
		this.binder.forField(goodsOfferForm.getGoodsCharacteristicsTwoTextField()).bind("characteristicTwo");
		this.binder.forField(goodsOfferForm.getDescriptionField()).withValidator(this::notEmptyValidatorString).bind("description");
		this.binder.forField(goodsOfferForm.getToLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorString).bind("toLocationCountry");
		this.binder.forField(goodsOfferForm.getToLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorString).bind("toLocationCity");
		this.binder.forField(goodsOfferForm.getFromLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCountry");
		this.binder.forField(goodsOfferForm.getFromLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCity");
		this.binder.forField(goodsOfferForm.getPayTypeSelect()).withValidator(this::notEmptyValidatorString).bind("payType");
		this.binder.forField(goodsOfferForm.getPayValueNumberField()).withValidator(this::notEmptyValidatorNumber).bind("payValue");
		this.binder.forField(goodsOfferForm.getPayCurrencySelect()).withValidator(this::notEmptyValidatorString).bind("payCurrency");
		this.binder.forField(goodsOfferForm.getToDatePicker()).withValidator(this::notEmptyValidatorDate).bind("toDate");
		this.binder.forField(goodsOfferForm.getFromDatePicker()).withValidator(this::notEmptyValidatorDate).bind("fromDate");
		this.binder.forField(goodsOfferForm.getDistanceTextField()).withValidator(this::notEmptyValidatorString).bind("distance");
		this.binder.forField(goodsOfferForm.getDistanceDurationTextField()).withValidator(this::notEmptyValidatorString).bind("duration");


		goodsOfferForm.getSaveButton().addClickListener(event -> {
			try {
				// Create empty bean to store the details into
				GoodsFormResultEntryDTO goodsFormResult = new GoodsFormResultEntryDTO();
				
				// Run validators and write the values to the bean
				this.binder.writeBean(goodsFormResult);
				

				// Typically, you would here call backend to store the bean

				// Show success message if everything went well
				
				if(presenter.saveUpdate(goodsFormResult)) {
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
			} catch (Exception registerException) {
				// TODO Auto-generated catch block
				registerException.printStackTrace();
			}
		});
	}

	public void addChangeListenerToLocation() throws URISyntaxException {
		presenter.addChangeBehaviourForLocationSelect(goodsOfferForm.getFromLocationSelect());
		presenter.addChangeBehaviourForLocationSelect(goodsOfferForm.getToLocationSelect());
	}


	private ValidationResult notEmptyValidatorString(String value, ValueContext ctx) {

		if (!StringUtils.hasLength(value)) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}
	
	private ValidationResult notEmptyValidatorNumber(Double value, ValueContext ctx) {

		if (value == null) {
			return ValidationResult.error("Value cannot be empty");
		}else {
			return ValidationResult.ok();
		}

	}
	
	private ValidationResult notEmptyValidatorNumberPallet(Double value, ValueContext ctx) {

		if(goodsOfferForm.getGoodsTypeSelect().getValue() != null && !goodsOfferForm.getGoodsTypeSelect().getValue().equals(GoodsTypeSelect.PALLET_GOODS)) {
			return ValidationResult.ok();
		}
		
		if (value == null) {
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

	public GoodsFormResultEntryDTO writeBean() throws URISyntaxException, ValidationException {

		GoodsFormResultEntryDTO bean = new GoodsFormResultEntryDTO();
		this.binder.writeBean(bean);
		return bean;
	}

	public void clearForm() throws URISyntaxException, ValidationException {
		this.binder.getFields().forEach(HasValue::clear);
	}
}
