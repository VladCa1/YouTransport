package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;

public interface NewDriverView {
    public interface NewDriverPresenter {

        void setView(NewDriverView newDriverView);

        void init();

        boolean addDriver(DriverResultEntryDTO driver);
    }
}
