package com.trans.views.widgets;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

public class PayTypeSelect extends Select<String>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6271129373264881632L;
	
	public static final String PER_DAY = "Per Day (/day)";
	public static final String PER_HOUR = "Per Hour (/hr)";
	public static final String PER_KM = "Per Kilometer (/km)";
	
	public PayTypeSelect() {
		super();
		setLabel("Pay Type:");
		List<String> types = new ArrayList<String>() {/**
			 * 
			 */
			private static final long serialVersionUID = 3979746780930797698L;

		{
			add(PER_DAY);
			add(PER_HOUR);
			add(PER_KM);
		}};
		setDataProvider(new ListDataProvider<String>(types));

	}

}
