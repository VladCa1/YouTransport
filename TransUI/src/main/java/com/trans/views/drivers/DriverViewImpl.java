package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import com.trans.serviceInterface.models.GoodsFormResultEntryDTO;
import com.trans.utils.DialogUtils;
import com.trans.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.ColumnPathRenderer;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.klaudeta.PaginatedGrid;

import javax.annotation.PostConstruct;

@PageTitle("Transport You - Your Drivers")
@Route(value = "/drivers", layout = MainLayout.class)
public class DriverViewImpl extends VerticalLayout implements DriverView, BeforeEnterObserver {

    @Getter
    private PaginatedGrid<DriverResultEntryDTO> grid;

    @Getter
    private Button createDriverButton;

    @Getter
    private final DriverView.DriverPresenter driverPresenter;

    @Autowired
    public DriverViewImpl(DriverView.DriverPresenter driverPresenter){
        this.driverPresenter = driverPresenter;
        this.driverPresenter.setView(this);
        initComponents();
        this.driverPresenter.init();
    }

    private void initComponents() {
        grid = new PaginatedGrid<>();
        grid.addColumn(DriverResultEntryDTO::getFirstName).setHeader("First Name");
        grid.addColumn(DriverResultEntryDTO::getLastName).setHeader("Last Name");
        grid.addColumn(DriverResultEntryDTO::getAge).setHeader("Age");
        grid.addColumn(DriverResultEntryDTO::getExperience).setHeader("Experience");
        grid.addColumn(DriverResultEntryDTO::getNationality).setHeader("Nationality");
        grid.addColumn(new ComponentRenderer<>(driver -> {
            Icon icon = null;
            if(driver.isAssigned()) {
                icon = new Icon(VaadinIcon.CHECK);
            } else {
                icon = new Icon(VaadinIcon.BAN);
            }
            return icon;
        })).setHeader("Assigned");
        grid.addColumn(new ComponentRenderer<>(driver -> {
            Button deleteButton = new Button("Delete");
            deleteButton.setIcon(new Icon(VaadinIcon.TRASH));
            deleteButton.addClickListener(event ->{
                DialogUtils.confirm("Delete driver", "Are you sure you want to delete the driver?").addClickListener(confirmEvent ->{
                    driverPresenter.deleteDriver(driver);
                });
            });
            return deleteButton;
        })).setHeader("Actions");

        createDriverButton = new Button("Create Driver");
        createDriverButton.setIcon(new Icon(VaadinIcon.PLUS));
        createDriverButton.getStyle().set("margin-left", "auto");
        createDriverButton.addClickListener(event -> {
            UI.getCurrent().navigate(NewDriverViewImpl.class);
        });

    }

    @PostConstruct
    private void postConstruct(){
        this.add(createDriverButton);
        this.add(grid);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if(driverPresenter.isGridPopulated()){
            driverPresenter.refreshGrid();
        }
    }
}
