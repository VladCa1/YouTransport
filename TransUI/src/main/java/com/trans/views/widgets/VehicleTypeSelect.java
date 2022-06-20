package com.trans.views.widgets;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.ArrayList;
import java.util.List;

public class VehicleTypeSelect extends Select<String> {

    public static final String SMALL_VEHICLE = "SmallVehicle";
    public static final String BIG_VEHICLE = "BigVehicle";
    public static final String TRUCK = "Truck";

    public VehicleTypeSelect() {
        super();
        setLabel("Vehicle Type:");
        List<String> types = new ArrayList<String>() {/**
         *
         */
        private static final long serialVersionUID = 3979746780930797698L;

            {
                add(SMALL_VEHICLE);
                add(BIG_VEHICLE);
                add(TRUCK);
            }};
        setDataProvider(new ListDataProvider<String>(types));

    }
}
