package com.trans.views.widgets.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;

public class OkButton extends Button{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -579325641049926049L;

	public OkButton(ComponentEventListener<ClickEvent<Button>> event){
		this.addClickListener(event);
		this.addClickShortcut(Key.F2);
		this.setText("Ok - F2");	
	}
}
