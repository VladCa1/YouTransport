package com.trans.views.widgets;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

public class GoodsTypeSelect extends Select<String>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4778697733164779899L;
	
	public static final String LIQUID_GOODS  = "Liquid (/l)";
	public static final String FREE_FLOW_GOODS = "Free flow (/t)";
	public static final String PALLET_GOODS = "Pallets (/number)";
	
	public GoodsTypeSelect() {
		setLabel("Goods Type:");
		List<String> types = new ArrayList<String>() {/**
			 * 
			 */
			private static final long serialVersionUID = 3979746780930797698L;

		{
			add(FREE_FLOW_GOODS);
			add(PALLET_GOODS);
			add(LIQUID_GOODS);
		}};
		setDataProvider(new ListDataProvider<String>(types));

	}
}
