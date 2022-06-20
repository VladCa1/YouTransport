package com.trans.views.widgets;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

import java.util.ArrayList;
import java.util.List;

public class PayCurrencySelect extends Select<String> {

    /**
     *
     */
    private static final long serialVersionUID = -6271129373264881632L;

    public static final String EURO_€ = "euro (€)";
    public static final String DOLLAR_$ = "dollar ($)";
    public static final String LEU_RON = "leu (ron)";

    public PayCurrencySelect() {
        super();
        setLabel("Pay Currency:");
        List<String> types = new ArrayList<String>() {/**
         *
         */
        private static final long serialVersionUID = 3979746780930797698L;

            {
                add(EURO_€);
                add(DOLLAR_$);
                add(LEU_RON);
            }};
        setDataProvider(new ListDataProvider<String>(types));

    }

}
