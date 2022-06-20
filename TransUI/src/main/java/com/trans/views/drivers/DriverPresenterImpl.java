package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.services.DriverService;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class DriverPresenterImpl implements DriverView.DriverPresenter {

    @Getter
    @Setter
    private DriverView view;

    @Autowired
    private DriverService driverService;

    @Getter
    private boolean isGridPopulated;


    @Override
    public void init() {
        populateGrid();
    }

    @Override
    public void refreshGrid() {
        populateGrid();
    }

    @Override
    public void deleteDriver(DriverResultEntryDTO driver) {
        if(driverService.deleteDriver(driver.getId())){
            ((ListDataProvider<DriverResultEntryDTO>)view.getGrid().getDataProvider()).getItems().remove(driver);
            ((ListDataProvider<DriverResultEntryDTO>)view.getGrid().getDataProvider()).refreshAll();
            Notification success = Notification.show("Driver succesfully deleted");
            success.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        }else {
            Notification failure = Notification.show("Driver failed to delete");
            failure.addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }

    private void populateGrid() {
        List<DriverResultEntryDTO> result = driverService.getAllDriversForUser();
        view.getGrid().setDataProvider(new ListDataProvider<DriverResultEntryDTO>(result));
        isGridPopulated = true;
    }
}
