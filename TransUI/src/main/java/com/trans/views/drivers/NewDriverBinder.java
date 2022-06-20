package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.utils.BasicUtils;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.binder.ValidationResult;
import com.vaadin.flow.data.binder.ValueContext;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class NewDriverBinder {

    private final NewDriverView.NewDriverPresenter driverPresenter;

    private final NewDriverForm newDriverForm;

    private BeanValidationBinder<DriverResultEntryDTO> binder;

    public NewDriverBinder(NewDriverView.NewDriverPresenter driverPresenter, NewDriverForm newDriverForm){

        this.driverPresenter = driverPresenter;
        this.newDriverForm = newDriverForm;
        binder = new BeanValidationBinder<>(DriverResultEntryDTO.class);
    }

    public void bind(){

        binder.forField(newDriverForm.getFirstName()).withValidator(this::notEmptyValidatorString).bind("firstName");
        binder.forField(newDriverForm.getLastName()).withValidator(this::notEmptyValidatorString).bind("lastName");
        binder.forField(newDriverForm.getAge()).withValidator(this::notEmptyValidatorNumber).bind("age");
        binder.forField(newDriverForm.getNationality()).withValidator(this::notEmptyValidatorString).bind("nationality");
        binder.forField(newDriverForm.getExperience()).withValidator(this::notEmptyValidatorNumber).bind("experience");

        newDriverForm.getCreateDriverButton().addClickListener( event -> {
            DriverResultEntryDTO driver = new DriverResultEntryDTO();
            try{


                this.binder.writeBean(driver);

                if(newDriverForm.getFileData() == null){
                    Notification notification = Notification.show("Please add a photo");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    return;
                }else{
                    driver.setImage(BasicUtils.readAllBytes(newDriverForm.getFileData()));
                }
                Notification notification = null;
                if(driverPresenter.addDriver(driver)){
                    notification = new Notification("Driver created");
                    notification.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                    UI.getCurrent().navigate(DriverViewImpl.class);
                }else {
                    notification = new Notification("Driver failed to be created");
                    notification.addThemeVariants(NotificationVariant.LUMO_ERROR);
                }
                notification.setDuration(5);
                notification.open();

            } catch (ValidationException exception) {
//                 validation errors are already visible for each field,
//                 and bean-level errors are shown in the status label.
//                 We could show additional messages here if we want, do logging, etc.
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

    private ValidationResult notEmptyValidatorNumber(Integer value, ValueContext ctx) {
        if (value == null) {
            return ValidationResult.error("Value cannot be empty");
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

}
