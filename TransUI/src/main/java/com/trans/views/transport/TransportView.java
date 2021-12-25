package com.trans.views.transport;

import com.trans.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "/transport", layout = MainLayout.class)
public class TransportView extends VerticalLayout{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3362393261776234525L;

	
	public TransportView() {
		this.add(new Text("Transport User"));
	}
}
