package com.trans.views.widgets;

import java.util.List;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.data.provider.ListDataProvider;

import lombok.Getter;

public class LocationSelect extends HorizontalLayout{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7435454272734438593L;

	@Getter
	private Select<String> countrySelect;
	
	@Getter
	private Select<String> citySelect;
	
	public LocationSelect() {
		initComponents();
		bind();
	}

	private void bind() {
		this.add(countrySelect, citySelect);
		
	}

	private void initComponents() {
		countrySelect = new Select<String>();
		countrySelect.setLabel("Country");
		citySelect = new Select<String>();
		citySelect.setLabel("City");
		citySelect.setEnabled(false);
	}
	
	public void populateCountrySelect(List<String> list) {
		countrySelect.setDataProvider(new ListDataProvider<String>(list));
	}
	
	public void populateCitySelect(List<String> list) {
		citySelect.setDataProvider(new ListDataProvider<String>(list));
	}
}
