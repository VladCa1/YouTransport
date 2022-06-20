package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.services.DriverService;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class NewDriverPresenterImpl implements NewDriverView.NewDriverPresenter {

    @Setter
    NewDriverView view;

    @Autowired
    private DriverService driverService;

    @Override
    public void init() {

    }

    @Override
    public boolean addDriver(DriverResultEntryDTO driver) {
        return driverService.addDriver(driver);
    }
}
