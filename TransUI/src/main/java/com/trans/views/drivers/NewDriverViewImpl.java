package com.trans.views.drivers;

import com.trans.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@PageTitle("Transport You - New Driver")
@Route(value = "/drivers/new", layout = MainLayout.class)
public class NewDriverViewImpl extends HorizontalLayout implements NewDriverView {

    @Getter
    private NewDriverForm newDriverForm;

    private final NewDriverPresenter driverPresenter;

    @Autowired
    public NewDriverViewImpl(NewDriverPresenter driverPresenter){

        this.driverPresenter = driverPresenter;
        this.driverPresenter.setView(this);
        initComponents();
        this.driverPresenter.init();
        bind();

    }

    private void bind() {
        NewDriverBinder binder = new NewDriverBinder(driverPresenter, this.newDriverForm);
        binder.bind();
    }

    private void initComponents() {
        newDriverForm = new NewDriverForm();
    }

    @PostConstruct
    private void postConstruct(){
        this.add(newDriverForm);
    }

}
