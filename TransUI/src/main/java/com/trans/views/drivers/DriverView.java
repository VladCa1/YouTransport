package com.trans.views.drivers;

import com.trans.serviceInterface.models.DriverResultEntryDTO;
import org.vaadin.klaudeta.PaginatedGrid;

public interface DriverView {
    interface DriverPresenter {

        void init();

        void setView(DriverView view);

        boolean isGridPopulated();

        void refreshGrid();

        void deleteDriver(DriverResultEntryDTO driver);
    }

    PaginatedGrid<DriverResultEntryDTO> getGrid();
}
