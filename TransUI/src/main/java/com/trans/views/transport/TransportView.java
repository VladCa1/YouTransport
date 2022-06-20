package com.trans.views.transport;

import com.trans.serviceInterface.models.TransportFormResultDTO;
import com.trans.views.widgets.LocationSelect;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.data.provider.DataCommunicator;
import org.vaadin.klaudeta.PaginatedGrid;

import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

public interface TransportView {

    PaginatedGrid<TransportFormResultDTO> getGrid();

    void resetFilters();

    String getPaySelectFilterValue();

    String getPayCurrencyValue();

    Double getPayMinValue();

    Set<String> getGoodsTypesValue();

    LocalDate getFromDate();

    String getToCountryValue();

    String getToCityValue();

    String getFromCityValue();

    String getFromCountryValue();

    public interface TransportPresenter {

        void setView(TransportView transportView);

        void refreshGrid();

        boolean isGridPopulated();

        void init();

        void addChangeBehaviourForLocationSelect(LocationSelect fromLocationSelect) throws URISyntaxException;

        void addChangeBehaviourForLocationSelectWithDistanceField(LocationSelect toLocationSelect);

        void applyFilters();
    }
}
