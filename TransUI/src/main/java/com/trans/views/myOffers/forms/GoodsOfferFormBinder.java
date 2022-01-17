package com.trans.views.myOffers.forms;

import java.net.URISyntaxException;
import java.time.LocalDate;

import org.springframework.util.StringUtils;

import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.utils.SecurityUtils;
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
	
	private final NewGoodsOfferForm goodsOfferForm;
	
	public GoodsOfferFormBinder(NewGoodsOfferForm goodsOfferForm, MyOffersPresenter presenter) {
		this.presenter = presenter;
		this.goodsOfferForm = goodsOfferForm;
	}

	public void bind() throws URISyntaxException {
		presenter.addChangeBehaviourForLocationSelect(goodsOfferForm.getFromLocationSelect());
		presenter.addChangeBehaviourForLocationSelect(goodsOfferForm.getToLocationSelect());
		
		BeanValidationBinder<GoodsFormResultEntryDTO> binder = new BeanValidationBinder<>(GoodsFormResultEntryDTO.class);
		
		binder.forField(goodsOfferForm.getGoodsTypeSelect()).withValidator(this::notEmptyValidatorString).bind("goodsType");
		binder.forField(goodsOfferForm.getGoodsSizeNumberField()).withValidator(this::notEmptyValidatorNumber).bind("goodsSize");
		binder.forField(goodsOfferForm.getGoodsPalletSizeNumberField()).withValidator(this::notEmptyValidatorNumberPallet).bind("goodsPalletSize");
		binder.forField(goodsOfferForm.getGoodsCharactersticsOneTextField()).bind("characteristicOne");
		binder.forField(goodsOfferForm.getGoodsCharactersiticsTwoTextField()).bind("characteristicTwo");
		binder.forField(goodsOfferForm.getDescriptionField()).withValidator(this::notEmptyValidatorString).bind("description");
		binder.forField(goodsOfferForm.getToLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorString).bind("toLocationCountry");
		binder.forField(goodsOfferForm.getToLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorString).bind("toLocationCity");
		binder.forField(goodsOfferForm.getFromLocationSelect().getCountrySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCountry");
		binder.forField(goodsOfferForm.getFromLocationSelect().getCitySelect()).withValidator(this::notEmptyValidatorString).bind("fromLocationCity");
		binder.forField(goodsOfferForm.getPayTypeSelect()).withValidator(this::notEmptyValidatorString).bind("payType");
		binder.forField(goodsOfferForm.getPayValueNumberField()).withValidator(this::notEmptyValidatorNumber).bind("payValue");
		binder.forField(goodsOfferForm.getToDatePicker()).withValidator(this::notEmptyValidatorDate).bind("toDate");
		binder.forField(goodsOfferForm.getFromDatePicker()).withValidator(this::notEmptyValidatorDate).bind("fromDate");
		
		goodsOfferForm.getSaveButton().addClickListener(event -> {
			try {
				// Create empty bean to store the details into
				GoodsFormResultEntryDTO goodsFormResult = new GoodsFormResultEntryDTO();
				
				// Run validators and write the values to the bean
				binder.writeBean(goodsFormResult);
				

				// Typically, you would here call backend to store the bean

				// Show success message if everything went well
				
				if(presenter.saveUpdate(goodsFormResult)) {
					showSuccess();
					binder.getFields().forEach(HasValue::clear);
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

		if(!goodsOfferForm.getGoodsTypeSelect().getValue().equals(GoodsTypeSelect.PALLET_GOODS)) {
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
	
	
	private void showSuccess() {
		Notification notification = Notification.show("The offer has been saved");
		notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
		
		UI.getCurrent().navigate(MyOffersViewImpl.class);
	}	
	
	private void showError() {
		Notification notification = Notification.show("Error saving the offer");
		notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
		
	}

}
