package com.trans.views.widgets.buttons;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;

public class CancelButton extends Button{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4554912670390702229L;

	public CancelButton(ComponentEventListener<ClickEvent<Button>> event){
		this.addClickListener(event);
		this.addClickShortcut(Key.F4);
		this.setText("Cancel - F4");	
	}
}
